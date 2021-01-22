/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import DrPlant.enumerations.Use;
import drPlant.classes.Equipment;
import drPlant.classes.User;
import drPlant.factory.EquipmentManagerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;

/**
 *
 * @author Eneko
 */
public class EquipmentDetailsController {

    private static final Logger LOOGER = Logger.getLogger("drPlant.controller.EquipmentDetailsController");

    boolean editar = true;

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        EquipmentDetailsController.user = user;
    }

    //Declaration of attributes
    @FXML
    private Stage stage;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lblNombre;
    @FXML
    private TextField inputNombre;
    @FXML
    private Label lblDescripcion;
    @FXML
    private TextArea inputDescription;
    @FXML
    private Label lblUso;
    @FXML
    private ChoiceBox choiceBoxUso;
    @FXML
    private Label lblPrecio;
    @FXML
    private TextField inputPrecio;
    @FXML
    private ImageView imageView;

    private static Equipment equip;

    public static Equipment getEquip() {
        return equip;
    }

    public static void setEquip(Equipment equip) {
        EquipmentDetailsController.equip = equip;
    }

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
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Equipamiento");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::setOncloseRequest);
        stage.showAndWait();

    }

    private void handleWindowShowing(WindowEvent event) {
        //btnGuardar.setDisable(true);
        try {
            choiceBoxUso.setItems(FXCollections.observableArrayList("General", "Sustrato", "Riego"));
            choiceBoxUso.setValue("General");
            inputNombre.setText(equip.getEquipment_name());
            inputDescription.setText(equip.getEquipment_description());
            inputPrecio.setText(String.valueOf(equip.getPrice()));
            if (equip.getUse().toString().equalsIgnoreCase("riego")) {
                choiceBoxUso.setValue("Riego");
            } else if (equip.getUse().toString().equalsIgnoreCase("sustrato")) {
                choiceBoxUso.setValue("Sustrato");
            } else {
                choiceBoxUso.setValue("General");
            }
        } catch (NullPointerException ex) {
            editar = false;
        }

        btnGuardar.setOnAction(this::handleSaveAction);
    }

    private void setOncloseRequest(WindowEvent we) {
        Alert alert;
        try {
            alert = new Alert(Alert.AlertType.WARNING,
                    "¿Si sales se perderan los cambios, estas seguro que quieres salir?", ButtonType.OK, ButtonType.CANCEL);//alert to ask the user to confirm
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

    @FXML
    private void handleSaveAction(ActionEvent event) {
        boolean correcto = true;
        try {
            if (inputDescription.getText().isEmpty()) {
                correcto = false;
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Por favor, introduce una descripcion", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (NullPointerException ex) {
            correcto = false;
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Por favor, introduce una descripcion", ButtonType.OK);
            alert.showAndWait();
        }
        if (inputNombre.getText().isEmpty()) {
            correcto = false;
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Por favor, introduce un nombre", ButtonType.OK);
            alert.showAndWait();
        }
        if (inputPrecio.getText().isEmpty()) {
            correcto = false;
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Por favor, introduce un precio", ButtonType.OK);
            alert.showAndWait();
        }
        if (correcto) {
            equip.setEquipment_description(inputDescription.getText());
            equip.setEquipment_name(inputNombre.getText());
            String precio;
            float precioFinal;
            precio = inputPrecio.getText();

            if (choiceBoxUso.getValue().toString().equalsIgnoreCase("Sustrato")) {
                equip.setUse(Use.sustrato);
            } else if (choiceBoxUso.getValue().toString().equalsIgnoreCase("Riego")) {
                equip.setUse(Use.riego);
            } else {
                equip.setUse(Use.general);
            }

            try {
                precioFinal = Float.parseFloat(precio);
                if (precioFinal < 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING,
                            "Por favor, introduce un precio valido (valor positivo)", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    equip.setPrice(precioFinal);
                    Alert alert = new Alert(Alert.AlertType.WARNING,
                            "¿Seguro que quieres guardar los datos?", ButtonType.OK, ButtonType.CANCEL);//alert to ask the user to confirm
                    alert.showAndWait();
                    if (alert.getResult().getButtonData().isCancelButton()) {
                        alert = new Alert(Alert.AlertType.WARNING,
                                "Se ha cancelado la accion", ButtonType.OK);//alert to advise that the action has being cancel
                        alert.showAndWait();

                    } else {
                        if (editar) {
                            EquipmentManagerFactory.getEquipmentManager().edit(equip);
                        } else {
                            EquipmentManagerFactory.getEquipmentManager().create(equip);
                        }

                        stage.close();
                    }
                }

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "El precio introducido no es valido, mete un numero por favor", ButtonType.OK);//alert to ask the user to confirm
                alert.showAndWait();
            }

        }
    }

}
