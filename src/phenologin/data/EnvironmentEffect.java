/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template in the editor.
 */
package phenologin.data;

import java.util.HashMap;
import java.util.Map;


public class EnvironmentEffect {
    
    private static final String[] stringKeys = {};
    private static final String[] intKeys = {};
    private static final String[] boolKeys = {};
    private static final String[] envKeys = {};
    
    public static boolean isStringKey( String s ){ return isContained( s, stringKeys ); }
    public static boolean isIntKey( String s ){ return isContained( s, intKeys ); }
    public static boolean isBoolKey( String s ){ return isContained( s, boolKeys ); }
    public static boolean isEnvKey( String s ){ return isContained( s, envKeys ); }
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
}
