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
     * Method to manage the button Accept and the button Cancel
     *
     * @param event onClick in button Accept and onClick in button Cancel
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
              //  tfScientName.setDisable(true);
                tfScientName.setEditable(false);
                tfCommonName.setDisable(true);
                tfCommonName.setEditable(false);
                txAreaDescription.setDisable(true);
                txAreaDescription.setEditable(false);
                txAreaControl.setDisable(true);
                txAreaControl.setEditable(false);
                txAreaRemedy.setDisable(true);
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
            // codificar cuando no recibe una plaga
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
     *
     * @param event
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

                }
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
