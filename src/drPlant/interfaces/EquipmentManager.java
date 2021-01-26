/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Eneko
 */
public interface EquipmentManager {

    public <T> T findAllEquipment(GenericType<T> responseType) throws ClientErrorException;
    
    public <T> T findEquipmentByNameAndUse(GenericType<T> responseType, String use, String name) throws ClientErrorException;

    public <T> T findEquipmentByUse(GenericType<T> responseType, String uses) throws ClientErrorException;

    public void edit(Object requestEntity) throws ClientErrorException;

    public <T> T find(GenericType<T> responseType, String id) throws ClientErrorException;

    public void create(Object requestEntity) throws ClientErrorException;

    public <T> T findEquipmentByName(GenericType<T> responseType, String equipment_name) throws ClientErrorException;

    /*public <T> T findEquipmentByPrice(Class<T> responseType, String minPrice, String maxPrice) throws ClientErrorException ;*/
    public void remove(String id) throws ClientErrorException;

}
