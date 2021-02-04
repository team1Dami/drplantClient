/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.Encrypted.Encrypt;
import drPlant.classes.User;
import drPlant.enumerations.UserPrivilege;
import drPlant.factory.UserManagerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.ClientErrorException;
import static javax.xml.bind.DatatypeConverter.printHexBinary;

/**
 *
 * @author eneko
 */
public class ChangePasswordController {

    private static final Logger LOOGER = Logger.getLogger("drPlant.controller.EquipmentViewController");

    private static User user;

    public static void setUser(User user) {
        ChangePasswordController.user = user;
    }

    @FXML
    private Stage stage;
    @FXML
    private Button changePassword;
    @FXML
    private PasswordField passwdField;
    @FXML
    private PasswordField passwd2Field;

    /*@FXML
    private MenuController menuControllerController;*/

    /**
     * Set the stage of the view
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initialize the view and set actions on different widgets
     *
     * @param root
     * @param u
     */
    public void initStage(Parent root, User u) {
        //menuControllerController.setUser(u);
        //menuControllerController.setStageOld(stage);
        setUser(u);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cambiar contraseña");
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::setOncloseRequest);
        stage.show();

    }

    private void handleWindowShowing(WindowEvent event) {
        changePassword.setOnAction(this::handleAddAction);
    }

    private void setOncloseRequest(WindowEvent we) {
        Alert alert;
        try {
            alert = new Alert(Alert.AlertType.WARNING,
                    "¿Seguro que no quieres cambiar la contraseña?", ButtonType.OK, ButtonType.CANCEL);//alert to ask the user to confirm
            alert.showAndWait();
            if (alert.getResult().getButtonData().isCancelButton()) {
                alert = new Alert(Alert.AlertType.WARNING,
                        "Se ha cancelado la accion", ButtonType.OK);//alert to advise that the action has being cancel
                alert.showAndWait();
                we.consume();//do as nothing has happen

            }
        } catch (Exception ex) {
            LOOGER.log(Level.SEVERE,
                    "UI LoginController: Error opening users managing window: {0}",
                    ex.getMessage());
            alert = new Alert(Alert.AlertType.WARNING,
                    "No se ha podido cargar la ventana", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void handleAddAction(ActionEvent event) {
        if (passwd2Field.getText().isEmpty() || passwdField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Hay cambios vacios", ButtonType.OK); //alert to ask the user to confirm
            alert.showAndWait();
        } else if (!passwd2Field.getText().equalsIgnoreCase(passwdField.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Las contraseñas no son iguales", ButtonType.OK); //alert to ask the user to confirm
            alert.showAndWait();
        } else {
            //user.setPasswd(passwdField.getText());
            Encrypt encrypt = new Encrypt();

            byte[] passCipher = encrypt.cifrarPass(passwdField.getText().trim());
            String passHex = printHexBinary(passCipher);
            user.setPasswd(passHex);
            try{
            UserManagerFactory.getUserManager().changeCustomPassword(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "La contraseña se ha guardado, le enviaremos un correo de confirmacion", ButtonType.OK); //alert to ask the user to confirm
            alert.showAndWait();
            stage.close();
            }catch (ClientErrorException ex){
                
            }
        }
    }
}
