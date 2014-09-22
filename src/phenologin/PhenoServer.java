/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phenologin;

import phenologin.data.Experiment;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import static javax.json.stream.JsonParser.Event.END_ARRAY;
import static javax.json.stream.JsonParser.Event.END_OBJECT;
import static javax.json.stream.JsonParser.Event.KEY_NAME;
import static javax.json.stream.JsonParser.Event.START_OBJECT;
import static javax.json.stream.JsonParser.Event.VALUE_FALSE;
import static javax.json.stream.JsonParser.Event.VALUE_NULL;
import static javax.json.stream.JsonParser.Event.VALUE_TRUE;
import javax.json.stream.JsonParsingException;
import phenologin.data.EnvironmentEffect;
import phenologin.network.Network;

/**
 * @author Sam <willsc8forwings@gmail.com>
 */
public class PhenoServer {

    /*
     * ================================================ MEMBER VARIABLES ================================================
     */
    private String jAuthenticationToken = "";
    private final List< PhenoServerListener > listeners = new ArrayList();
    

    /*
     * ================================================== CONSTRUCTORS ==================================================
     */
    /**
     * Default Constructor for PhenoServer.
     */
    public PhenoServer() {

    }

    /*
     * ================================================ PRIMARY FUNCTIONS ===============================================
     */
    public Experiment getExperiment( int id ) throws JsonParsingException{
        updateStatus( "parsing json for experiment with id " + id, 0 );
        
        String toParse = Network.getExperiment(jAuthenticationToken,id);

        if( toParse == null || "".equals(toParse) ) {
            return null;
        }

        JsonParser parser = Json.createParser( new StringReader(toParse) );

        assertEventType( parser.next(), Event.START_OBJECT );

        Experiment result = new Experiment();
        Event e = parser.next();

        while( true ){
            if( e == Event.END_OBJECT )
                break;
            assertEventType( e, Event.KEY_NAME );
            String key = parser.getString();
            e = parser.next();
            
            if( Experiment.isBoolKey( key ) ){
                assertEventType( e, key, Event.VALUE_FALSE, Event.VALUE_TRUE, Event.VALUE_NULL );
                result.boolMap.put( key, getBoolOrNull( e ) );
                
            }else if( Experiment.isEnvKey( key ) ){
                assertEventType( e, key, Event.START_OBJECT, Event.VALUE_NULL );
                result.envMap.put( key, getEnvOrNull( e, parser ) );
                
            }else if( Experiment.isArrKey( key ) ){
                assertEventType( e, key, Event.START_ARRAY, Event.VALUE_NULL );
                result.arrMap.put( key, getMapListOrNull( e, parser ) );
                
            }else if( Experiment.isObjKey( key ) ){
                assertEventType( e, key, Event.START_OBJECT, Event.VALUE_NULL );
                result.objMap.put( key, getMapOrNull( e, parser ) );
                
            }else if( Experiment.isIntKey( key ) ){
                assertEventType( e, key, Event.VALUE_NUMBER, Event.VALUE_NULL );
                result.intMap.put( key, getIntOrNull( e, parser ) );
                
            }else if( Experiment.isStringKey( key ) ){
                assertEventType( e, key, Event.VALUE_STRING, Event.VALUE_NULL );
                result.stringMap.put( key, getStringOrNull( e, parser ) );
                
            }else{
                updateStatus( "ERROR while parsing json for experiment with id " + id, -1 );
                throw new Error( "unrecognized key \"" + key + "\", value type: " + e );
            }
            
            
            
            e = parser.next();
        }
        
        return result;
    }
    
    private Boolean getBoolOrNull( Event e ){
        switch( e ){
            case VALUE_NULL: return null;
            case VALUE_TRUE: return true;
            case VALUE_FALSE: return false;
            default: throw new Error( "event not null or boolean" );
        }
    }
    
    //assumes string/null event type has been asserted
    private String getStringOrNull( Event e, JsonParser parser ){
        if( e == Event.VALUE_NULL )
            return null;
        return parser.getString();
    }
    
    //assumes number/null event type has been asserted
    private Integer getIntOrNull( Event e, JsonParser parser ){
        if( e == Event.VALUE_NULL )
            return null;
        return parser.getInt();
    }
    
    //assumes env/null event type has been asserted
    private EnvironmentEffect getEnvOrNull( Event e, JsonParser parser ){
        if( e == Event.VALUE_NULL )
            return null;
        EnvironmentEffect result = new EnvironmentEffect();
        
        e = parser.next();

        while( true ){
            if( e == Event.END_OBJECT ){
                System.out.println( "Env: reached end of environment object" );
                break;
            }
            assertEventType( e, Event.KEY_NAME );
            String key = parser.getString();
            e = parser.next();
            
            if( e == Event.VALUE_NULL )
                System.out.println( "Env: found null value for key \"" + key + "\"" );
            
            else if( e == Event.VALUE_FALSE || e == Event.VALUE_TRUE )
                System.out.println( "Env: found boolean value for key \"" + key + "\"" );
            
            else if( e == Event.VALUE_NUMBER )
                System.out.println( "Env: found numerical value for key \"" + key + "\"" );
            
            else if( e == Event.VALUE_STRING )
                System.out.println( "Env: found string value for key \"" + key + "\"" );
            
            else
                throw new Error( "Env: unexpected event: " + e );
            
            e = parser.next();
        }
        
        return result;
    }
    
    //assumes env/null event type has been asserted
    private List< Map< String, Object > > getMapListOrNull( Event e, JsonParser parser ){
        if( e == Event.VALUE_NULL )
            return null;
       List< Map< String, Object > > result = new ArrayList();
        
        e = parser.next();

        while( true ){
            if( e == Event.END_ARRAY ){
                System.out.println( "List: reached end of list" );
                break;
            }
            assertEventType( e, Event.START_OBJECT, Event.VALUE_NULL );
            
            result.add( getMapOrNull( e, parser ) );
            
            e = parser.next();
        }
        
        return result;
    }
    
    private Map< String, Object > getMapOrNull( Event e, JsonParser parser ){
        if( e == Event.VALUE_NULL || !parser.hasNext() )
            return null;
        
        e = parser.next();
            
        Map< String, Object > result = new HashMap();
        
        while( true ){
            if( e == Event.END_OBJECT ){
                System.out.println( "map: reached end of object" );
                break;
            }
            
//            if( e == Event.END_ARRAY ){
//                System.out.println( "map: reached end of list" );
//                break;
//            }
            
            if( e == Event.START_OBJECT || e == Event.VALUE_NULL ){
                result.put( getUniqueKey( result ), getMapOrNull( e, parser ) );
            }else{
                assertEventType( e, Event.KEY_NAME );
                String key = parser.getString();
                e = parser.next();

                if( e == Event.VALUE_NULL ){
                    System.out.println( "map: found null value for key \"" + key + "\"" );
                    result.put( key, null );

                }else if( e == Event.VALUE_FALSE || e == Event.VALUE_TRUE ){
                    System.out.println( "map: found boolean value for key \"" + key + "\"" );
                    result.put( key, getBoolOrNull( e ) );

                }else if( e == Event.VALUE_NUMBER ){
                    System.out.println( "map: found numerical value for key \"" + key + "\"" );
                    result.put( key, parser.getInt() );
                    
                }else if( e == Event.VALUE_STRING ){
                    System.out.println( "map: found string value for key \"" + key + "\"" );
                    result.put( key, parser.getString() );
                    
                }else if( e == Event.START_OBJECT ){
                    System.out.println( "map: found object value for key \"" + key + "\"" );
                    result.put( key, getMapOrNull( e, parser ) );
                    
                }else if( e == Event.START_ARRAY ){
                    System.out.println( "map: found list value for key \"" + key + "\"" );
                    result.put( key, getMapListOrNull( e, parser ) );
                    
                }else
                    throw new Error( "map: unexpected event: " + e );
            }

            e = parser.next();
        }
        
        return result;
    }
    
    private String getUniqueKey( Map< String, Object > m ){
        for( int i = 0 ;; i++ ){
            String s = "unnamed_" + i;
            if( m.containsKey( s ) )
                continue;
            return s;
        }
    }
    
    private void assertEventType( Event e, Event... expected ){
        assertEventType( e, null, expected );
    }
    
    private void assertEventType( Event e, String key, Event... expected ){
        boolean b = false;
        for( Event exp : expected ){
            if( e == exp ){
                b = true;
                break;
            }
        }
        if( !b ){
            StringBuilder sb = new StringBuilder();
            sb.append( "expected event of type " );
            for( int i = 0 ; i < expected.length ; i++ ){
                sb.append( expected[i] );
                if( i < expected.length-1 )
                    sb.append( " or " );
            }
            if( key != null )
                sb.append( " for key \"" + key + "\"" );
            sb.append( ", but found " + e );
            throw new Error( sb.toString() );
        }
    }
    public void addListener( PhenoServerListener psl ){
        listeners.add( psl );
    }
    private void updateStatus( String s, int flag ){
        for( PhenoServerListener l : listeners )
            l.statusChange( s, flag );
    }
    
    /**
     * Gets a list of all Experiments that the currently logged in user has
     * access to. If nothing has changed since the last call, this will just
     * reference memory, otherwise it will query the server, unless it had been
     * already called.
     *
     * @return List[ExperimentInfo]: A list of all the experiments that the current
 user has access to. The experiments only contain basic information; ie
 name and id number.
     * @throws Exception TODO: Use specific exception
     */
    public List<Integer> getExperimentIds() throws Exception {
        List<Integer> output = new ArrayList<>();
        String toParse = Network.getExperiments(jAuthenticationToken);

        if (toParse == null || "".equals(toParse)) {
            return null;
        }

        JsonParser parser = Json.createParser(new StringReader(toParse));
        
        //dummy class to make old code work with few changes
        class ExperimentInfo{
            int id;
            String name;
        }
        ExperimentInfo currentExperiment = null;
        
        if( parser == null ) {
            throw new Exception("Parser returns null object");
        }
        
        while (parser.hasNext()) {
            Event e = parser.next();
            if (e == Event.START_ARRAY) {
                while (e != Event.END_ARRAY && parser.hasNext()) {
                    switch (parser.next()) {
                        case START_OBJECT:
                            currentExperiment = new ExperimentInfo();
                            break;

                        case KEY_NAME:
                            switch (parser.getString()) {
                                case "id":
                                    parser.next();
                                    currentExperiment.id = parser.getInt();
                                    break;

                                case "name":
                                    parser.next();
                                    currentExperiment.name = parser.getString();
                                    break;

                                default:
                                    throw new Exception("Server experiment contained object not of type 'id' or 'name'");
                            }
                            break;

                        case END_OBJECT:
                            output.add(currentExperiment.id);
                            break;

                        case END_ARRAY:
                            return output;

                        default:
                            throw new Exception("Wtf");
                    }
                }
            }
        }
        return output;
    }

    /*
     * ================================================ GETTERS & SETTERS ===============================================
     */
    /**
     *
     * @param token
     */
    public void setToken(String token) {
        jAuthenticationToken = token;
    }

    /**
     * @return
     */
    public String getToken() {
        return jAuthenticationToken;
    }

    /*
     * =============================================== MODIFIER FUNCTIONS ===============================================
     */

    /*
     * ================================================ VISUAL FUNCTIONS ================================================
     */

    /*
     * ================================================ PRIVATE FUNCTIONS ===============================================
     */
}
