/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package phenologin.data;

/**
 * 
 * @author Sam <willsc8forwings@gmail.com>
 */
public class Plant {
    private final int jFlatID;
    private final int jID;
    private final boolean jIsEmpty;
    private final String jBarCode;
    private final int jSeedID;
    private final String jName;
    
    public Plant() {
        jFlatID = -1;
        jID = -1;
        jIsEmpty = true;
        jBarCode = "";
        jSeedID = -1;
        jName = "";
    }
    
    public Plant(int flatID, int ID, boolean isEmpty, String barCode, int seedID, String name) {
        jFlatID = flatID;
        jID = ID;
        jIsEmpty = isEmpty;
        jBarCode = barCode;
        jSeedID = seedID;
        jName = name;
    }
    
    public int getFlatID() {
        return jFlatID;
    }
    
    public int getID() {
        return jID;
    }
    
    public boolean getIsEmpty() {
        return jIsEmpty;
    }
    
    public String getBarCode() {
        return jBarCode;
    }
    
    public int getSeedID() {
        return jSeedID;
    }
    
    public String getName() {
        return jName;
    }
}
