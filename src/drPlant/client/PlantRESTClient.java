/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.client;

import drPlant.interfaces.PlantManager;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:PlantFacadeREST [plant]<br>
 * USAGE:
 * <pre>
 *        PlantRESTClient client = new PlantRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author saray
 */
public class PlantRESTClient implements PlantManager{  // hacer que implemente la interfaz (ésta hay que crearla)

    private WebTarget webTarget;
    private Client client;
    
    private static ResourceBundle resource;
    
    //private String BASE_URI = "http://localhost:8080/drplant/webresources"; // esta ruta se debe leer de un archivo de propiedades
    
    private String BASE_URI;
    
    public PlantRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        
        resource=ResourceBundle.getBundle("drPlant/client/BaseUrl");
        BASE_URI=resource.getString("BaseUri");
        
        webTarget = client.target(BASE_URI).path("plant");
    }

    public <T> T getPlantByPetFriendly(GenericType<T> responseType, String petFriendly) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("petFriendly/{0}", new Object[]{petFriendly}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }
  
    public <T> T getPlantByTypeAndClimate(GenericType<T> responseType, String plantType, String climate) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("TypeAndClimate/{0}/{1}", new Object[]{plantType, climate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public void edit(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }
  
    public <T> T getPlantByClimate(GenericType<T> responseType, String climate) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("climate/{0}", new Object[]{climate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public <T> T getPlantByPetFriendlyAndClimate(GenericType<T> responseType, String petFriendly, String climate) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getPlantByPetFriendlyAndClimate/{0}/{1}", new Object[]{petFriendly, climate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public <T> T getAllPlants(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request().delete();
    }

    public <T> T getPlantWithAll(GenericType<T> responseType, String plantType, String petFriendly, String climate) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getPlantWithAll/{0}/{1}/{2}", new Object[]{plantType, petFriendly, climate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public <T> T getPlantByCommonName(GenericType<T> responseType, String commonName) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getPlantByCommonName/{0}", new Object[]{commonName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public <T> T getPlantByTypeAndPetFriendly(GenericType<T> responseType, String plantType, String petFriendly) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("TypeAndPetFriendly/{0}/{1}", new Object[]{plantType, petFriendly}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public <T> T getPlantByType(GenericType<T> responseType, String plantType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("plantType/{0}", new Object[]{plantType}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void close() {
        client.close();
    }

    @Override
    public <T> T getAllPlants(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }
    
}
