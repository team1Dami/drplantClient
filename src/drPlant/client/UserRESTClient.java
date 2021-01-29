/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.client;

import drPlant.interfaces.UserManager;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:UserFacadeREST [user]<br>
 * USAGE:
 * <pre>
 *        UserRESTClient client = new UserRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author saray, Eneko
 */
public class UserRESTClient implements UserManager {

    private WebTarget webTarget;
    private Client client;
    private static ResourceBundle resource;
    private String BASE_URI;

    /**
     *
     */
    public UserRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        resource = ResourceBundle.getBundle("drPlant/client/BaseUrl");
        BASE_URI = resource.getString("BaseUri");
        webTarget = client.target(BASE_URI).path("user");
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param login
     * @param passwd
     * @return
     * @throws ClientErrorException
     */
    public <T> T findUserByLoginAndPasswd(Class<T> responseType, String login, String passwd) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("login/{0}/{1}", new Object[]{login, passwd}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    /*
    Ejemplo de llamada:
    
    List <User> u = UserManagerFactory.getCustomerManager()
                    .findUserByLoginAndPasswd (User.class, login, password);
     */

    /**
     *
     * @param requestEntity
     * @throws ClientErrorException
     */


    public void edit(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param id
     * @return
     * @throws ClientErrorException
     */
    public <T> T find(Class<T> responseType, Integer id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    /**
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    public void create_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     *
     * @param id
     * @throws ClientErrorException
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request()
                .delete();
    }

    /**
     *  Method that change your password for the user witch is asociated with the introduced e-mail
     * @param email
     * @throws ClientErrorException
     */
    public void resetPassword(String email) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("email/{0}", new Object[]{email}));
        resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get();
    }

    /**
     *
     */
    public void close() {
        client.close();
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @return
     * @throws ClientErrorException
     */
    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

}
