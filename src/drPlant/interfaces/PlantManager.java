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
public interface PlantManager {
    
    public <T> T getPlantByPetFriendly(Class<T> responseType, String petFriendly) throws ClientErrorException ;

    public <T> T getPlantByTypeAndClimate(Class<T> responseType, String plantType, String climate) throws ClientErrorException ;

    public void edit(Object requestEntity) throws ClientErrorException ;

    public <T> T getPlantByClimate(Class<T> responseType, String climate) throws ClientErrorException ;

    public <T> T getPlantByPetFriendlyAndClimate(Class<T> responseType, String petFriendly, String climate) throws ClientErrorException ;

    public <T> T getAllPlants(Class<T> responseType) throws ClientErrorException ;

    public void remove(String id) throws ClientErrorException ;

    public <T> T getPlantWithAll(Class<T> responseType, String plantType, String petFriendly, String climate) throws ClientErrorException ;
    
    public <T> T getPlantByCommonName(Class<T> responseType, String commonName) throws ClientErrorException ;

    public <T> T getPlantByTypeAndPetFriendly(Class<T> responseType, String plantType, String petFriendly) throws ClientErrorException ;

    public <T> T getPlantByType(Class<T> responseType, String plantType) throws ClientErrorException ;

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException ;

    public void create(Object requestEntity) throws ClientErrorException ;

}
