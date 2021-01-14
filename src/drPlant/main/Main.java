/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.main;

import drPlant.classes.User;
import drPlant.classes.Plague;
import drPlant.factory.PlagueManagerFactory;
import drPlant.factory.UserManagerFactory;
import drPlant.interfaces.UserManager;

import javax.ws.rs.core.GenericType;
import java.util.List;
import javax.ws.rs.ClientErrorException;

/**
 *
 * @author 2dam
 */
public class Main {

    public static void main(String[] args) {
       String login = "user";
       String passwd = "android";
        try {
           UserManager um = UserManagerFactory.getUserManager();
           User u = um.findUserByLoginAndPasswd(User.class, login, passwd);
           System.out.println(u.getFullname());
        } catch (ClientErrorException e) {
            System.out.println(e.getMessage());
        }
    }
}
