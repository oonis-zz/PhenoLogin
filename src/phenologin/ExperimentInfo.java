/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template in the editor.
 */
package phenologin;

/**
 * @author Sam <willsc8forwings@gmail.com>
 */
public class ExperimentInfo {

    /*
     * ================================================ MEMBER VARIABLES ================================================
     */
    private int jID = -1;
    private String jName = "DEFAULT";

    /*
     * ================================================== CONSTRUCTORS ==================================================
     */
    
    /**
     * Default Constructor for Experiment.
     */
    public ExperimentInfo() {

    }

    /*
     * ================================================ PRIMARY FUNCTIONS ===============================================
     */

    /*
     * ================================================ GETTERS & SETTERS ===============================================
     */
    public int getID() {
        return jID;
    }

    public void setID(int id) {
        jID = id;
    }

    public String getName() {
        return jName;
    }

    public void setName(String name) {
        jName = name;
    }

    /*
     * =============================================== MODIFIER FUNCTIONS ===============================================
     */

    /*
     * ================================================ VISUAL FUNCTIONS ================================================
     */
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "ID: " + jID + "\tName: " + jName;
    }

    /*
     * ================================================ PRIVATE FUNCTIONS ===============================================
     */
}
