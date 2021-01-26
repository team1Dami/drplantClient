package drplant.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import drPlant.classes.Shop;
import drPlant.controller.LoginController;
import drPlant.controller.ShopViewController;
import drPlant.factory.ShopManagerFactory;
import drPlant.factory.UserManagerFactory;
import drPlant.interfaces.ShopManager;
import drPlant.interfaces.UserManager;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public class drPlantApplication extends Application {
    
    private Logger logger = Logger.getLogger("DrPlantClient.main");
    
    @Override
    public void start(Stage stage) {
       Parent root;
        try {
             LoginController controller = new LoginController();
             //ShopViewController controller = new ShopViewController();
            //Load node graph from fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/Login.fxml"));
           // FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/ShopView.fxml"));
            
            root = (Parent) loader.load();
            controller = (loader.getController());
            //Set a reference for Stage
            controller.setStage(stage);
            //Initializes primary stage
            controller.initStage(root);
            
        } catch (IOException ex) {
                Logger.getLogger(drPlantApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
                Logger.getLogger(drPlantApplication.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
