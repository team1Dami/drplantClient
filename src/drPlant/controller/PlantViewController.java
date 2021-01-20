
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.classes.Plant;
import drPlant.controller.*;
import drPlant.factory.PlantManagerFactory;
import drPlant.interfaces.PlantManager;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Ruben
 */
public class PlantViewController {

    private static final Logger logger = Logger.getLogger("drPlant.controller.PlantFilterController");

    @FXML
    private Stage stage;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView tblPlants;

    @FXML
    private TableColumn clImage;

    @FXML
    private TableColumn clScienceName;

    @FXML
    private TableColumn clCommonName;

    @FXML
    private TableColumn clType;

    @FXML
    private TableColumn clAnimal;

    @FXML
    private TableColumn clClimate;

    @FXML
    private ChoiceBox cbClimate;

    @FXML
    private ChoiceBox cbType;

    @FXML
    private ChoiceBox cbPet;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void initStage(Parent root) {
        try {
            Scene scene = new Scene(root);
            stage = new Stage();
            //Set stage properties
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Lista plantas");
            stage.setResizable(false);

            stage.setOnShowing(this::handleShowWindow);

            stage.show();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    private void handleShowWindow(WindowEvent e) {
        clImage.setCellValueFactory(
                new PropertyValueFactory<>("image"));
        clScienceName.setCellValueFactory(
                new PropertyValueFactory<>("scienceName"));
        clCommonName.setCellValueFactory(
                new PropertyValueFactory<>("commonName"));
        clType.setCellValueFactory(
                new PropertyValueFactory<>("plantType"));
        clAnimal.setCellValueFactory(
                new PropertyValueFactory<>("petfriendly"));
        clClimate.setCellValueFactory(
                new PropertyValueFactory<>("climate"));

        ObservableList<Plant> plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getAllPlants(new GenericType<Set<Plant>>() {
        }));

        tblPlants.setItems((ObservableList) plants);

        // comboBox
        cbType.setItems(FXCollections.observableArrayList(
                " ", "Interior", "Exterior", "Suculenta")
        );
        cbType.setValue(" ");
        cbClimate.setItems(FXCollections.observableArrayList(
                " ", "Calido", "Frio", "Humedo", "Seco")
        );
        cbClimate.setValue(" ");
        cbPet.setItems(FXCollections.observableArrayList(
                " ", "Perro", "Gato", "Ambos")
        );
        cbPet.setValue(" ");
        
        btnSearch.setOnAction(this::handleSearchButton);
        btnAdd.setOnAction(this::handleAddButton);
        btnEdit.setOnAction(this::handleEditButton);
        btnRemove.setOnAction(this::handleRemoveButton);
    }

    private void handleAddButton(ActionEvent e) {

    }

    private void handleEditButton(ActionEvent e) {

    }

    private void handleRemoveButton(ActionEvent e) {

    }

    private void handleSearchButton(ActionEvent e) {
        /* Set<Plant> plants;
        String nombre = null, clima = null, tipo = null, pet = null;
        if (txtSearch.getText() != null) {
            nombre = txtSearch.getText();
        }
        if (cbClimate.getValue() != " ") {
            if (cbClimate.getValue() == "Calido") {
                clima = "hot";
            }
            if (cbClimate.getValue() == "Humedo") {
                clima = "wet";
            }
            if (cbClimate.getValue() == "Frio") {
                clima = "cold";
            }
            if (cbClimate.getValue() == "Seco") {
                clima = "dry";
            }
        }
        if (cbPet.getValue() != " ") {
            pet = cbPet.getValue().toString();
        }
        if (cbType.getValue() != " ") {
            tipo = cbType.getValue().toString();
        }
        if (nombre != null & clima != null & tipo != null) {

        }*/
    }

    private void setOncloseRequest(WindowEvent we) {
        Alert alert;
        try {
            alert = new Alert(Alert.AlertType.WARNING,
                    "Desea Salir de esta ventana", ButtonType.OK, ButtonType.CANCEL);//alert to ask the user to confirm
            alert.showAndWait();
            if (alert.getResult().getButtonData().isCancelButton()) {
                alert = new Alert(Alert.AlertType.WARNING,
                        "Se ha cancelado la accion", ButtonType.OK);//alert to advise that the action has being cancel
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
