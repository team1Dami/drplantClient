package drplant.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import drPlant.classes.Equipment;
import drPlant.controller.EquipmentViewController;
import drPlant.factory.EquipmentManagerFactory;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
    
    @Override
    public void start(Stage primaryStage) {
       Parent root;
        try {
            EquipmentViewController controller = new EquipmentViewController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/EquipmentView.fxml"));

            try {
                root = (Parent) loader.load();
                controller = (loader.getController());
                controller.setStage(primaryStage);
                controller.initStage(root);
            } catch (IOException ex) {
                //Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            //Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        /*List<Equipment> equipmentss = EquipmentManagerFactory.getEquipmentManager()
        .findAllEquipment(new GenericType<List<Equipment>>() {});
        Iterator <Equipment> it = equipmentss.iterator();
        while (it.hasNext()){
        System.out.println(it.next().getEquipment_name());
        }*/
    }
    
}
