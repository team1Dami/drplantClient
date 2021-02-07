/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.classes.Equipment;
import drPlant.classes.User;
import drPlant.enumerations.UserPrivilege;
import drPlant.factory.EquipmentManagerFactory;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;

/**
 * Controller class for equipment' management view . It contains event handlers
 * and initialization code for the view defined in EquipmentView.fmxl file.
 *
 * @author Eneko
 */
public class EquipmentViewController {

    /**
     * The logger to catch the info of the view and it's events
     */
    private static final Logger LOOGER = Logger.getLogger("drPlant.controller.EquipmentViewController");

    /**
     * The user who has logged in in the aplication
     */
    private static User user;

    /**
     * A method to get the user of this stage
     *
     * @return The User of this stage
     */
    public static User getUser() {
        return user;
    }

    /**
     * A method to set the user of this stage
     *
     * @param user The user information that you want to set in
     */
    public static void setUser(User user) {
        EquipmentViewController.user = user;
    }

    //Declaration of attributes
    /**
     * The stage of this window
     */
    @FXML
    private Stage stage;
    /**
     * The button to search equipments by their use, name or both
     */
    @FXML
    private Button btnSearch;
    /**
     * The button to add a new equipment, it will rise a new window to introduce
     * the information
     */
    @FXML
    private Button btnAdd;
    /**
     * The button to edit the selected equipment in the table that will rise a
     * new window with the actual information
     */
    @FXML
    private Button btnEdit;
    /**
     * The button to delete the selected equipment on the table
     */
    @FXML
    private Button btnDelete;
    /**
     * A choice box with the possible uses in orther to search the equipments
     * with that use
     */
    @FXML
    private ChoiceBox choiceBoxFiltros;
    /**
     * A text field to introduce the equipment name you want to search for
     */
    @FXML
    private TextField txtSearch;
    /**
     * The table with the equipents where you can see them
     */
    @FXML
    private TableView equipmentTable;
    /**
     * The table colum wich contains the name of the equipment
     */
    @FXML
    private TableColumn nameCol;
    /**
     * The table colum wich contains the description of the equipment
     */
    @FXML
    private TableColumn descriptionCol;
    /**
     * The table colum wich contains the use of the equipment
     */
    @FXML
    private TableColumn useCol;
    /**
     * The table colum wich contains the price of the equipment
     */
    @FXML
    private TableColumn priceCol;

    /**
     * The menu controller to load the menu
     */
    @FXML
    private MenuController menuControllerController;

    /**
     * A boolean to know if the user that is logged is an admin or not
     */
    private boolean isAdmin;

    /**
     * A observable list where the equipments that returns the database are load
     */
    private ObservableList<Equipment> equipments;

    /**
     * Returns the boolean isAdmin is true or false
     *
     * @return isAdmin boolean value
     */
    public boolean isIsAdmin() {
        return isAdmin;
    }

    /**
     * Set the isAdmin boolean to true or false
     *
     * @param isAdmin A boolean
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Set the stage of the view
     *
     * @param stage Stage 
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initialize the view and set actions on different widgets
     *
     * @param root The parent value
     * @param u The user of the previus stage
     */
    public void initStage(Parent root, User u) {
        //Get the User witch has logged in
        menuControllerController.setUser(u);
        menuControllerController.setStageOld(stage);
        //Gets the privilege of the user to set the isAdmin value if the privilege is ADMIN
        //the isAdmin will be set to true, if is USER the isAdmin will be false
        if (u.getPrivilege().equals(UserPrivilege.ADMIN)) {
            setIsAdmin(true);
        } else {
            setIsAdmin(false);
        }
        //Set stage properties
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Equipamiento");
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::setOncloseRequest);
        stage.show();
        //Add all the equipments of the database to the table
        setTableValues();

    }

    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type
     * event.
     *
     * @param event The window event
     */
    private void handleWindowShowing(WindowEvent event) {

        //Set the promp text to the search field
        txtSearch.setPromptText("Introduce el equipamiento que quieres buscar");
        txtSearch.focusedProperty().addListener(this::focusChanged);

        //Set the columns of the tables
        nameCol.setCellValueFactory(new PropertyValueFactory<>("equipment_name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("equipment_description"));
        useCol.setCellValueFactory(new PropertyValueFactory<>("use"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Set the choicebox items to the posible uses you can search for
        choiceBoxFiltros.setItems(FXCollections.observableArrayList("", "General", "Sustrato", "Riego"));
        choiceBoxFiltros.setValue("");

        //Set the price text allignment to the right in order to compare better the price
        priceCol.setStyle("-fx-alignment: CENTER-RIGHT;");

        //Looks if isAdmin is true or false in order to show the buttons of editing, adding and deleting 
        if (!isAdmin) {
            btnAdd.setVisible(isAdmin);
            btnEdit.setVisible(isAdmin);
            btnDelete.setVisible(isAdmin);
        } else {
            btnAdd.setDisable(false);
            btnAdd.setVisible(isAdmin);
            btnEdit.setVisible(isAdmin);
            btnDelete.setVisible(isAdmin);
        }

        //A listener to the table to know if it's clicked
        equipmentTable.setOnMouseClicked((MouseEvent event2) -> {
            if (event2.getButton().equals(MouseButton.PRIMARY)) {
                btnDelete.setDisable(false);
                btnEdit.setDisable(false);
            }
        });

        //Set the event handlers to the respective buttons
        btnSearch.setOnAction(this::handleSearchAction);
        btnAdd.setOnAction(this::handleAddAction);
        btnEdit.setOnAction(this::handleEditAction);
        btnDelete.setOnAction(this::handleDeleteAction);
    }

    /**
     * Gets the text that is in the textfield of search and the selected option
     * in the choice box of uses, in case that the searched quipment exists it
     * will show all the equipments found with that parameters
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleSearchAction(ActionEvent event) {
        String filtroUso = (String) choiceBoxFiltros.getValue();
        String buscador = txtSearch.getText();
        //It erase the white spaces before and after the text of the sear field
        buscador = buscador.replaceAll("^\\s*", "");
        buscador = buscador.replaceAll("\\s*$", "");
        //It checks what filters are activated 
        if (!filtroUso.isEmpty() && buscador.isEmpty()) {
            //Search by use
            setTableValuesWithFilters(filtroUso);
        } else if (!filtroUso.isEmpty() && !buscador.isEmpty()) {
            //Search by name and use
            setTableValuesWithFilters(filtroUso, buscador);
        } else if (filtroUso.isEmpty() && !buscador.isEmpty()) {
            //Search by name
            setTableValuesWithText(buscador);
        } else {
            //Search without filters
            setTableValues();
        }
    }

    /**
     * It will show the EquipmentDetailsView.fxml with all the fields empty in
     * order to introduce the new equipment information, after you save the
     * information it will add the new equipment to the database and refresh the
     * table
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleAddAction(ActionEvent event) {
        Parent root;
        try {
            //Load the modal window to add a new equipment
            Stage newstage = new Stage();
            EquipmentDetailsController controller = new EquipmentDetailsController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/EquipmentDetails.fxml"));

            Equipment equip = new Equipment();
            controller.setEquip(equip);
            root = (Parent) loader.load();
            controller = (loader.getController());
            controller.setStage(newstage);
            controller.initStage(root);

        } catch (IOException ex) {
            LOOGER.log(Level.SEVERE, "IO exception", ex.getMessage());
        } catch (Exception e) {
            LOOGER.log(Level.SEVERE, "An exception ocurred", e.getMessage());
        }
        //Refresh the table with the new equipment
        setTableValues();
    }

    /**
     * It will show the EquipmentDetailsView.fxml with all the fields set with
     * the selected equipment information, in case there isn't a selected row it
     * will show an alert to notify the user that he must select a row, to
     * introduce the new equipment information, after you save the information
     * it will update the equipment to the database and refresh the table
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleEditAction(ActionEvent event) {
        try {
            Equipment equip = (Equipment) equipmentTable.getSelectionModel().getSelectedItem();
            //Check if a row is selected
            if (equip.getEquipment_name().isEmpty() && equip.getEquipment_description().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Primero seleccione una fila de la tabla", ButtonType.OK); //Alert to ask the user to confirm
                alert.showAndWait();
                btnDelete.setDisable(true);
                btnEdit.setDisable(true);
            }
            Parent root;
            try {
                //Load the modal window to edit the selected equipment
                Stage newstage = new Stage();
                EquipmentDetailsController controller = new EquipmentDetailsController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/EquipmentDetails.fxml"));

                controller.setEquip(equip);
                root = (Parent) loader.load();
                controller = (loader.getController());
                controller.setStage(newstage);
                controller.initStage(root);

            } catch (IOException ex) {
                LOOGER.log(Level.SEVERE, "Exception while loading the new stage", ex.getMessage());
            } catch (Exception e) {
                LOOGER.log(Level.SEVERE, "Exception while loading the controller", e.getMessage());
            }
            //Refresh the table with the equipment edited updated
            setTableValues();
        } catch (NullPointerException ex) {
            LOOGER.log(Level.SEVERE, "No row selected", ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Primero seleccione una fila de la tabla", ButtonType.OK); //alert to ask the user to confirm
            alert.showAndWait();
            btnDelete.setDisable(true);
            btnEdit.setDisable(true);
        }

    }

    /**
     * It will show an alert to warn the user about the delete and the name of
     * the equipment is going to delete if the user want to delete it, he will
     * click on the OK button and the equipment will be removed from the
     * database, then the table will refresh with the new list of equipments in
     * case there are in the database. If the user clicks on the CANCEL button,
     * the warning will close as nothing happend
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        try {
            //Gets the selected item
            Equipment equip = (Equipment) equipmentTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "¿Desea borrar el equipamiento " + equip.getEquipment_name() + "?", ButtonType.OK, ButtonType.CANCEL); //alert to ask the user to confirm
            alert.showAndWait();
            //If the button clicked is OK it will delete the selected item
            if (!alert.getResult().getButtonData().isCancelButton()) {
                EquipmentManagerFactory.getEquipmentManager().remove(equip.getId_equipment().toString());
                setTableValues();
            }
        } catch (NullPointerException ex) {
            LOOGER.log(Level.SEVERE, "No row selected", ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Primero seleccione una fila de la tabla", ButtonType.OK); //alert to ask the user to confirm
            alert.showAndWait();
            btnDelete.setDisable(true);
            btnEdit.setDisable(true);
        }
    }

    /**
     * Method that asks when you want to close the window if you want to exit of
     * the application if you press OK the application will be closed otherwhise
     * you stay in the EquipmentView
     *
     * @param we The ActionEvent object for the event.
     */
    private void setOncloseRequest(WindowEvent we) {
        Alert alert;
        try {
            //Alert to ask the user to confirm
            alert = new Alert(Alert.AlertType.WARNING,
                    "¿Desea salir de la aplicacion?", ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult().getButtonData().isCancelButton()) {
                //Do as nothing has happen
                we.consume();
            }
        } catch (Exception ex) {
            LOOGER.log(Level.SEVERE,
                    "Error while closing",
                    ex.getMessage());
            alert = new Alert(Alert.AlertType.WARNING,
                    "No se ha podido cerrar la ventana", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * NOT WORKING (EMPTY) Is not used and it's empty
     *
     * @param observable NOT WORKING
     * @param oldValue NOT WORKING
     * @param newValue NOT WORKING
     */
    public void focusChanged(ObservableValue observable, Boolean oldValue,
            Boolean newValue) {

    }

    /**
     * This method gets all the equipments stored in the database and load them
     * to the equipment table, it is used to load the equipment for the first
     * time when the window is showed or to load the changes after a
     * modification on the database
     */
    private void setTableValues() {
        ObservableList<Equipment> equipmentss = FXCollections.observableArrayList(EquipmentManagerFactory.getEquipmentManager()
                .findAllEquipment(new GenericType<Set<Equipment>>() {
                }));
        equipmentTable.setItems(equipmentss);
    }

    /**
     * This method gets all the equipments with the selected filter stored in
     * the database and load them to the equipment table, it is used to load the
     * equipment when you only want to search them by use
     *
     * @param filtroUso The use you want to search for
     */
    private void setTableValuesWithFilters(String filtroUso) {
        filtroUso = filtroUso.toLowerCase();
        ObservableList<Equipment> equipmentss = FXCollections.observableArrayList(EquipmentManagerFactory.getEquipmentManager()
                .findEquipmentByUse(new GenericType<Set<Equipment>>() {
                }, filtroUso));

        equipmentTable.setItems(equipmentss);
    }

    /**
     * This method gets all the equipments with the selected filter and the name
     * stored in the database and load them to the equipment table, it is used
     * to load the equipment when you want to search them by use and name
     * simultaneously
     *
     * @param filtroUso The use you want to search for
     * @param buscador The name you want to look for
     */
    private void setTableValuesWithFilters(String filtroUso, String buscador) {
        filtroUso = filtroUso.toLowerCase();
        ObservableList<Equipment> equipmentsWithFilter = FXCollections.observableArrayList(EquipmentManagerFactory.getEquipmentManager()
                .findEquipmentByNameAndUse(new GenericType<Set<Equipment>>() {
                }, filtroUso, buscador));
        equipmentTable.setItems(equipmentsWithFilter);
    }

    /**
     * This method gets all the equipments with the introduced name stored in
     * the database and load them to the equipment table, it is used to load the
     * equipment when you only want to search them by name
     *
     * @param buscador The text introduced on the Search textfield
     */
    private void setTableValuesWithText(String buscador) {
        ObservableList<Equipment> equipmentsWithFilter = FXCollections.observableArrayList(EquipmentManagerFactory.getEquipmentManager()
                .findEquipmentByName(new GenericType<Set<Equipment>>() {
                }, buscador));
        equipmentTable.setItems(equipmentsWithFilter);
    }

}
