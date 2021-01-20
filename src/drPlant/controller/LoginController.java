/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import DrPlant.enumerations.UserPrivilege;
import drPlant.Encrypted.CifradoPublica;
import drPlant.classes.User;
import drPlant.factory.UserManagerFactory;
import drPlant.interfaces.UserManager;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.codec.binary.Hex;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class LoginController{
    private Alert alert;

    private static final Logger logger =
            Logger.getLogger("drplant.Controller.LoginController");
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
    
    @FXML
    private Hyperlink nuevaContraseña;
    
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
        nuevaContraseña.setOnAction(this::handleLinkNewPassword);
        StageLogin.setOnCloseRequest(this::setOncloseRequest);
        
        
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

        //If password field is higher than 255
        if (tfPasswd.getText().length() > 255 || tfLogin.getText().length() > 255) {
            btnLogin.setDisable(true);
        } //If text fields are empty 
        else if (tfLogin.getText().trim().isEmpty()
                || tfPasswd.getText().trim().isEmpty()){
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

            
            //cipher of the password
            CifradoPublica cifrado = new CifradoPublica();
            
            //El string que se recoge del textfield se cifra y se convierte en una array de bytes
            //Ese array de bytes hay que pasarlo a a hexadecimal
            
            byte[] cifrada = cifrado.cifrarTexto(tfPasswd.getText().toString());//Change to array of bytes
            myUser.setPasswd(Hex.encodeHexString(cifrada));//change the array of bytes and introduce inside the password of the user

            
            //find the user by login and password 
            UserManager imp = UserManagerFactory.getUserManager();
            User serverUser = null;
            serverUser = imp.findUserByLoginAndPasswd(User.class, tfLogin.getText(), tfPasswd.getText());

            
            if (serverUser != null) { //user exists

               // LogoutController controller = new LogoutController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Logout.fxml"));//me falta la siguiente ventana
                Parent root;

                try {
                    root = (Parent) loader.load();
                    //controller = (loader.getController());
                    //controller.setStage(stage);
                    //controller.initStage(root);
                } catch (IOException ex) {
                    //Logger.getLogger(LogoutController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else { //user doesn't exist
                logger.info("User null");
            }
        }

    }
    /**
     * Method that control the button register
     * @param event 
     */
    private void handleButtonRegister(ActionEvent event) {
        
        Parent root;
        try {
            //if in the view signup you press the x the aplication won't stop, it will go back to the login 
            //stage.hide();
            ShopViewController controller = new ShopViewController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/ShopView.fxml"));//necesito la pagina principal para colocar
            root = (Parent) loader.load();
            controller = (loader.getController());
            controller.setStage(StageLogin);
            controller.initStage(root);
            //newStage.showAndWait();
        } catch (IOException ex) {
           // Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "No se ha podido cargar la ventana", ButtonType.OK);
        }
    }
    /**
     * Method that controls if the hiperlink it's clicked
     * @param event 
     */
    private void handleLinkNewPassword(ActionEvent event) {
        
        Parent root;
        Stage newStage = new Stage();
        try {
            //if you press the x the aplication won't stop, it will go back to the login 
            PopUpEmailController controller = new PopUpEmailController();
            //LoginController controller = new LoginController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/NewEmail.fxml"));
            root = (Parent) loader.load();
            controller = (loader.getController());
            controller.setStage(newStage);
            controller.initStage(root);
            //newStage.showAndWait();
        } catch (IOException ex) {
           // Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, ex);
            alert = new Alert(Alert.AlertType.WARNING,
                    "No se ha podido cargar la ventana", ButtonType.OK);
            alert.showAndWait();
        }
    }
    

    /**
     * method that asks when you press the x if you want to go back or not,
     * if you press OK you go back to login otherwhise you stay in the signup
     * @param we 
     */
     private void setOncloseRequest(WindowEvent we){
        
         try {
           alert = new Alert(Alert.AlertType.WARNING,
                   "Desea Salir de esta ventana", ButtonType.OK,ButtonType.CANCEL);//alert to ask the user to confirm
           alert.showAndWait();
           if(alert.getResult().getButtonData().isCancelButton()){          
               alert = new Alert(Alert.AlertType.WARNING,
                       "Se ha cancelado la accion",ButtonType.OK);//alert to advise that the action has being cancel
               alert.showAndWait();
               we.consume();//do as nothing has happen
            
           }    
        } catch (Exception ex) {
            logger.log(Level.SEVERE,
                    "UI LoginController: Error opening users managing window: {0}",
                    ex.getMessage());
           alert = new Alert(Alert.AlertType.WARNING,
                   "No se ha podido cargar la ventana", ButtonType.OK);
           alert.showAndWait();
        }
    }
}
