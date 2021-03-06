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
public interface UserManager {    
    public <T> T findUserByLoginAndPasswd(Class<T> responseType, String login, String passwd) throws ClientErrorException;
    public void edit(Object requestEntity) throws ClientErrorException;
    public <T> T find(Class<T> responseType, Integer id) throws ClientErrorException;
    public void create_XML(Object requestEntity) throws ClientErrorException;
    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException;
    public void remove(String id) throws ClientErrorException;  
    public void resetPassword(String email) throws ClientErrorException;
}
