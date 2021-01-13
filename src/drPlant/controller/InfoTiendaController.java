/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import static DrPlant.enumerations.UserPrivilege.*;
import drPlant.classes.Shop;
import drPlant.classes.User;
import javafx.beans.value.ObservableValue;
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
public class InfoTiendaController  {

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
     
     
     
     private Shop shop;  
     
     /**
      * Initial stage of the view
      * @param root
      * @param user
      * @param shop 
      */
    public void initStage(Parent root,User user,Shop shopp){
        shop=shopp;
        Scene scene = new Scene(root);
        StagePopUpTienda.setScene(scene);
        StagePopUpTienda.setResizable(false);
        StagePopUpTienda.show();
        
        //put the shop data inside the text fields and the name in the label
        //the label can't be edited
        tfUrl.setText(shop.getUrl());
        tfEmail.setText(shop.getEmail());
        tfLocation.setText(shop.getLocation());
        tfNombre.disableProperty();
        tfNombre.setText(shop.getShop_name());
          
        //set the actions for the buttons
        btnAtras.setOnAction(this::handleButtonCancelarAction);
        btnGuardar.setOnAction(this::handleButtonGuardarAction);
        
        //If the user is not Admin can't edit the text fields
        if(user.getPrivilege().equals(USER)){
            tfUrl.disableProperty();
            tfEmail.disableProperty();
            tfLocation.disableProperty();
            btnGuardar.setVisible(false);
        }

    }
    /**
     * 
     * @param event 
     */
    private void handleButtonGuardarAction(ActionEvent event) {
           Shop newShop = shop; 
           
    }
    /**
     * 
     * @param event 
     */
    private void handleButtonCancelarAction(ActionEvent event) {
            StagePopUpTienda.close();         
    }
    
      private void textChange(ObservableValue observable, String oldValue, String newValue) {
        //disable the guardar button

        //If password field is higher than 255
        if (tfUrl.getText().length() > 255 || tfEmail.getText().length() > 255 
                || tfLocation.getText().length() > 255) {
            btnGuardar.setDisable(true);
        } //If text fields are empty 
        else if (tfEmail.getText().trim().isEmpty()
                || tfUrl.getText().trim().isEmpty()
                ||tfLocation.getText().trim().isEmpty()) {
            btnGuardar.setDisable(true);
        } //Else, enable accept button
        else {
            btnGuardar.setDisable(false);
        }

    }

    
}
