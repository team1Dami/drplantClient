/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.classes.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class LoginController{

    private static final Logger logger = Logger.getLogger("drplant.Controller.LoginController");
    //Declaration of attributes
    @FXML
    private Stage StageLogin;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfPasswd;
    
    /**
     * Set the stage of the view
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.StageLogin = stage;
    }
    
    /**
     * Initialize the view and set actions on different widgets
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        StageLogin.setScene(scene);
        StageLogin.setResizable(false);
        StageLogin.setTitle("Login");
        StageLogin.show();

        tfLogin.textProperty().addListener(this::textChange);
        tfPasswd.textProperty().addListener(this::textChange);
        btnLogin.setOnAction(this::handleButtonLogin);
        btnRegister.setOnAction(this::handleButtonRegister);
    }
    
    /**
     * Text changed event handler. If both fields are empty the button is
     * disable
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void textChange(ObservableValue observable, String oldValue, String newValue) {
        //disable the Login button

        //If password field is higher than 12
        if (tfPasswd.getText().length() > 12) {
            btnLogin.setDisable(true);
        } //If text fields are empty 
        else if (tfLogin.getText().trim().isEmpty()
                || tfPasswd.getText().trim().isEmpty()) {
            btnLogin.setDisable(true);
        } //Else, enable accept button
        else {
            btnLogin.setDisable(false);
        }

    }
    
    private void handleButtonLogin(ActionEvent event) {
        if (tfLogin.getText().isEmpty() || tfPasswd.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error", ButtonType.APPLY);
        } else {
            User myUser = new User();
            myUser.setLogin(tfLogin.getText().toString());
            myUser.setPasswd(tfPasswd.getText().toString());
           // ClientServer imp = ImpFactory.getImplement();
            User serverUser = null;
            //serverUser = imp.signIn(myUser);

            if (serverUser != null) {

               // LogoutController controller = new LogoutController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Logout.fxml"));
                Parent root;

                try {
                    root = (Parent) loader.load();
                    //controller = (loader.getController());
                    //controller.setStage(stage);
                    //controller.initStage(root);
                } catch (IOException ex) {
                    //Logger.getLogger(LogoutController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                logger.info("User null");
            }
        }

    }
    /**
     * 
     * @param event 
     */
    private void handleButtonRegister(ActionEvent event) {
        
        Parent root;
        Stage newStage = new Stage();
        try {
            //if in the view signup you press the x the aplication won't stop, it will go back to the login 
            //stage.hide();
           // SignUpController controller = new SignUpController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            root = (Parent) loader.load();
            //controller = (loader.getController());
            //controller.setStage(newStage);
            //controller.initStage(root);
            //stage.showAndWait();
        } catch (IOException ex) {
           // Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.WARNING, "No se ha podido cargar la ventana", ButtonType.OK);
        }
    }    
}
