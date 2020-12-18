/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author saray
 */
public interface EquipmentManager {
    
     public <T> T findAllEquipment(Class<T> responseType) throws ClientErrorException ;

    public <T> T findEquipmentByUse(Class<T> responseType, String uses) throws ClientErrorException ;

    public void edit(Object requestEntity) throws ClientErrorException ;

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException ;

    public void create(Object requestEntity) throws ClientErrorException ;

    public <T> T findEquipmentByName(Class<T> responseType, String equipment_name) throws ClientErrorException ;

    public <T> T findEquipmentByPrice(Class<T> responseType, String minPrice, String maxPrice) throws ClientErrorException ;

    public void remove(String id) throws ClientErrorException ;
}
