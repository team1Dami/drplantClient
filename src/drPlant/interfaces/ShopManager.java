/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author saray
 */
public interface ShopManager {
    
    public void edit(Object requestEntity) throws ClientErrorException ;

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException ;

    public void create(Object requestEntity) throws ClientErrorException ;

    public <T> T getShopByName(Class<T> responseType, String shop_name) throws ClientErrorException ;

    public void remove(String id) throws ClientErrorException ;

    public <T> T findAllShops(GenericType<T> responseType) throws ClientErrorException ;

}
