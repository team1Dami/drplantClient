/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import static DrPlant.enumerations.UserPrivilege.*;
import drPlant.classes.Shop;
import drPlant.classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class TiendaPopUpController  {

     @FXML
    private Stage StagePopUpTienda;
     
     @FXML
     private TextField tfUrl;
     
     @FXML
     private TextField tfEmail;
      
     @FXML
     private TextField tfLocation;
     
     @FXML
     private Label tfNombre;
     
     @FXML
     private Button btnAtras;
     
     @FXML
     private Button btnGuardar;
        

     /**
      * 
      * @param root
      * @param user
      * @param shop 
      */
    public void initStage(Parent root,User user,Shop shop) {
        Scene scene = new Scene(root);
        StagePopUpTienda.setScene(scene);
        StagePopUpTienda.setResizable(false);
        StagePopUpTienda.show();
        
        tfUrl.setText(shop.getUrl());
        tfEmail.setText(shop.getEmail());
        tfLocation.setText(shop.getLocation());
        tfNombre.disableProperty();
        tfNombre.setText(shop.getShop_name());
        btnAtras.setOnAction(this::handleButtonCancelarAction);
        //btnGuardar.setOnAction(this::);
        if(user.getPrivilege().equals(USER)){
            tfUrl.disableProperty();
            tfEmail.disableProperty();
            tfLocation.disableProperty();
            btnGuardar.setVisible(false);
        }

       // tfEmail.textProperty().addListener(this::textChange);
       // btnEnviar.setOnAction(this::handleButtonEnviar);

    }
    /**
     * 
     * @param event 
     */
    private void handleButtonGuardarAction(ActionEvent event) {
                    
    }
    /**
     * 
     * @param event 
     */
    private void handleButtonCancelarAction(ActionEvent event) {
            StagePopUpTienda.close();         
    }

    
}
