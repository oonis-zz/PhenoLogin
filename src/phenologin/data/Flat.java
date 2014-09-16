/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package phenologin.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Sam <willsc8forwings@gmail.com>
 */
public class Flat {
    private List<Plant> jPlants;
    private String jBarCode;
    private int jID;
    private int jJobID;
    
    public Flat() {
        jPlants = new ArrayList<>();
        jBarCode = "";
        jID = -1;
        jJobID = -1;
    }
}
