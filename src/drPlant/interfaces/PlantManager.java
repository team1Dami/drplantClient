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
public interface PlantManager {
    
    public <T> T getPlantByPetFriendly(GenericType<T> responseType, String petFriendly) throws ClientErrorException ;

    public <T> T getPlantByTypeAndClimate(GenericType<T> responseType, String plantType, String climate) throws ClientErrorException ;

    public void edit(Object requestEntity) throws ClientErrorException ;

    public <T> T getPlantByClimate(GenericType<T> responseType, String climate) throws ClientErrorException ;

    public <T> T getPlantByPetFriendlyAndClimate(GenericType<T> responseType, String petFriendly, String climate) throws ClientErrorException ;

    public <T> T getAllPlants(Class<T> responseType) throws ClientErrorException ;
  
    public <T> T getAllPlants(GenericType<T> responseType) throws ClientErrorException ;

    public void remove(String id) throws ClientErrorException ;

    public <T> T getPlantWithAll(GenericType<T> responseType, String plantType, String petFriendly, String climate) throws ClientErrorException ;
    
    public <T> T getPlantByCommonName(GenericType<T> responseType, String commonName) throws ClientErrorException ;

    public <T> T getPlantByTypeAndPetFriendly(GenericType<T> responseType, String plantType, String petFriendly) throws ClientErrorException ;

    public <T> T getPlantByType(GenericType<T> responseType, String plantType) throws ClientErrorException ;

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException ;

    public void create(Object requestEntity) throws ClientErrorException ;

}
