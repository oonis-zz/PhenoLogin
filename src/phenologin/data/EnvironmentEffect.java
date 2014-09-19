/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template in the editor.
 */
package phenologin.data;

/**
 * @author William A. Norman (LordCrekit@gmail.com, normanwi@msu.edu)
 * 
 */
public class EnvironmentEffect {

    /*
     * ================================================ MEMBER VARIABLES ================================================
     */
    private String jType = "";
    private int jConstantValue = -1;
    private int jMaxValue = -1;
    private int jMinValue = -1;
    private boolean jIsPreset = false;
    private int jFluctuatingDayFactor = -1;
    private String jComment = null;

    /*
     * ================================================== CONSTRUCTORS ==================================================
     */
    /**
     * Default Constructor for EnvironmentEffect.
     */
    public EnvironmentEffect() {
        
    }

    /*
     * ================================================ PRIMARY FUNCTIONS ===============================================
     */

    /*
     * ================================================ GETTERS & SETTERS ===============================================
     */
    /**
     *
     * @return
     */
    public String getType() {
        return jType;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        jType = type;
    }

    public int getConstantValue() {
        return jConstantValue;
    }

    public void setConstantValue(int value) {
        jConstantValue = value;
    }

    public int getMaxValue() {
        return jMaxValue;
    }

    public void setMaxValue(int value) {
        jMaxValue = value;
    }

    public int getMinValue() {
        return jMinValue;
    }

    public void setMinValue(int value) {
        jMinValue = value;
    }

    public boolean getIsPreset() {
        return jIsPreset;
    }

    public void setIsPreset(boolean preset) {
        jIsPreset = preset;
    }

    public int getFluctuatingDayFactor() {
        return jFluctuatingDayFactor;
    }

    public void setFluctuatingDayFactor(int factor) {
        jFluctuatingDayFactor = factor;
    }

    public String getComment() {
        return jComment;
    }

    public void setComment(String comment) {
        jComment = comment;
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
