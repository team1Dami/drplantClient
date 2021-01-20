/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import DrPlant.enumerations.PlagueType;
import drPlant.classes.Plague;
import drPlant.factory.PlagueManagerFactory;
import drPlant.interfaces.PlagueManager;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.ClientErrorException;

/**
 *
 * @author saray
 */
public class InfoPlagueController {

    @FXML
    private Stage stage;

    @FXML
    private ImageView imgPlague;
    @FXML
    private TextField tfScientName;
    @FXML
    private TextField tfCommonName;
    @FXML
    private TextArea txAreaDescription;
    @FXML
    private TextArea txAreaControl;
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

    /**
     *
     * @return
     */
    public boolean isIsAdmin() {
        return isAdmin;
    }

    /**
     *
     * @param isAdmin
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
     *
     * @param root
     */
    public void initStage(Parent root, boolean isAdmin ) {
        setIsAdmin(isAdmin);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Información de la plaga");
        stage.setResizable(false);

        stage.setOnShowing(this::handleWindowShowing);
        stage.show();
    }
    /**
     *
     * @param root
     */
    public void initStage(Parent root, boolean isAdmin, Plague plague) {
        setIsAdmin(isAdmin);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Información de la plaga");
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

        //   imgPlague.setVisible(false);
        tfScientName.setEditable(isAdmin);
        tfScientName.textProperty().addListener(this::handleTextChanged);
        tfCommonName.setEditable(isAdmin);
        tfCommonName.textProperty().addListener(this::handleTextChanged);

        txAreaDescription.setEditable(isAdmin);
        txAreaDescription.textProperty().addListener(this::handleTextChanged);
        txAreaControl.setEditable(isAdmin);
        txAreaControl.textProperty().addListener(this::handleTextChanged);
        txAreaRemedy.setEditable(isAdmin);
        txAreaRemedy.textProperty().addListener(this::handleTextChanged);

        rbLight.setSelected(false);
        rbMedium.setSelected(false);
        rbSevere.setSelected(false);

        btnSaveChanges.setOnAction(this::handleSaveChangesAction);
        btnDelete.setOnAction(this::handleDeleteAction);
    }

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
            if (!tfScientName.getText().isEmpty() && !tfScientName.getText().matches("[a-zA-Z]")) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "El nombre científico sólo puede contener letras",
                        ButtonType.OK);
            }
            if (!tfCommonName.getText().isEmpty() && !tfCommonName.getText().matches("[a-zA-Z]+")) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "El nombre común sólo puede contener letras",
                        ButtonType.OK);
            }
            if (!txAreaDescription.getText().matches("[a-zA-Z]+\\.)")) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "La descripción sólo puede contener letras",
                        ButtonType.OK);
            }
            if (!txAreaControl.getText().matches("[a-zA-Z]+\\.)")) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "La descripción sólo puede contener letras",
                        ButtonType.OK);
            }
            if (!txAreaRemedy.getText().matches("[a-zA-Z]+\\.)")) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "La descripción sólo puede contener letras",
                        ButtonType.OK);
            }
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    private void handleSaveChangesAction(ActionEvent event) {
        Alert alert;
        try {
            Plague plague = new Plague();

            plague.setScienceName(tfScientName.getText().trim());
            plague.setCommonName(tfCommonName.getText().trim());
            plague.setDescription(txAreaDescription.getText());
            plague.setControl(txAreaControl.getText());
            plague.setRemedy(txAreaRemedy.getText());

            if (rbLight.isSelected()) {
                plague.setType(PlagueType.light);
            }
            if (rbMedium.isSelected()) {
                plague.setType(PlagueType.middle);
            }
            if (rbSevere.isSelected()) {
                plague.setType(PlagueType.severe);
            }
            if (!rbLight.isSelected() || !rbMedium.isSelected() || !rbSevere.isSelected()) {
                alert = new Alert(Alert.AlertType.WARNING, "Debes seleccionar un tipo de gravedad!", ButtonType.OK);
            }

            plagueManager = PlagueManagerFactory.getPlagueManager();
            plagueManager.create(plague);

        } catch (ClientErrorException e) {
            alert = new Alert(Alert.AlertType.WARNING, "Ops! Se ha producido un error inesperado.\nInténtelo de nuevo en unos minutos", ButtonType.OK);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        Alert alert;
        if(tfScientName.getText().isEmpty() || tfCommonName.getText().isEmpty()
                    || txAreaDescription.getText().isEmpty() || txAreaControl.getText().isEmpty()
                    || txAreaRemedy.getText().isEmpty()){
            btnDelete.setDisable(true);
        }
        try {
            
            String id = tfScientName.getText();
            
            plagueManager = PlagueManagerFactory.getPlagueManager();
            plagueManager.remove(id);
            
        }catch (ClientErrorException e){
            alert = new Alert(Alert.AlertType.WARNING, "Ops! Se ha producido un error inesperado.\nInténtelo de nuevo en unos minutos", ButtonType.OK);
        }
    }
}
