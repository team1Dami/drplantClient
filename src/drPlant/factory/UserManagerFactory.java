/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.factory;

import drPlant.client.UserRESTClient;
import drPlant.interfaces.UserManager;

/**
 *
 * @author saray
 */
public class UserManagerFactory {
    
    public static UserManager getUserManager(){
        return new UserRESTClient();
    }
}
