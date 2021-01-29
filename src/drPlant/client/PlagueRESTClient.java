/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.client;

import drPlant.interfaces.PlagueManager;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:PlagueFacadeREST [plague]<br>
 * USAGE:
 * <pre>
 *        PlagueRESTClient client = new PlagueRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author saray
 */
public class PlagueRESTClient implements PlagueManager{

    private WebTarget webTarget;
    private Client client;
   
    private static ResourceBundle resource;
    private String BASE_URI;

    public PlagueRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        resource = ResourceBundle.getBundle("drPlant/client/BaseUrl");
        BASE_URI = resource.getString("BaseUri");
        webTarget = client.target(BASE_URI).path("plague");
    }

    /**
     * Method to edit a plague
     * @param requestEntity rquestEntity - the entity to be edit
     * @throws ClientErrorException error in serverClient
     */
    @Override
    public void edit(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Method to find a plague
     * @param <T> plague Entity
     * @param responseType the response of the server side
     * @param id the id of the plague to be find
     * @return the plague
     * @throws ClientErrorException error in serverClient
     */
    @Override
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    /**
     * Method to find a plague list by type
     * @param <T> the plague list to be find
     * @param responseType the plague list
     * @param type the PlagueType to find a plague list
     * @return the plague list
     * @throws ClientErrorException error in serverClient
     */
    @Override
    public <T> T findPlaguesByType(Class<T> responseType, String type) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findPlaguesByType/{0}", new Object[]{type}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }
    /**
     * Method to find a plague list by type
     * @param <T> the plague list to be find
     * @param responseType the plague list
     * @param type the PlagueType to find a plague list
     * @return the plague list
     * @throws ClientErrorException error in serverClient
     */
    public <T> T findPlaguesByType(GenericType<T> responseType, String type) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findPlaguesByType/{0}", new Object[]{type}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    /**
     * Method to find all plagues
     * @param <T> the plague list 
     * @param responseType the plague list
     * @return the plague list
     * @throws ClientErrorException error in serverClient
     */
    @Override
    public <T> T findAllPlagues(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }
    
    
    /**
     * Method to find all plagues
     * @param <T> the plague list 
     * @param responseType the plague list
     * @return the plague list GenericType
     * @throws ClientErrorException error in serverClient
     */
    public <T> T findAllPlagues(GenericType <T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    /**
     * Method to create a plague
     * @param requestEntity the entity to be create
     * @throws ClientErrorException error in serverClient
     */
    @Override
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Method to remove a plague by id
     * @param id the id of the plague to be remove
     * @throws ClientErrorException error in serverClient
     */
    @Override
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request().delete();
    }

    /**
     * Method to find a plague by common name
     * @param <T> the entity plague to be find
     * @param responseType the entity plague
     * @param commonName the common name to find the plague entity
     * @return the plague entity
     * @throws ClientErrorException error in serverClient
     */
    @Override
    public <T> T findPlagueByCommonName(Class<T> responseType, String commonName) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findPlagueByCommonName/{0}", new Object[]{commonName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public void close() {
        client.close();
    }
}