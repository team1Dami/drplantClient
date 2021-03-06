/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.client;

import drPlant.classes.User;
import drPlant.interfaces.UserPlantManager;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:UserPlantFacadeREST
 * [userplant]<br>
 * USAGE:
 * <pre>
 *        UserPlantRESTClient client = new UserPlantRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author saray
 */
public class UserPlantRESTClient implements UserPlantManager {

    private WebTarget webTarget;
    private Client client;
    //private static final String BASE_URI = "http://localhost:8080/drplant/webresources";
    private static ResourceBundle resource;
    private String BASE_URI;

    public UserPlantRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        resource = ResourceBundle.getBundle("drPlant/client/BaseUrl");
        BASE_URI = resource.getString("BaseUri");
        webTarget = client.target(BASE_URI).path("userplant");
    }

    public void edit_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public <T> T find_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void create_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public void close() {
        client.close();
    }

    public <T> T getMyPlants(GenericType<T> responseType) {
        /*  WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getMyPlants/", new Object[]{user}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
        .get(responseType);*/
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

}
