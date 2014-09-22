/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template in the editor.
 */
package phenologin.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.stream.JsonParser.Event;

public class Experiment{
    
    private static final String[] stringKeys = { 
        "approved_at", "archived_at", "completed_at", "created_at", "name", 
        "photoperiod", "start_at", "state", "updated_at", 
        "collaborator_name", "script_url", "jobs_count" 
    };
    
    private static final String[] intKeys = { 
        "duration", "id", "imager_id", "archived_by", "max_co2", "max_humidity", 
        "max_light", "max_temperature", "min_co2", "min_humidity", "min_light", "min_temperature",
        "plant_age_at", "temp", "user_id", "institute_id", "collaborator_id", 
    };
    
    private static final String[] boolKeys = { 
        "is_basic", "is_perturb" 
    };
    
    private static final String[] envKeys = { 
        "co2", "humidity", "light", 
    };
    
    private static final String[] arrKeys = { 
        "experiment_jobs", "germplasms" 
    };
    
    private static final String[] objKeys = { 
        "imager" 
    };
    
    public static boolean isStringKey( String s ){ return isContained( s, stringKeys ); }
    public static boolean isIntKey( String s ){ return isContained( s, intKeys ); }
    public static boolean isBoolKey( String s ){ return isContained( s, boolKeys ); }
    public static boolean isEnvKey( String s ){ return isContained( s, envKeys ); }
    public static boolean isArrKey( String s ){ return isContained( s, arrKeys ); }
    public static boolean isObjKey( String s ){ return isContained( s, objKeys ); }
    private static boolean isContained( String s, String[] arr ){
        for( String a : arr )
            if( a.equals( s ) )
                return true;
        return false;
    }
        
    //START INSTANCE VARIABLES
    public final Map< String, String > stringMap = new HashMap();
    public final Map< String, Integer > intMap = new HashMap();
    public final Map< String, Boolean > boolMap = new HashMap();
    public final Map< String, EnvironmentEffect > envMap = new HashMap();   
    public final Map< String, List< Map< String, Object > > > arrMap = new HashMap();   
    public final Map< String, Map< String, Object > > objMap = new HashMap();   
}
