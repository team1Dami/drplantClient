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
public interface UserPlantManager {
    
    public void edit_XML(Object requestEntity) throws ClientErrorException ;

    public <T> T find_XML(Class<T> responseType, String id) throws ClientErrorException ;
 
    public void create_XML(Object requestEntity) throws ClientErrorException ;

    public void remove(String id) throws ClientErrorException ;
}
