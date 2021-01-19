package drPlant.controller;

import drPlant.Encrypted.Encrypt;
import drPlant.classes.User;
import drPlant.enumerations.UserPrivilege;
import drPlant.enumerations.Userstatus;
import drPlant.factory.UserManagerFactory;
import drPlant.interfaces.UserManager;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import javax.ws.rs.ClientErrorException;
import static javax.xml.bind.DatatypeConverter.printHexBinary;

/**
 * Class to control the elements of the signUp UI
 *
 * @author saray
 */
public class SignUpController {

    private static final Logger logger = Logger.getLogger("drPlant.controller.SignUpController");

    private static final int MAX_PASS_LENGHT = 12;
    private static final int MIN_PASS_LENGHT = 6;

    @FXML
    private Stage stage;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfPasswd;  //min 6 max 12
    @FXML
    private PasswordField tfPasswd2;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnAccept;
    private Encrypt encrypt;

    /**
     * Method to set the stage
     *
     * @param stage the stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method to initialice the stage and add the events to the elements of the
     * UI
     *
     * @param root Parent of the stage
     */
    public void initStage(Parent root) {
        logger.info("Initializing signUp stage");

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Formulario de registro");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);

        stage.show();
    }

    /**
     * Method to manage the button Accept and the button Cancel
     *
     * @param event onClick in button Accept and onClick in button Cancel
     */
    private void handleWindowShowing(WindowEvent event) {

        tfFullName.setPromptText("Introduzca su nombre completo");
        tfUser.setPromptText("Introduzca un nombre de usuario");
        tfEmail.setPromptText("Introduzca un email: example@example.com");
        tfPasswd.setPromptText("Introduzca una contraseña");
        tfPasswd2.setPromptText("Repita su contraseña");

        tfFullName.focusedProperty().addListener(this::handleFocusChanged);
        tfUser.focusedProperty().addListener(this::handleFocusChanged);
        tfEmail.focusedProperty().addListener(this::handleFocusChanged);
        tfPasswd.focusedProperty().addListener(this::handleFocusChanged);
        tfPasswd2.focusedProperty().addListener(this::handleFocusChanged);

        btnCancel.setOnAction(this::handleButtonCancelarAction);
        btnAccept.setOnAction(this::handleButtonAceptarAction);
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void handleFocusChanged(ObservableValue observable, Boolean oldValue,
            Boolean newValue) {

        if (oldValue) {
            if (tfFullName.getText().isEmpty() && tfUser.getText().isEmpty()
                    && tfEmail.getText().isEmpty() && tfPasswd.getText().isEmpty()
                    && tfPasswd2.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Debes rellenar todos los campos",
                        ButtonType.OK);
            }
            if (!tfEmail.getText().isEmpty() && !validateEmail(tfEmail.getText().trim())) {
                // hacer que se muestre label con texto rojo
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "El email tiene formato incorrecto",
                        ButtonType.OK);
                
                //  IMPORTANTE  ------> IMPLEMENTAR MÉTODO findUserByEmail() DEL SERVER!!!
            }
            if (!tfFullName.getText().isEmpty() && !tfFullName.getText().matches("[a-zA-Z]+")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "El nombre no puede contener números",
                        ButtonType.OK);
            }
            if (!tfUser.getText().isEmpty() && tfUser.getText().contains(" ")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "El usuario no puede contener espacios en blanco",
                        ButtonType.OK);
            }
            if(!tfUser.getText().isEmpty() && !tfUser.getText().contains(" ")){
                try{
                    UserManager um = UserManagerFactory.getUserManager();
                    // IMPORTANTE -----> IMPLEMENTAR MÉTODO findUserByLogin DEL SERVER!!!!!!
                    // User u = um.findUserByLogin(tfUser.getText().trim());
                } catch(Exception e){
                     Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Este usuario ya está registrado!",
                        ButtonType.OK);
                }
            }
            if (!tfPasswd.getText().isEmpty() && tfPasswd.getText().contains(" ")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "La contraseña no puede contener espacios en blanco",
                        ButtonType.OK);
            }
            if (!tfPasswd2.getText().isEmpty() && tfPasswd2.getText().contains(" ")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "La contraseña no puede contener espacios en blanco",
                        ButtonType.OK);
            }
            if (!tfPasswd.getText().trim().equals(tfPasswd2.getText().trim())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Las contraseñas no coinciden",
                        ButtonType.OK);
            }
        }
    }

    /**
     * Method to manage the action on the button Accept First of all: the
     * password is encrypted and converted to hexadecimal format. Then the
     * values of the LastAccess and LastPasswdChange are be setted whit the date
     * of today the User privilege is setted as USER and User Status is setted
     * as ENABLED Then the new User is sending to the server to be inserted in
     * the DB (drplant.user) After that, the LoginView is showing to access to
     * the app whit the credentials and the SignUpView is closed
     *
     * @param event OnClick in button Accept
     */
    @FXML
    private void handleButtonAceptarAction(ActionEvent event) {
        Alert alert;
        try {
            User newUser = new User();
            byte[] passCipher = encrypt.cifrarPass(tfPasswd.getText().trim());
            String passHex = printHexBinary(passCipher);

            newUser.setPasswd(passHex);
            newUser.setLastAccess(Date.valueOf(LocalDate.now()));
            newUser.setLastPasswdChange(Date.valueOf(LocalDate.now()));
            newUser.setPrivilege(UserPrivilege.USER);
            newUser.setStatus(Userstatus.ENABLE);

            UserManager um = UserManagerFactory.getUserManager();
            um.create_XML(newUser);
            /*   FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent) loader.load();
            LoginController controller = ((LoginController) loader.getController());
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root);*/

 /*alert = new Alert(Alert.AlertType.ERROR, "No se ha podido conectar con el servidor."
                            + "Inténtelo más tarde.", ButtonType.OK);
                    alert.showAndWait();*/
        } catch (ClientErrorException e) {
            logger.log(Level.SEVERE, "User can not be set", e.getMessage());
            alert = new Alert(Alert.AlertType.ERROR, "No se ha podido conectar con el servidor."
                    + "Inténtelo más tarde.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Method to manage the action on the button Cancel if is clicked the logIn
     * view is opened, if it can't be opened an alert message is showing
     *
     * @param event onClik in button Cancel
     */
    @FXML
    private void handleButtonCancelarAction(ActionEvent event
    ) {

        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent) loader.load();
            /*    LoginController controller = ((LoginController) loader.getController());
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root);*/

        } catch (Exception ex) {
            logger.log(Level.SEVERE,
                    "UI LoginController: Error opening users managing window: {0}",
                    ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No se ha podido cargar la ventana");
            alert.showAndWait();
        }
    }

    /**
     * Method to validate the email format throws a pattern
     *
     * @param email type of object: String
     * @return true if the email matches or false if its not
     */
    private boolean validateEmail(String email) {
        // Patron para validar el email

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        if (!mather.find()) {
            return false;
        } else {
            return true;
        }
    }
}
