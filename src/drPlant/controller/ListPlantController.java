
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.enumerations.Climate;
import drPlant.enumerations.UserPrivilege;
import drPlant.classes.Plant;
import drPlant.classes.User;
import drPlant.factory.PlantManagerFactory;
import java.io.IOException;
import java.util.Optional;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.DefaultStringConverter;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Ruben
 */
public class ListPlantController {

    private static final Logger LOGGER = Logger.getLogger("drPlant.controller.ListPlantController");

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

    @FXML
    private Button btnRefresh;
    
    @FXML 
    private MenuController menuControllerController;

    private User user;
    
    

    //private Plant plantEdit;
    public void setUser(User user) {
        if (user.getPrivilege() == UserPrivilege.ADMIN) {
            isAdmin = true;
        } else {
            isAdmin = false;
        }

        this.user = user;
    }

    public boolean isAdmin = true;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    /**
     * Method that initialize the window
     *
     * @param root
     * @param user
     */
    public void initStage(Parent root, User user) {
        try {
            setUser(user);
            menuControllerController.setUser(user);
            Scene scene = new Scene(root);            
            //stage = new Stage();
            menuControllerController.setStageOld(stage);
            //Set stage properties
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Lista plantas");

            stage.setOnShowing(this::handleShowWindow);

            stage.setOnCloseRequest(this::setOncloseRequest);
            
            stage.show();
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not initialize the window");
        }
    }

    /**
     * Method to handle the first view the windows element
     *
     * @param e
     */
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

        try {
            ObservableList<Plant> plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getAllPlants(new GenericType<Set<Plant>>() {
            }));
            tblPlants.setItems(plants);
        } catch (ClientErrorException e2) {
            LOGGER.log(Level.SEVERE, "Error adding items in the table");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        }

        cbType.setItems(FXCollections.observableArrayList(
                " ", "Interior", "Exterior", "Suculenta")
        );
        cbType.setValue(" ");
        cbClimate.setItems(FXCollections.observableArrayList(
                " ", "Calido", "Frio", "Humedo", "Seco")
        );
        cbClimate.setValue(" ");
        cbPet.setItems(FXCollections.observableArrayList(
                " ", "Perro", "Gato", "Ambos", "No")
        );
        cbPet.setValue(" ");
        if (isAdmin) {
            btnAdd.setVisible(true);
            btnAdd.setDisable(false);
            btnEdit.setVisible(true);
            //btnEdit.setDisable(false);
            btnRemove.setVisible(true);
            tblPlants.getSelectionModel().selectedItemProperty()
                    .addListener(this::handleUsersTableSelection);

        } else {
            btnAdd.setVisible(false);
            btnEdit.setVisible(true);
            btnRemove.setVisible(false);
        }
        btnSearch.setOnAction(this::handleSearchButton);
        btnAdd.setOnAction(this::handleAddButton);
        btnEdit.setOnAction(this::handleEditButton);
        btnRemove.setOnAction(this::handleRemoveButton);

        btnRefresh.setOnAction(this::refreshTable);

        tblPlants.setOnMouseClicked(this::handleClickTable);

    }

    /**
     * Method that handle the clicks on the tableview
     * If is admin and make 2 clicks can edit some columns
     * If is user and maka 2 clicks enter in infoplant view
     * Both can select and activate the bottons above
     * @param ev
     */
    private void handleClickTable(MouseEvent ev) {
        if (ev.getClickCount() == 2 & isAdmin) {

            tblPlants.setEditable(true);

            clCommonName.setCellFactory(TextFieldTableCell.forTableColumn());
            clCommonName.setOnEditCommit(t -> {
                updateCommonName((CellEditEvent<Plant, String>) t);
            });

            ObservableList<String> cbClimate = FXCollections.observableArrayList("hot", "cold", "wet", "dry");
            clClimate.setCellFactory(ChoiceBoxTableCell.forTableColumn(cbClimate));
            clClimate.getCellData(cbClimate);
            clClimate.setOnEditCommit(t -> {
                updateClimate((CellEditEvent<Plant, String>) t);
            });

            ObservableList<String> cbType = FXCollections.observableArrayList("indoor", "outdoor", "succulent");
            clType.setCellFactory(ChoiceBoxTableCell.forTableColumn(cbType));
            clType.getCellData(cbClimate);
            clType.setOnEditCommit(t -> {
                updateType((CellEditEvent<Plant, String>) t);
            });

            ObservableList<String> cbPet = FXCollections.observableArrayList("no", "cat", "dog", "both");
            clAnimal.setCellFactory(ChoiceBoxTableCell.forTableColumn(cbPet));
            clAnimal.getCellData(cbClimate);
            clAnimal.setOnEditCommit(t -> {
                updatePet((CellEditEvent<Plant, String>) t);
            });
        }
        if (ev.getClickCount() == 2 & !isAdmin) {
            //user.setPrivilege(UserPrivilege.ADMIN);
            Plant plant = (Plant) tblPlants.getSelectionModel().getSelectedItem();
            InfoPlantController controller = new InfoPlantController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/InfoPlanta.fxml"));
            Parent root;
            try {
                root = (Parent) loader.load();
                controller = (loader.getController());
                controller.setStage(stage);
                controller.initStage(root, plant, user);
            } catch (IOException ex) {
                System.out.println("Error");
            }
        }
    }

    /**
     * Method that handle the selection on the table Make button edit and remove
     * enableif there is a row selected
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void handleUsersTableSelection(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        //If there is a row selected, move row data to corresponding fields in the
        //window and enable create, modify and delete buttons
        if (newValue != null) {
            btnEdit.setDisable(false);
            btnRemove.setDisable(false);

        } else {
            //If there is not a row selected, clean window fields 
            //and disable create, modify and delete buttons
            btnEdit.setDisable(true);
            btnRemove.setDisable(true);
        }

        //Focus Search field
        txtSearch.requestFocus();
    }
/**
 * Handle add button
 * open the info plant view without sending any plant
 * @param e 
 */
    private void handleAddButton(ActionEvent e) {
        InfoPlantController controller = new InfoPlantController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/InfoPlanta.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root, isAdmin);
        } catch (IOException ex) {
            System.out.println("Casca");
        }
    }
/**
 * Open info plant window with a plant information and editable
 * @param e 
 */
    private void handleEditButton(ActionEvent e) {

        Plant plant = (Plant) tblPlants.getSelectionModel().getSelectedItem();

        InfoPlantController controller = new InfoPlantController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/InfoPlanta.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root, plant, user);
        } catch (IOException ex) {
            System.out.println("Error");
        }

    }

    /**
     * Method that handle button remove
     * Remove a plant from database 
     * @param e 
     */
    private void handleRemoveButton(ActionEvent e) {
        Plant plant = (Plant) tblPlants.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Borrar la fila seleccionada?\n"
                + "Esta operación no se puede deshacer.",
                ButtonType.OK, ButtonType.CANCEL);
        alert.getDialogPane();
        Optional<ButtonType> result = alert.showAndWait();
        //If OK to deletion
        if (result.isPresent() && result.get() == ButtonType.OK) {
            PlantManagerFactory.getPlantManager().remove(plant.getScienceName());
            refreshTable();
        }
    }

    /**
     * Get the info that user introduce:
     * Name, choicebox of Climate, pet and type
     * @param e
     */
    private void handleSearchButton(ActionEvent e) {
        ObservableList<Plant> plants = null;
        String clima = null, tipo = null, pet = null;
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
            if (cbPet.getValue() == "Perro") {
                pet = "dog";
            }
            if (cbPet.getValue() == "Gato") {
                pet = "cat";
            }
            if (cbPet.getValue() == "Ambos") {
                pet = "both";
            } else if (cbPet.getValue() == "No") {
                pet = "no";
            }
        }
        if (cbType.getValue() != " ") {
            if (cbType.getValue() == "Interior") {
                tipo = "indoor";
            }
            if (cbType.getValue() == "Suculenta") {
                tipo = "succulent";
            }
            if (cbType.getValue() == "Exterior") {
                tipo = "outdoor";
            }
        }
        if (txtSearch.getText().isEmpty()) {
            if (pet != null & clima != null & tipo != null) {
                LOGGER.log(Level.INFO, "pet, clima y tipo distintos de null");
                plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getPlantWithAll(new GenericType<Set<Plant>>() {
                }, tipo, pet, clima));
            } else if (pet == null & clima != null & tipo != null) {
                LOGGER.log(Level.INFO, "pet nulo, clima y tipo distintos de null");
                plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getPlantByTypeAndClimate(new GenericType<Set<Plant>>() {
                }, tipo, clima));

            } else if (pet == null & clima == null & tipo != null) {
                LOGGER.log(Level.INFO, "pet y clima nulo, tipo no null");
                plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getPlantByType(new GenericType<Set<Plant>>() {
                }, tipo));
            } else if (pet != null & clima == null & tipo != null) {
                LOGGER.log(Level.INFO, "pet, clima y tipo distintos de null");
                plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getPlantByTypeAndPetFriendly(new GenericType<Set<Plant>>() {
                }, tipo, pet));
            } else if (pet != null & clima != null & tipo == null) {
                LOGGER.log(Level.INFO, "pet, clima y tipo distintos de null");
                plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getPlantByPetFriendlyAndClimate(new GenericType<Set<Plant>>() {
                }, pet, tipo));

            } else if (pet != null & clima == null & tipo == null) {
                LOGGER.log(Level.INFO, "pet, clima y tipo distintos de null");
                plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getPlantByPetFriendly(new GenericType<Set<Plant>>() {
                }, pet));

            } else if (pet == null & clima == null & tipo == null) {
                LOGGER.log(Level.INFO, "pet, clima y tipo distintos de null");
                plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getAllPlants(new GenericType<Set<Plant>>() {
                }));
            } else if (pet == null & clima != null & tipo == null) {
                LOGGER.log(Level.INFO, "pet, clima y tipo distintos de null");
                plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getPlantByClimate(new GenericType<Set<Plant>>() {
                }, clima));
            }

        } else {

            LOGGER.log(Level.INFO, "Hay texto. nombre, clima y tipo distintos de null");
            try {
                plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().find(Plant.class, txtSearch.getText()));
            } catch (ClientErrorException ex) {
                System.out.println("No existe");
                plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getPlantByCommonName(new GenericType<Set<Plant>>() {
                }, txtSearch.getText()));
            }

        }
        tblPlants.setItems(plants);
    }
/**
 * If user try to close the window show an alert for confirm the action
 * @param we 
 */
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
            LOGGER.log(Level.SEVERE,
                    "UI LoginController: Error opening users managing window: {0}",
                    ex.getMessage());
            alert = new Alert(Alert.AlertType.WARNING,
                    "No se ha podido cargar la ventana", ButtonType.OK);
            alert.showAndWait();
        }
    }
/**
 * Update the cell of the common Name
 * @param t 
 */
    private void updateCommonName(CellEditEvent<Plant, String> t) {
        Plant p = t.getRowValue();
        p.setCommonName(t.getNewValue());
        PlantManagerFactory.getPlantManager().edit(p);
        tblPlants.refresh();
    }
/**
 * Update Climate after change in the table
 * @param t 
 */
    private void updateClimate(CellEditEvent<Plant, String> t) {
        Plant p = t.getRowValue();
        if (p.getClimate().equals(Climate.hot)) {
            p.setClimate(Climate.hot);
        } else if (p.getClimate().equals(Climate.cold)) {
            p.setClimate(Climate.cold);
        } else if (p.getClimate().equals(Climate.dry)) {
            p.setClimate(Climate.dry);
        } else if (p.getClimate().equals(Climate.wet)) {
            p.setClimate(Climate.wet);
        }
        PlantManagerFactory.getPlantManager().edit(p);
        //tblPlants.refresh();
    }
/**
 * search all the plant after click a botton
 * @param e 
 */
    private void refreshTable(ActionEvent e) {
        try {
            ObservableList<Plant> plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getAllPlants(new GenericType<Set<Plant>>() {
            }));
            tblPlants.setItems(plants);
        } catch (ClientErrorException e2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        }
    }
/**
 * search all the plant after a call
 */
    private void refreshTable() {
        try {
            ObservableList<Plant> plants = FXCollections.observableArrayList(PlantManagerFactory.getPlantManager().getAllPlants(new GenericType<Set<Plant>>() {
            }));
            tblPlants.setItems(plants);
        } catch (ClientErrorException e2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        }
    }
/**
 * 
 * @param cellEditEvent 
 */
    private void updateType(CellEditEvent<Plant, String> cellEditEvent) {

    }
/**
 * 
 * @param cellEditEvent 
 */
    private void updatePet(CellEditEvent<Plant, String> cellEditEvent) {

    }
}
