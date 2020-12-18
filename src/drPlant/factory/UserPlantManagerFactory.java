/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.factory;

import drPlant.client.UserPlantRESTClient;
import drPlant.interfaces.UserPlantManager;

/**
 *
 * @author saray
 */
public class UserPlantManagerFactory {
    
    public static UserPlantManager getUserPlantManager(){
        return new UserPlantRESTClient();
    }
}
