package drPlant.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

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
    private TextField tfPasswd;  //min 6 max 12
    @FXML
    private TextField tfPasswd2;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnAccept;

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
        tfFullName.setPromptText("Introduzca su nombre completo");
        tfUser.setPromptText("Introduzca un nombre de usuario");
        tfEmail.setPromptText("Introduzca un email: example@example.com");
        tfPasswd.setPromptText("Introduzca una contraseña");
        tfPasswd2.setPromptText("Repita su contraseña");

        stage.show();
    }

    /**
     * Method to manage the button Accept and the button Cancel
     *
     * @param event onClick in button Accept and onClick in button Cancel
     */
    private void handleWindowShowing(WindowEvent event) {

        btnCancel.setOnAction(this::handleButtonCancelarAction);
        btnAccept.setOnAction(this::handleButtonAceptarAction);
    }

    /**
     * Method to manage the action on the button Accept First of all: get the
     * values of the text fields and set to the User class After sends the
     * values to the ClientServerImplementation class If the register is
     * succesfull the login view is opened
     *
     * @param event OnClick in button Accept
     */
    @FXML
    private void handleButtonAceptarAction(ActionEvent event) {
             
        // I HAVE TO CODE IT
        
        
        /* Alert alert;
        if (!validateFields(tfUser.getText())) {
            alert = new Alert(Alert.AlertType.WARNING, "El usuario no puede contener espacios en blanco", ButtonType.OK);
            alert.showAndWait();
        } else if (!validateFields(tfPasswd.getText())) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("La contraseña no puede contener espacios en blanco");
            alert.showAndWait();
        } else if (!validateFields(tfPasswd2.getText())) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("La contraseña no puede contener espacios en blanco");
            alert.showAndWait();
        } else {
            
            
             try {
                User myUser;
                if (validateEmail(tfEmail.getText())) {
                    
                    myUser = new User();
                    myUser.setFullname(tfFullName.getText().toString());
                    myUser.setLogIn(tfUser.getText().toString());
                    myUser.setEmail(tfEmail.getText().toString());
                    myUser.setPasswd(tfPasswd.getText().toString());
                    ClientServer imp = ImpFactory.getImplement();
                    User serverUser = imp.signUp(myUser);

                    if (null != serverUser) {
                        alert = new Alert(Alert.AlertType.INFORMATION, "Se ha registrado correctamente", ButtonType.OK);
                        alert.showAndWait();
                        FXMLLoader loader
                                = new FXMLLoader(getClass().getResource("Login.fxml"));
                        Parent root = (Parent) loader.load();
                        LoginController controller = ((LoginController) loader.getController());
                        controller = (loader.getController());
                        controller.setStage(stage);
                        controller.initStage(root);
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR, "No se ha podido conectar con el servidor."
                                + "Inténtelo más tarde.", ButtonType.OK);
                        alert.showAndWait();
                        FXMLLoader loader
                                = new FXMLLoader(getClass().getResource("Login.fxml"));
                        Parent root = (Parent) loader.load();
                        LoginController controller = ((LoginController) loader.getController());
                        controller = (loader.getController());
                        controller.setStage(stage);
                        controller.initStage(root);
                    }
                }
            } catch (EmailFormatException ex) {
                Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                alert = new Alert(Alert.AlertType.WARNING, ex.getMessage(), ButtonType.OK);
                alert.showAndWait();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "User can not set", e.getMessage());
                alert = new Alert(Alert.AlertType.ERROR, "No se ha podido conectar con el servidor."
                        + "Inténtelo más tarde.", ButtonType.OK);
                alert.showAndWait();
            
        }}*/
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
        String regex = "\"^[\\\\w-_\\\\.+]*[\\\\w-_\\\\.]\\\\@([\\\\w]+\\\\.)+[\\\\w]+[\\\\w]$\"";
        
        return email.matches(regex);
       /* Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        if (!mather.find()) {
            return false;
        } else {
            return true;
        }*/
    }

    /**
     * Method to validate that the tfUser and the tfPassword doesn't contains
     * white space
     *
     * @param text String that contains the value of tfUser or the value of
     * tfPassword
     * @return true if don't contains white space or returns false if contains
     * white space
     */
    private boolean validateFields(String text) {

        if (text.contains(" ")) {
            return false;
        } else {
            return true;
        }

    }
}
