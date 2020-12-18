/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.factory;

import drPlant.client.PlantRESTClient;
import drPlant.interfaces.PlantManager;

/**
 *
 * @author saray
 */
public class PlantManagerFactory {
    
    public static PlantManager getPlantManager(){
        return new PlantRESTClient();
    }
}
