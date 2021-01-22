/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.interfaces;

import javax.ws.rs.ClientErrorException;

/**
 *
 * @author saray
 */
public interface UserManager {
    
    public <T> T findUserByLoginAndPasswd(Class<T> responseType, String login, String passwd) throws ClientErrorException;
    public void edit(Object requestEntity) throws ClientErrorException;
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException;
    public void create_XML(Object requestEntity) throws ClientErrorException;
    public <T> T findAll(Class<T> responseType) throws ClientErrorException;
    public void remove(String id) throws ClientErrorException;  
    public void changePassword(String email) throws ClientErrorException;
}
