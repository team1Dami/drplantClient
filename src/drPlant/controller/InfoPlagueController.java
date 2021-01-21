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
import java.util.logging.Level;
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

    private Plague plague;

    /**
     *
     * @return
     */
    public Plague getPlague() {
        return plague;
    }

    /**
     *
     * @param plague
     */
    public void setPlague(Plague plague) {
        this.plague = plague;
    }

    /**
     *
     * @return
     */
    public PlagueManager getPlagueManager() {
        return plagueManager;
    }

    /**
     *
     * @param plagueManager
     */
    public void setPlagueManager(PlagueManager plagueManager) {
        this.plagueManager = PlagueManagerFactory.getPlagueManager();
    }

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
 /*   public void initStage(Parent root, boolean isAdmin) {
        setIsAdmin(false);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Información de la plaga");
        stage.setResizable(false);

        stage.setOnShowing(this::handleWindowShowing);
        stage.show();
    }*/

    /**
     *
     * @param root
     */
    public void initStage(Parent root, boolean isAdmin, Plague plague) {
        setIsAdmin(false);
        this.plague = plague;
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
        if (plague != null && isAdmin) {
            tfScientName.setText(plague.getScienceName());
            tfScientName.setEditable(isAdmin);
            
            tfCommonName.setText(plague.getCommonName());
            tfCommonName.setEditable(isAdmin);
            
            txAreaDescription.setText(plague.getDescription());
            txAreaDescription.setEditable(isAdmin);
            
            txAreaControl.setText(plague.getControl());
            txAreaControl.setEditable(isAdmin);
            
            txAreaRemedy.setText(plague.getRemedy());
            txAreaRemedy.setEditable(isAdmin);
            
            if (plague.getType().equals(PlagueType.light)) {
                rbLight.setSelected(true);
            }
            if (plague.getType().equals(PlagueType.middle)) {
                rbMedium.setSelected(true);
            }
            if (plague.getType().equals(PlagueType.severe)) {
                rbSevere.setSelected(true);
            }

            btnSaveChanges.setOnAction(this::handleSaveChangesAction);
            btnDelete.setOnAction(this::handleDeleteAction);
        }
        if(plague == null){
        // codificar cuando recibe una plaga
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
            if (!tfScientName.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "Debes introducir un nombre de búsqueda",
                        ButtonType.OK);
            }
            if (!tfCommonName.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "Debes introducir un nombre de búsqueda",
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
                alert.showAndWait();
            }

            alert = new Alert(Alert.AlertType.WARNING, "¿Enviar cambios?", ButtonType.YES, ButtonType.NO);
            if (!alert.getResult().getButtonData().isCancelButton()) {
                plagueManager = PlagueManagerFactory.getPlagueManager();
                plagueManager.create(plague);
                alert = new Alert(Alert.AlertType.INFORMATION, "Se ha registrado correctamente", ButtonType.OK);
                alert.showAndWait();
            }

        } catch (ClientErrorException e) {
            alert = new Alert(Alert.AlertType.WARNING, "Ops! Se ha producido un error inesperado.\nInténtelo de nuevo en unos minutos", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     *
     * @param event
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
                alert = new Alert(Alert.AlertType.WARNING, "Ops! Se ha producido un error inesperado.\nInténtelo de nuevo en unos minutos", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    private void setOncloseRequest(WindowEvent we) {

        stage.close();

    }
}
