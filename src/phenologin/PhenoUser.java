/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phenologin;

/**
 * @author William A. Norman (LordCrekit@gmail.com, normanwi@msu.edu)
 */
public class PhenoUser {

    /*
     * ================================================ MEMBER VARIABLES ================================================
     */
    // private ____ jTimeCreated
    // private ____ jTimeUpdated
    // private String jTitle = null;
    // private String jName = null;
    // private boolean jOptOut = false;
    // private boolean jSelfRegistration = false;
    // private int jInstituteID = -1;
    // private int jDepartmentID = -1;
    // mobile number
    // phone number
    // private String jLabName = null;
    private String jEmail = "Default";

    private int jID = -1;

    /*
     * ================================================== CONSTRUCTORS ==================================================
     */
    /**
     * Default Constructor for PhenoUser.
     */
    public PhenoUser() {
        
    }

    /*
     * ================================================ PRIMARY FUNCTIONS ===============================================
     */

    /*
     * ================================================ GETTERS & SETTERS ===============================================
     */
    
    /**
     * Get the email of the user who is currently logged in
     * @return The users email address. Will return "local" if in offline mode
     */
    public String getEmail() {
        return jEmail;
    }

    void setEmail(String email) {
        jEmail = email;
    }

    public int getID() {
        return jID;
    }

    void setID(int id) {
        jID = id;
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
