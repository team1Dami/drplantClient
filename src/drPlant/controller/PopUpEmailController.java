/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class PopUpEmailController {

    @FXML
    private Stage StagePopUp;
    
    @FXML
    private TextField tfEmail;
    
    @FXML
    private Button btnEnviar;
    
    @FXML
    private Label MensajeError;
    
     public void initStage(Parent root) {
        Scene scene = new Scene(root);
        StagePopUp.setScene(scene);
        StagePopUp.setResizable(false);
        StagePopUp.show();

        tfEmail.textProperty().addListener(this::textChange);
        btnEnviar.setOnAction(this::handleButtonEnviar);

    }
     
      private void textChange(ObservableValue observable, String oldValue, String newValue) {
       

    }
     /**
     * 
     * @param event 
     */
    private void handleButtonEnviar(ActionEvent event) {
        
        Parent root;
        Stage newStage = new Stage();
        //if in the view signup you press the x the aplication won't stop, it will go back to the login
        //stage.hide();
        // SignUpController controller = new SignUpController();
        //controller = (loader.getController());
        //controller.setStage(newStage);
        //controller.initStage(root);
        //stage.showAndWait();
    }
     public void setStage(Stage stage){
        this.StagePopUp = stage;
    }
    
}
