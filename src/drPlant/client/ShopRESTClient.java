/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.client;

import drPlant.interfaces.ShopManager;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:ShopFacadeREST [shop]<br>
 * USAGE:
 * <pre>
 *        ShopRESTClient client = new ShopRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author saray
 */
public class ShopRESTClient implements ShopManager {

    private WebTarget webTarget;
    private Client client;
    //private static final String BASE_URI = "http://localhost:8080/drplant/webresources"; // esta ruta se debe leer de un archivo de propiedades

    private static ResourceBundle resource;
    private String BASE_URI;

    public ShopRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        resource = ResourceBundle.getBundle("drPlant/client/BaseUrl");
        BASE_URI = resource.getString("BaseUri");
        webTarget = client.target(BASE_URI).path("shop");
    }

    public void edit(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
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

    public <T> T getShopByName(Class<T> responseType, String shop_name) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("shop_name/{0}", new Object[]{shop_name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request().delete();
    }

    public <T> T findAllShops(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public void close() {
        client.close();
    }

}
