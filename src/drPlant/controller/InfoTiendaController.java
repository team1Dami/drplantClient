/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import static DrPlant.enumerations.UserPrivilege.USER;
import drPlant.classes.Shop;
import drPlant.classes.User;
import drPlant.factory.ShopManagerFactory;
import drPlant.interfaces.ShopManager;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
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
     private TextField tfComision;
     
     @FXML
     private Label tfNombre;
     
     @FXML
     private Button btnAtras;
     
     @FXML
     private Button btnGuardar;
     
     
     
     private Shop shop;  
     private  Alert alert;
     private static final Logger logger = Logger.getLogger("drplant.Controller.InfoTiendaController");

     
     /**
      * Initial stage of the view
      * @param root
      * @param user
      * @param shop 
      */
    public void initStage(Parent root,boolean  admin,Shop shopp){
        shop=shopp;
        Scene scene = new Scene(root);
        StagePopUpTienda.setScene(scene);
        StagePopUpTienda.setResizable(false);
        StagePopUpTienda.initModality(Modality.APPLICATION_MODAL);
        StagePopUpTienda.show();
        
        //put the shop data inside the text fields and the name in the label
        //the label can't be edited
        tfUrl.setText(shop.getUrl());
        tfEmail.setText(shop.getEmail());
        tfLocation.setText(shop.getLocation());
        tfNombre.setText(shop.getShop_name());
        tfComision.setText(Float.toString(shop.getCommission()));
          
        //set the actions for the buttons
        btnAtras.setOnAction(this::handleButtonCancelarAction);
        btnGuardar.setOnAction(this::handleButtonEditarAction);
        
        //If the user is not Admin can't edit the text fields
        if(!admin){
             tfUrl.setEditable(false);
            tfEmail.setEditable(false);
            tfLocation.setEditable(false);
            tfNombre.setDisable(false);
            tfComision.setEditable(false);
            btnGuardar.setVisible(false);
        }
        
        //set the actions for the fields
        tfUrl.textProperty().addListener(this::textChange);
        tfEmail.textProperty().addListener(this::textChange);
        tfLocation.textProperty().addListener(this::textChange);
        tfComision.textProperty().addListener(this::textChange);
        
       

    }  
    
    public void initStage(Parent root,boolean admin){
        Scene scene = new Scene(root);
        StagePopUpTienda.setScene(scene);
        StagePopUpTienda.setResizable(false);
        StagePopUpTienda.initModality(Modality.APPLICATION_MODAL);
        btnGuardar.setDisable(true);
        StagePopUpTienda.show();
        
        //put the shop data inside the text fields and the name in the label
        //the label can't be edited
        
         tfUrl.setText("");
        tfEmail.setText("");
        tfLocation.setText("");
        tfNombre.setText("");
        tfComision.setText("");
          
        //set the actions for the buttons
        btnAtras.setOnAction(this::handleButtonCancelarAction);
        btnGuardar.setOnAction(this::handleButtonGuardarAction);
     
        //set the actions for the fields
        tfUrl.textProperty().addListener(this::textChange);
        tfEmail.textProperty().addListener(this::textChange);
        tfLocation.textProperty().addListener(this::textChange);
        tfComision.textProperty().addListener(this::textChange);

    }
    /**
     * 
     * @param event 
     */
    private void handleButtonGuardarAction(ActionEvent event) {
 
           try{
                  shop = new Shop();
                  shop.setShop_name(tfNombre.getText());
                  shop.setEmail(tfEmail.getText());
                  shop.setLocation(tfLocation.getText());
                 
                  shop.setCommission(Float.parseFloat(tfComision.getText()));
                  shop.setUrl(tfUrl.getText());
                  
                  if(!validateEmail(shop.getEmail())){
                      //the gmail is not correct
                       alert = new Alert(Alert.AlertType.WARNING, "Error en el gmail", ButtonType.OK);
                       alert.showAndWait();
                  }else{
                    Shop shopEsta;
                    ShopManager manager = ShopManagerFactory.getShopManager();
                    shopEsta= manager.getShopByName(Shop.class, shop.getShop_name());
                    System.out.println(shopEsta.getEmail());
                    if(shopEsta != null){
                        //the shop already exist
                         alert = new Alert(Alert.AlertType.WARNING, "Error Este nombre de tienda ya existe", ButtonType.OK);
                         alert.showAndWait();
                    }else{
                         manager.create(shop);
                         
                    }
                  }  
           }catch(Exception e){
               logger.info("no hace bien la creacion de una tienda :(");
           }
           
    }
    
    private void handleButtonEditarAction(ActionEvent event) {
 
           try{
                  shop.setShop_name(tfNombre.getText());
                  shop.setEmail(tfEmail.getText());
                  shop.setLocation(tfLocation.getText());
                  shop.setCommission((float) tfComision.getUserData());
                  shop.setUrl(tfUrl.getText());
                  
                  if(!validateEmail(shop.getEmail())){
                      //the gmail is not correct
                       alert = new Alert(Alert.AlertType.WARNING, "Error en el gmail", ButtonType.OK);
                       alert.showAndWait();
                  }
                  ShopManager manager = ShopManagerFactory.getShopManager();

                  alert = new Alert(Alert.AlertType.WARNING, "Error Este nombre de tienda ya existe", ButtonType.OK,ButtonType.CANCEL);
                  alert.showAndWait();
                  if(alert.getResult().getButtonData().isDefaultButton()){
                       manager.edit(shop);
                  }

                      
                     
                  
           }catch(Exception e){
               
           }
           
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
        
        /*// force the field to be numeric only
        tfComision.textProperty().addListener(new ChangeListener<String>() {
        public void changed(ObservableValue<? extends String> observable, String oldValue,
        String newValue) {
        if (!newValue.matches("\\d*")) {
        tfComision.setText(newValue.replaceAll("[^\\d]", ""));
        btnGuardar.setDisable(false);
        }else{
        btnGuardar.setDisable(true);
        }
        }
        });*/

        //If password field is higher than 255
        if (tfUrl.getText().length() > 255 || tfEmail.getText().length() > 255 
                || tfLocation.getText().length() > 255) {
            btnGuardar.setDisable(true);
        } //If text fields are empty 
        else if (tfEmail.getText().trim().isEmpty()
                || tfUrl.getText().trim().isEmpty()
                ||tfLocation.getText().trim().isEmpty()
                ||tfComision.getText().trim().isEmpty()) {
            btnGuardar.setDisable(true);
        } //Else, enable accept button
        else {
            btnGuardar.setDisable(false);
        }

    }
      
      /**
     * Method to validate the email format
     *
     * @param email
     * @return true or false
     */
    private boolean validateEmail(String email) {
        // Patern to validate the email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        // Compare and see if the email introduced respects the patern establiced
        Matcher mather = pattern.matcher(email);
        if (!mather.find()) {
            return false;
        } else {
            return true;
        }
    }

    
    
    public void setStage(Stage stage){
        this.StagePopUpTienda = stage;
    }

    
}
