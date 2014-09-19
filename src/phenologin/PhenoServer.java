/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phenologin;

import phenologin.data.Experiment;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import phenologin.network.Network;

/**
 * @author Sam <willsc8forwings@gmail.com>
 */
public class PhenoServer {

    /*
     * ================================================ MEMBER VARIABLES ================================================
     */
    private String jAuthenticationToken = "";
    private final List<ExperimentInfo> jExperiments = new ArrayList<>();

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
    
    public Experiment getExperiment() {
        String toParse = Network.getExperiment(jAuthenticationToken,125);
        
        if( toParse == null || "".equals(toParse) ) {
            return null;
        }
        
        JsonParser parser = Json.createParser( new StringReader(toParse) );
        Experiment currentExperiment = null;
        
        if( parser == null ) {
            System.out.println("Parser returns null object");
            return null;
        }
        
        while( parser.hasNext() ) {
            Event e = parser.next();
            if( e == Event.START_ARRAY ) {
                while( e != Event.END_ARRAY && parser.hasNext() ) {
                    switch( parser.next() ) {
                        case START_OBJECT:
                            
                            break;
                        case KEY_NAME:
                            System.out.println(parser.getString());
                            break;
                        case END_OBJECT:
                            System.out.println(parser.getString());
                            break;
                        case END_ARRAY:
                            System.out.println(parser.getString());
                            break;
                        default:
                            System.out.println("WTF");
                            return null;
                    }
                }
            }
        }
        
        return currentExperiment;
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
    public List<ExperimentInfo> getExperiments() throws Exception {
        List<ExperimentInfo> output = new ArrayList<>();
        String toParse = Network.getExperiments(jAuthenticationToken);

        if (toParse == null || "".equals(toParse)) {
            return null;
        }

        JsonParser parser = Json.createParser(new StringReader(toParse));
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
                                    currentExperiment.setID(parser.getInt());
                                    break;

                                case "name":
                                    parser.next();
                                    currentExperiment.setName(parser.getString());
                                    break;

                                default:
                                    throw new Exception("Server experiment contained object not of type 'id' or 'name'");
                            }
                            break;

                        case END_OBJECT:
                            output.add(currentExperiment);
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
