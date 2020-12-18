/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.factory;

import drPlant.client.EquipmentRESTClient;
import drPlant.interfaces.EquipmentManager;

/**
 *
 * @author saray
 */
public class EquipmentManagerFactory {
    
    public static EquipmentManager getEquipmentManager(){
        return new EquipmentRESTClient();
    }
}
