/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.factory;

import drPlant.client.PlagueRESTClient;
import drPlant.interfaces.PlagueManager;

/**
 *
 * @author saray
 */
public class PlagueManagerFactory {
    
    public static PlagueManager getPlagueManager(){
        return new PlagueRESTClient();
    }
}
