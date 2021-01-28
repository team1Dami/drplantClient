/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.classes.Plague;
import drPlant.enumerations.PlagueType;
import drPlant.factory.PlagueManagerFactory;
import drPlant.interfaces.PlagueManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.ClientErrorException;

/**
 * Controller class for infoPlague' management view . 
 * It contains event handlers and
 * initialization code for the view defined in infoPlague.fmxl file.
 * @author saray
 */
public class InfoPlagueController {
    private static final Logger logger = Logger.getLogger("drPlant.controller.InfoPlagueController");
    @FXML
    private Stage stage;

    @FXML
    private ImageView imgPlague;
    @FXML
    private TextField tfScientName;
    @FXML
    private TextField tfCommonName;
    @FXML
    private ScrollPane scrollPaneDescription;
    @FXML
    private TextArea txAreaDescription;
    @FXML
    private ScrollPane scrollPaneControl;
    @FXML
    private TextArea txAreaControl;
    @FXML
    private ScrollPane scrollPaneRemedy;
    @FXML
    private TextArea txAreaRemedy;
    @FXML
    private RadioButton rbLight;
    @FXML
    private RadioButton rbMedium;
    @FXML
    private RadioButton rbSevere;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnDelete;

    private boolean isAdmin;

    private PlagueManager plagueManager;

    private Plague plague;

    /**
     * Method to get the plague
     * @return plague selected plague to view the information about it
     */
    public Plague getPlague() {
        return plague;
    }

    /**
     * Method to set the plague
     * @param plague plague to be set
     */
    public void setPlague(Plague plague) {
        this.plague = plague;
    }

    /**
     * Method to get the plagueManager
     * @return plagueManager plagueManager to do the request
     */
    public PlagueManager getPlagueManager() {
        return plagueManager;
    }

    /**
     * Method to get the plagueManager
     * @param plagueManager plagueManager to be set
     */
    public void setPlagueManager(PlagueManager plagueManager) {
        this.plagueManager = PlagueManagerFactory.getPlagueManager();
    }

    /**
     * Method to get isAdmin boolean value
     * @return the isAdmin boolean value
     */
    public boolean isIsAdmin() {
        return isAdmin;
    }

    /**
     * Method to set the isAdmin boolean value, if the user is admin the boolean will be true 
     * else if is user, the boolean will be false
     * @param isAdmin is admin boolean value
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Method to set the stage
     *
     * @param stage the stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method to init the stage
     * @param root the root Parent
     * @param isAdmin the boolean value of the isAdmin 
     * @param plague the plague to be show
     */
    public void initStage(Parent root, boolean isAdmin, Plague plague) {
        setIsAdmin(isAdmin);
        setPlague(plague);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Información de la plaga");
        stage.setResizable(false);

        stage.setOnShowing(this::handleWindowShowing);
        stage.show();
    }

    /**
     * Method to show the information about the plague:
     *
     * Behaviour of the infoPlagueView: 
     * In case of being <strong>Admin</strong> the
     * following textFields of the view will be editable:
     * <ul>
     * <li>Common name</li>
     * <li>Description</li>
     * <li>Control</li>
     * <li>Remedy</li>
     * </ul>
     * 
     * * Radio Buttons for the type of plague that will be edited:
     * <ul>
     * <li> type: light </li>
     * <li> type:middle </li>
     * <li> type: severe </li>
     * </ul>
     * 
     * Buttons will also be displayed at the bottom of the view:
     * <ul>
     * <li> Add to </li>
     * <li> Remove (disabled when any row is selected)</li>
     * </ul>
     *
     * If the user is not Admin, only can read the information about the plague
     * and the buttons are not visibles
     *
     * @param event event type: Window event
     */
    private void handleWindowShowing(WindowEvent event) {
        if (plague != null) {
            if (isAdmin) {
                tfScientName.setDisable(true);
                tfScientName.setEditable(false);
                tfCommonName.setEditable(true);
                txAreaDescription.setEditable(true);
                txAreaControl.setEditable(true);
                txAreaRemedy.setEditable(true);

                btnSaveChanges.setVisible(true);
                btnSaveChanges.setOnAction(this::handleSaveChangesAction);
                btnDelete.setVisible(true);
                btnDelete.setOnAction(this::handleDeleteAction);

                rbLight.setDisable(false);
                rbMedium.setDisable(false);
                rbSevere.setDisable(false);

                rbLight.setOnAction(this::handleCheckedRadioButton);
                rbMedium.setOnAction(this::handleCheckedRadioButton);
                rbSevere.setOnAction(this::handleCheckedRadioButton);
                
            } else {
                
                tfScientName.setEditable(false);
                tfCommonName.setEditable(false);
                txAreaDescription.setEditable(false);
                txAreaControl.setEditable(false);
                txAreaRemedy.setEditable(false);
               
                rbLight.setDisable(true);
                rbMedium.setDisable(true);
                rbSevere.setDisable(true);

                btnSaveChanges.setVisible(false);
                btnDelete.setVisible(false);
            }
            
            tfScientName.setText(plague.getScienceName());
            tfCommonName.setText(plague.getCommonName());
            txAreaDescription.setText(plague.getDescription());
            txAreaControl.setText(plague.getControl());
            txAreaRemedy.setText(plague.getRemedy());

            if (plague.getType().equals(PlagueType.light)) {
                rbLight.setSelected(true);
            }
            if (plague.getType().equals(PlagueType.middle)) {
                rbMedium.setSelected(true);
            }
            if (plague.getType().equals(PlagueType.severe)) {
                rbSevere.setSelected(true);
            }
        }

        if (plague == null) {
            
            //   imgPlague.setVisible(false);
            tfScientName.setEditable(true);
            tfScientName.textProperty().addListener(this::handleTextChanged);
            tfCommonName.setEditable(true);
            tfCommonName.textProperty().addListener(this::handleTextChanged);

            txAreaDescription.setEditable(true);
            txAreaDescription.textProperty().addListener(this::handleTextChanged);
            txAreaControl.setEditable(true);
            txAreaControl.textProperty().addListener(this::handleTextChanged);
            txAreaRemedy.setEditable(true);
            txAreaRemedy.textProperty().addListener(this::handleTextChanged);

            rbLight.setSelected(false);
            rbMedium.setSelected(false);
            rbSevere.setSelected(false);

            btnSaveChanges.setOnAction(this::handleSaveChangesAction);
            btnDelete.setOnAction(this::handleDeleteAction);
        }      
        
        scrollPaneDescription.setContent(txAreaDescription);
        scrollPaneDescription.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneDescription.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        scrollPaneControl.setContent(txAreaControl);
        scrollPaneControl.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneControl.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        scrollPaneRemedy.setContent(txAreaRemedy);
        scrollPaneRemedy.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneRemedy.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    /**
     * Method to validate the fields against add a plague
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    private void handleTextChanged(ObservableValue observable, String oldValue,
            String newValue) {
        Alert alert;
        if (newValue != oldValue) {
            if (tfScientName.getText().isEmpty() || tfCommonName.getText().isEmpty()
                    || txAreaDescription.getText().isEmpty() || txAreaControl.getText().isEmpty()
                    || txAreaRemedy.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "Debes rellenar todos los campos!",
                        ButtonType.OK);
            }
            if (!tfScientName.getText().isEmpty() && tfScientName.getText().matches("^[a-zA-Z\\s]*$")) {
                try{
                    plagueManager = PlagueManagerFactory.getPlagueManager();       
                    Plague p = plagueManager.find(Plague.class, tfScientName.getText().toString());
                    if(p!=null){
                        alert = new Alert(Alert.AlertType.INFORMATION,
                        "Esta plaga ya está registrada!",
                        ButtonType.OK);
                    }
                } catch (Exception e){
                    logger.log(Level.SEVERE, "infoPlague find plague");
                }              
            }
            if (!tfCommonName.getText().isEmpty() && !tfCommonName.getText().matches("^[a-zA-Z\\s]*$")) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "El nombre sólo puede contener letras!",
                        ButtonType.OK);
            }
            if (txAreaDescription.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "Debes rellenar la descripción",
                        ButtonType.OK);
            }
            if (txAreaControl.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "Debes rellenar la descripción del control",
                        ButtonType.OK);
            }
            if (txAreaRemedy.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "Debes rellenar la descripción del remedio",
                        ButtonType.OK);
            }
        }
    }

    /**
     * Method to get manage the radioButton checked
     * @param event event - ActionEvent
     */
    private void handleCheckedRadioButton(ActionEvent event) {

        if (rbLight.isSelected()) {
            rbMedium.setSelected(false);
            rbSevere.setSelected(false);
        }
        if (rbMedium.isSelected()) {
            rbLight.setSelected(false);
            rbSevere.setSelected(false);
        }
        if (rbSevere.isSelected()) {
            rbLight.setSelected(false);
            rbMedium.setSelected(false);
        }
    }

    /**
     * Method to get the data of the plague to edit whe the admin clicks on the button save
     * @param event event - ActionEvent
     */
    @FXML
    private void handleSaveChangesAction(ActionEvent event) {
        Alert alert;
        try {
            Plague p = new Plague();

            p.setScienceName(tfScientName.getText().trim());
            p.setCommonName(tfCommonName.getText().trim());
            p.setDescription(txAreaDescription.getText());
            p.setControl(txAreaControl.getText());
            p.setRemedy(txAreaRemedy.getText());

            if (rbLight.isSelected()) {
                p.setType(PlagueType.light);
            }
            if (rbMedium.isSelected()) {
                p.setType(PlagueType.middle);
            }
            if (rbSevere.isSelected()) {
                p.setType(PlagueType.severe);
            }

            alert = new Alert(Alert.AlertType.WARNING, "¿Enviar cambios?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (!alert.getResult().getButtonData().isCancelButton()) {
                plagueManager = PlagueManagerFactory.getPlagueManager();
                try {
                    if (plague != null) {                      
                        plagueManager.edit(p);
                        alert = new Alert(Alert.AlertType.INFORMATION, "Se han guardado los cambios correctamente", ButtonType.OK);
                        alert.showAndWait();
                        stage.close();
                    } else {
                        plagueManager.create(p);
                        alert = new Alert(Alert.AlertType.INFORMATION, "Se ha registrado correctamente", ButtonType.OK);
                        alert.showAndWait();
                        stage.close();
                    }
                } catch (ClientErrorException e) {
                   logger.log(Level.SEVERE, "Error editanto plaga desde infoPlague");
                }
            }

        } catch (ClientErrorException e) {
            logger.log(Level.SEVERE, "Error editanto plaga desde infoPlague", e.getMessage());
            alert = new Alert(Alert.AlertType.WARNING, "Ops! Se ha producido un error inesperado.\nInténtelo de nuevo en unos minutos", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Method to manage the delete button action to delete the plague
     * @param event event - ActionEvent
     */
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        Alert alert;

        if (tfScientName.getText().isEmpty() || tfCommonName.getText().isEmpty()
                || txAreaDescription.getText().isEmpty() || txAreaControl.getText().isEmpty()
                || txAreaRemedy.getText().isEmpty()) {
            btnDelete.setDisable(true);

        } else {

            try {

                String id = tfScientName.getText();
                alert = new Alert(Alert.AlertType.WARNING, "¿Eliminar plaga?\nEsta acción no se puede revertir", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

                if (!alert.getResult().getButtonData().isCancelButton()) {
                    plagueManager = PlagueManagerFactory.getPlagueManager();
                    plagueManager.remove(id);
                    alert = new Alert(Alert.AlertType.INFORMATION, "Se ha eliminado correctamente", ButtonType.OK);
                    alert.showAndWait();
                }

            } catch (ClientErrorException e) {
                logger.log(Level.SEVERE, "Error borrando plaga desde ninfoPlague");
                alert = new Alert(Alert.AlertType.WARNING, "Ops! Se ha producido un error inesperado.\nInténtelo de nuevo en unos minutos", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    private void setOncloseRequest(WindowEvent we) {

        stage.close();
    }
}
