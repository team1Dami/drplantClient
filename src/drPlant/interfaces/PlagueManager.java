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
 * @author saray
 */
public interface PlagueManager {
    
    public void edit(Object requestEntity) throws ClientErrorException ;

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException ;

    public <T> T findPlaguesByType(Class<T> responseType, String type) throws ClientErrorException ;

    public <T> T findAllPlagues(Class<T> responseType) throws ClientErrorException ;
    
    public <T> T findAllPlagues(GenericType <T> responseType) throws ClientErrorException ;

    public void create(Object requestEntity) throws ClientErrorException ;
    
    public void remove(String id) throws ClientErrorException ;

    public <T> T findPlagueByCommonName(Class<T> responseType, String commonName) throws ClientErrorException ;

}
