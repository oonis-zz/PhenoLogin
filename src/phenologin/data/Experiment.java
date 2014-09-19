/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template in the editor.
 */
package phenologin.data;

import java.util.ArrayList;
import java.util.List;
import phenologin.ExperimentInfo;
import phenologin.data.EnvironmentEffect;

/**
 * @author Sam <willsc8forwings@gmail.com>
 */
public class Experiment extends ExperimentInfo {

    /*
     * ================================================ MEMBER VARIABLES ================================================
     */
    // private date? jTimeCreated
    // private date? jTimeUpdated
    // private date? jTimeApproved
    // private date? jTimeStart
    // private date? jTimeCompleted
    // private int jPlantAge = -1;
    // private PhenoUser jArchiver = null;
    // private PhenoUser jCollaborator = null;
    // private int jDuration = -1;
    // private boolean jIsPerterb = false;
    // private String jScriptURL = null;
    private final List<EnvironmentEffect> jEnvironmentEffects = new ArrayList<>();

    /*
     * ================================================== CONSTRUCTORS ==================================================
     */
    /**
     * Default Constructor for FullExperiment.
     */
    public Experiment() {

    }
    
}
