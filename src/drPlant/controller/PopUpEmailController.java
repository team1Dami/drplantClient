/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.factory.UserManagerFactory;
import drPlant.interfaces.UserManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class PopUpEmailController {

    @FXML
    private Stage StageLogin;
    
    @FXML
    private TextField tfEmail;
    
    @FXML
    private Button btnEnviar;
    
    @FXML
    private Label MensajeError;
    
    private Alert alert;
    
    /**
     * Method that initialize the view
     * @param root 
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        StageLogin.setScene(scene);
        StageLogin.setResizable(false);
        StageLogin.initModality(Modality.APPLICATION_MODAL);
        StageLogin.show();

        tfEmail.textProperty().addListener(this::textChange);
        btnEnviar.setOnAction(this::handleButtonEnviar);
        btnEnviar.setDisable(true);

    }
     
    /**
     * Method to control if the text inside the textfield is correct
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    private void textChange(ObservableValue observable, String oldValue, String newValue) {
       boolean estaBien=false;
       estaBien=validateEmail(newValue);
       if(!estaBien){
           MensajeError.setText("El dato introducido no es correcto, revisalo por favor");
           btnEnviar.setDisable(true);
       }else{
           MensajeError.setText("");
           btnEnviar.setDisable(false);
       }

    }
     /**
     * Method to send the new password to the user
     * @param event 
     */
    private void handleButtonEnviar(ActionEvent event) {
        
        UserManager manager = UserManagerFactory.getUserManager();
        manager.resetPassword(tfEmail.getText());
        alert = new Alert(Alert.AlertType.WARNING,
                "Se ha enviado la nueva contraseña al correo", ButtonType.OK);
        alert.showAndWait();
       StageLogin.close();
    }
    /**
     * Method to set the view a stage
     * @param stage 
     */
    public void setStage(Stage stage){
        this.StageLogin = stage;
    }
     
    /**
     * Method to control if the email is valid 
     * @param email
     * @return a boolean if the email is valid or not
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
    
}
