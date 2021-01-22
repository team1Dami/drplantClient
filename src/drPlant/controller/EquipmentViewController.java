/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.classes.Equipment;
import drPlant.classes.User;
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
 *
 * @author Eneko
 */
public class EquipmentViewController {

    private static final Logger LOOGER = Logger.getLogger("drPlant.controller.EquipmentViewController");

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        EquipmentViewController.user = user;
    }

    //Declaration of attributes
    @FXML
    private Stage stage;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private ChoiceBox choiceBoxFiltros;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView equipmentTable;
    @FXML
    private TableColumn imageCol;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn descriptionCol;
    @FXML
    private TableColumn useCol;
    @FXML
    private TableColumn priceCol;

    private boolean isAdmin;
    private ObservableList<Equipment> equipments;

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
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
        setIsAdmin(true);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Equipamiento");
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::setOncloseRequest);
        stage.show();

    }

    private void handleWindowShowing(WindowEvent event) {

        setTableValues();

        txtSearch.setPromptText("Introduce el equipamiento que quieres buscar");
        txtSearch.focusedProperty().addListener(this::focusChanged);

        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("equipment_name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("equipment_description"));
        useCol.setCellValueFactory(new PropertyValueFactory<>("use"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        choiceBoxFiltros.setItems(FXCollections.observableArrayList("", "General", "Sustrato", "Riego"));
        choiceBoxFiltros.setValue("");

        // idea: en función de la selección del choice box se mostrará un promp text u otro
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

        equipmentTable.setOnMouseClicked((MouseEvent event2) -> {
            if (event2.getButton().equals(MouseButton.PRIMARY)) {
                btnDelete.setDisable(false);
                btnEdit.setDisable(false);
            }
        });

        btnSearch.setOnAction(this::handleSearchAction);
        btnAdd.setOnAction(this::handleAddAction);
        btnEdit.setOnAction(this::handleEditAction);
        btnDelete.setOnAction(this::handleDeleteAction);
    }

    /**
     * Recoge el texto que se ha introducido en el textFieldBuscador y la opción
     * seleccionada en el choice box en caso de encontrar la plaga la mostrará
     * en la lista.
     *
     * @param event event - onClick event
     */
    @FXML
    private void handleSearchAction(ActionEvent event) {
        String filtroUso = (String) choiceBoxFiltros.getValue();
        String buscador = txtSearch.getText();
        if (!filtroUso.isEmpty() && buscador.isEmpty()) {
            setTableValuesWithFilters(filtroUso);
        } else if (!filtroUso.isEmpty() && !buscador.isEmpty()) {
            setTableValuesWithFilters(filtroUso, buscador);
        } else if (filtroUso.isEmpty() && !buscador.isEmpty()) {
            setTableValuesWithText(buscador);
        } else {
            setTableValues();
        }
    }

    /**
     * Se abrirá la ventana modal de infoPlague.fxml todos los campos (nombre
     * científico,nombre común, descripción, control, remedio y tipo) editables.
     *
     * @param event
     */
    @FXML
    private void handleAddAction(ActionEvent event) {
        Parent root;
        try {
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
            Logger.getLogger(EquipmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(EquipmentViewController.class.getName()).log(Level.SEVERE, null, e);
        }
        setTableValues();
    }

    /**
     * Se abrirá la ventana modal de infoPlague.fxml campos (nombre común,
     * descripción, control, remedio y tipo) editables.
     *
     * @param event
     */
    @FXML
    private void handleEditAction(ActionEvent event) {
        try {
            Equipment equip = (Equipment) equipmentTable.getSelectionModel().getSelectedItem();
            if (equip.getEquipment_name().isEmpty() && equip.getEquipment_description().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Primero seleccione una fila de la tabla", ButtonType.OK); //alert to ask the user to confirm
                alert.showAndWait();
                btnDelete.setDisable(true);
                btnEdit.setDisable(true);
            }
            Parent root;
            try {
                Stage newstage = new Stage();
                EquipmentDetailsController controller = new EquipmentDetailsController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/EquipmentDetails.fxml"));

                try {
                    controller.setEquip(equip);
                    root = (Parent) loader.load();
                    controller = (loader.getController());
                    controller.setStage(newstage);
                    controller.initStage(root);

                } catch (IOException ex) {
                    //Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                //Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, e);
            }
            setTableValues();
        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Primero seleccione una fila de la tabla", ButtonType.OK); //alert to ask the user to confirm
            alert.showAndWait();
            btnDelete.setDisable(true);
            btnEdit.setDisable(true);
        }

    }

    /**
     * Se mostrará un alert indicando la acción con las opciones SÍ / NO. En
     * caso de pulsar SÍ se eliminará la plaga seleccionada. Método de la capa
     * de lógica al que se llama: remove(); En caso de pulsar NO se cerrará el
     * alert.
     *
     * @param event
     */
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        try {
            Equipment equip = (Equipment) equipmentTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "¿Desea borrar el equipamiento " + equip.getEquipment_name() + "?", ButtonType.OK, ButtonType.CANCEL); //alert to ask the user to confirm
            alert.showAndWait();
            if (alert.getResult().getButtonData().isCancelButton()) {
                alert = new Alert(Alert.AlertType.WARNING,
                        "Se ha cancelado el borrado", ButtonType.OK);//alert to advise that the action has being cancel
                alert.showAndWait();
            } else {
                EquipmentManagerFactory.getEquipmentManager().remove(equip.getId_equipment().toString());
                alert = new Alert(Alert.AlertType.WARNING,
                        "Se ha borrado correctamente", ButtonType.OK);
                alert.showAndWait();
                setTableValues();
            }
        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Primero seleccione una fila de la tabla", ButtonType.OK); //alert to ask the user to confirm
            alert.showAndWait();
            btnDelete.setDisable(true);
            btnEdit.setDisable(true);
        }
    }

    /**
     * Method that asks when you press the x if you want to exit of the
     * application if you press OK the application will be closed otherwhise you
     * stay in the PlagueView
     *
     * @param we
     */
    private void setOncloseRequest(WindowEvent we) {
        Alert alert;
        try {
            alert = new Alert(Alert.AlertType.WARNING,
                    "¿Desea salir de la aplicacion?", ButtonType.OK, ButtonType.CANCEL);//alert to ask the user to confirm
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

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void focusChanged(ObservableValue observable, Boolean oldValue,
            Boolean newValue) {

    }

    private void setTableValues() {
        ObservableList<Equipment> equipmentss = FXCollections.observableArrayList(EquipmentManagerFactory.getEquipmentManager()
                .findAllEquipment(new GenericType<Set<Equipment>>() {
                }));
        equipmentTable.setItems(equipmentss);
    }

    private void setTableValuesWithFilters(String filtroUso) {
        filtroUso = filtroUso.toLowerCase();
        ObservableList<Equipment> equipmentss = FXCollections.observableArrayList(EquipmentManagerFactory.getEquipmentManager()
                .findEquipmentByUse(new GenericType<Set<Equipment>>() {
                }, filtroUso));

        equipmentTable.setItems(equipmentss);
    }

    private void setTableValuesWithFilters(String filtroUso, String buscador) {
        filtroUso = filtroUso.toLowerCase();
        ObservableList<Equipment> equipmentsWithFilter = FXCollections.observableArrayList(EquipmentManagerFactory.getEquipmentManager()
                .findEquipmentByNameAndUse(new GenericType<Set<Equipment>>() {
                }, filtroUso, buscador));
        equipmentTable.setItems(equipmentsWithFilter);
    }

    private void setTableValuesWithText(String buscador) {
        ObservableList<Equipment> equipmentsWithFilter = FXCollections.observableArrayList(EquipmentManagerFactory.getEquipmentManager()
                .findEquipmentByName(new GenericType<Set<Equipment>>() {
                }, buscador));
        equipmentTable.setItems(equipmentsWithFilter);
    }

}
