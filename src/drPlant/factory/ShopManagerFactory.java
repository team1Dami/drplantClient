/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.factory;

import drPlant.client.ShopRESTClient;
import drPlant.interfaces.ShopManager;

/**
 *
 * @author saray
 */
public class ShopManagerFactory {
    
    public static ShopManager getShopManager() {
        return new ShopRESTClient();
    }
}
