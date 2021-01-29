/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.client;

import drPlant.interfaces.EquipmentManager;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:EquipmentFacadeREST
 * [equipment]<br>
 * USAGE:
 * <pre>
 *        EquipmentRESTClient client = new EquipmentRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Eneko
 */
public class EquipmentRESTClient implements EquipmentManager {

    private WebTarget webTarget;
    private Client client;
    private static ResourceBundle resource;
    private String BASE_URI;

    /**
     * Public constructor to set the Rest info
     */
    public EquipmentRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        resource = ResourceBundle.getBundle("drPlant/client/BaseUrl");
        BASE_URI = resource.getString("BaseUri");
        webTarget = client.target(BASE_URI).path("equipment");
    }

    /**
     *  Method to find all the equipment in the database
     * @param <T>
     * @param responseType
     * @return Returns a List of Equipment 
     * @throws ClientErrorException
     */
    public <T> T findAllEquipment(GenericType<T> responseType) throws ClientErrorException {

        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Find all the equipment wich is of the selected type
     * @param <T>
     * @param responseType
     * @param uses The use of the equipment
     * @return Returns a List of Equipment
     * @throws ClientErrorException
     */
    public <T> T findEquipmentByUse(GenericType<T> responseType, String uses) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("uses/{0}", new Object[]{uses}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Edit a specific equipment
     * @param requestEntity
     * @throws ClientErrorException
     */
    public void edit(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Finds a equipment by it's ID
     * @param <T>
     * @param responseType
     * @param id
     * @return The equipment with the ID if exists
     * @throws ClientErrorException
     */
    public <T> T find(GenericType<T> responseType, String id) throws ClientErrorException {

        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Create a new equipment
     * @param requestEntity
     * @throws ClientErrorException
     */
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Find the list of equipment that contains the name subString
     * @param <T>
     * @param responseType
     * @param equipment_name
     * @return Returns a List of Equipment filtred by it's name
     * @throws ClientErrorException
     */
    public <T> T findEquipmentByName(GenericType<T> responseType, String equipment_name) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("equipment_name/{0}", new Object[]{equipment_name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Removes the equipment with the introduced ID
     * @param id
     * @throws ClientErrorException
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }
    
    /**
     *  Find a list of equipment that contains the introduced name and is of the selected use
     * @param <T>
     * @param responseType
     * @param use
     * @param name
     * @return Returns a List of Equipment
     * @throws ClientErrorException
     */
    public <T> T findEquipmentByNameAndUse(GenericType<T> responseType, String use, String name) throws ClientErrorException {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("equipment_name/{0}/{1}", new Object[]{use, name}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        }

    /**
     *
     */
    public void close() {
        client.close();
    }
}
