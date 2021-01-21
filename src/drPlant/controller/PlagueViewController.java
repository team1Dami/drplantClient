/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.classes.Plague;
import drPlant.classes.Plant;
import drPlant.classes.User;
import drPlant.factory.PlagueManagerFactory;
import drPlant.factory.PlantManagerFactory;
import drPlant.interfaces.PlagueManager;
import java.io.IOException;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author saray
 */
public class PlagueViewController {

    private static final Logger logger = Logger.getLogger("drPlant.controller.PlagueViewController");

    @FXML
    private Stage stage;
    @FXML
    private TextField tfSearch;
    @FXML
    private ChoiceBox chBox;
    @FXML
    private TableView tbPlague;
    @FXML
    private TableColumn colImage;
    @FXML
    private TableColumn colScientName;
    @FXML
    private TableColumn colCommonName;
    @FXML
    private TableColumn colType;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;

    private boolean isAdmin;

    private PlagueManager plagueManager;
    //   private ObservableList<Plague> plagues;
    private User user;

    private Plague plague;

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
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public Plague getPlague() {
        return plague;
    }

    /**
     *
     * @param user
     */
    public void setPlague(Plague plague) {
        this.plague = plague;
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
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     *
     * @param root
     * @param user
     */
    public void initStage(Parent root) {
        setIsAdmin(false);
        plagueManager = PlagueManagerFactory.getPlagueManager();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Lista de Plagas");

        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::setOncloseRequest);
        stage.show();
    }

    /**
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {

        // table
        //colImage.setCellValueFactory(new PropertyValueFactory<>("photo"));
        colScientName.setCellValueFactory(new PropertyValueFactory<>("scienceName"));
        colCommonName.setCellValueFactory(new PropertyValueFactory<>("commonName"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        ObservableList<Plague> plagues = FXCollections.observableArrayList(plagueManager.findAllPlagues(new GenericType<List<Plague>>() {
        }));
        tbPlague.setItems((ObservableList) plagues);
        tbPlague.getSelectionModel().selectedItemProperty().addListener(this::handlePlagueTableSelectionChanged);

        tfSearch.requestFocus();
        tfSearch.setPromptText("Introduce la plaga que buscas");
        //   tfSearch.focusedProperty().addListener(this::handleFocusChanged);

        chBox.setItems(FXCollections.observableArrayList(
                "Nombre científico", "Nombre común", "Selecciona un tipo de gravedad:",
                "Leve", "Medio", "Grave"));
        chBox.setValue("Nombre científico");

        /* chBox.getItems().add("Nombre científico");
        chBox.getItems().add("Nombre común");
        chBox.getItems().add("Tipo");*/
        // idea: en función de la selección del choice box se mostrará un promp text u otro
        if (isAdmin == true) {
            btnAdd.setVisible(true);
            btnEdit.setVisible(true);
            btnDelete.setVisible(true);
            btnEdit.setDisable(true);
            btnDelete.setDisable(true);
        } else {
            btnAdd.setVisible(false);
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);
            btnEdit.setDisable(false);
            btnDelete.setDisable(false);
        }
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
        Alert alert;
        
        if (tfSearch.getText().isEmpty()) {
            ObservableList<Plague> plagues = FXCollections.observableArrayList(plagueManager.findAllPlagues(new GenericType<List<Plague>>() {
            }));
            tbPlague.setItems((ObservableList) plagues);
        }
        
        if (chBox.getSelectionModel().getSelectedItem().equals("Nombre científico")) {

            Plague plague = plagueManager.find(Plague.class, tfSearch.getText().trim());

            if (plague != null) {
                Parent root;
                Stage stage2 = new Stage();
                try {
                    InfoPlagueController controller = new InfoPlagueController();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/infoPlague.fxml"));

                    try {
                        root = (Parent) loader.load();
                        controller = (loader.getController());
                        controller.setStage(stage2);
                        controller.initStage(root, isAdmin, plague);
                    } catch (IOException ex) {
                        ex.getMessage();//Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ClientErrorException e) {
                    alert = new Alert(Alert.AlertType.INFORMATION, "No se ha encontrado la plaga que buscas", ButtonType.OK);
                    alert.showAndWait();
                    // Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        if (chBox.getSelectionModel().getSelectedItem().equals("Nombre común")) {

            Plague plague = plagueManager.findPlagueByCommonName(Plague.class, tfSearch.getText().trim());
            
            if (plague != null) {
                Parent root;
                Stage stage2 = new Stage();
                try {
                    InfoPlagueController controller = new InfoPlagueController();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/infoPlague.fxml"));

                    try {
                        root = (Parent) loader.load();
                        controller = (loader.getController());
                        controller.setStage(stage2);
                        controller.initStage(root, isAdmin, plague);
                    } catch (IOException ex) {
                        ex.getMessage();//Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ClientErrorException e) {
                    alert = new Alert(Alert.AlertType.INFORMATION, "No se ha encontrado la plaga que buscas", ButtonType.OK);
                    alert.showAndWait();
                    // Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        
        if (chBox.getSelectionModel().getSelectedItem().equals("Leve")) {
            try {
                String searchedType = "light";
                ObservableList<Plague> plagues = FXCollections.observableArrayList(plagueManager.findPlaguesByType(new GenericType<List<Plague>>() {
                }, searchedType));
                tbPlague.setItems(plagues);
            } catch (ClientErrorException e) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Ops! se ha producido un error inesperado", ButtonType.OK);
                alert.showAndWait();
            }
        }
       
        if (chBox.getSelectionModel().getSelectedItem().equals("Medio")) {
            try {
                String searchedType = "middle";
                ObservableList<Plague> plagues = FXCollections.observableArrayList(plagueManager.findPlaguesByType(new GenericType<List<Plague>>() {
                }, searchedType));
                if (plagues.size() == 0) {
                    alert = new Alert(Alert.AlertType.INFORMATION, "No se han encontrado plagas de tipo medio", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    tbPlague.setItems(plagues);
                }
            } catch (ClientErrorException e) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Ops! se ha producido un error inesperado", ButtonType.OK);
                alert.showAndWait();
            }
        }
        
        if (chBox.getSelectionModel().getSelectedItem().equals("Grave")) {
            try {
                String searchedType = "severe";
                ObservableList<Plague> plagues = FXCollections.observableArrayList(plagueManager.findPlaguesByType(new GenericType<List<Plague>>() {
                }, searchedType));
                if (plagues.size() == 0) {
                    alert = new Alert(Alert.AlertType.INFORMATION, "No se han encontrado plagas de tipo medio", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    tbPlague.setItems(plagues);
                }
            } catch (ClientErrorException e) {
                alert = new Alert(Alert.AlertType.INFORMATION, "No se han encontrado plagas de tipo grave", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    /**
     * Se abrirá la ventana modal de infoPlague.fxml todos los campos (nombre
     * científico,nombre común, descripción, control, remedio y tipo) editables.
     *
     * @param event
     */
    @FXML
    private void handleAddAction(ActionEvent event
    ) {
        Parent root;
        Stage stage2 = new Stage();
        try {
            InfoPlagueController controller = new InfoPlagueController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/infoPlague.fxml"));

            try {
                root = (Parent) loader.load();
                controller = (loader.getController());
                controller.setStage(stage2);
                controller.initStage(root, isAdmin, plague);
            } catch (IOException ex) {
                ex.getMessage();//Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            e.getMessage();
            // Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    private void handlePlagueTableSelectionChanged(ObservableValue observable,
            Object oldValue,
            Object newValue
    ) {
        if (newValue != null) {
            plague = plagueManager.find(Plague.class, colScientName.getText());
            if (isAdmin) {
                btnEdit.setDisable(false);
                btnDelete.setDisable(false);
            }
        }
    }

    /**
     * Se abrirá la ventana modal de infoPlague.fxml campos (nombre común,
     * descripción, control, remedio y tipo) editables.
     *
     * @param event
     */
    @FXML
    private void handleEditAction(ActionEvent event
    ) {

        Parent root;
        Stage stage2 = new Stage();
        plague = plagueManager.find(Plague.class, colScientName.getText());

        try {
            InfoPlagueController controller = new InfoPlagueController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/infoPlague.fxml"));

            try {
                root = (Parent) loader.load();
                controller = (loader.getController());
                controller.setStage(stage2);
                controller.initStage(root, isAdmin, plague);
            } catch (IOException ex) {
                ex.getMessage();//Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            e.getMessage();
            // Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, e);
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
    private void handleDeleteAction(ActionEvent event
    ) {
        Plague plague = (Plague) tbPlague.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Borrar la fila seleccionada?\n"
                + "Esta operación no se puede deshacer.",
                ButtonType.OK, ButtonType.CANCEL);
        alert.getDialogPane();
        Optional<ButtonType> result = alert.showAndWait();
        //If OK to deletion
        if (result.isPresent() && result.get() == ButtonType.OK) {

            PlagueManagerFactory.getPlagueManager().remove(plague.getScienceName());
        }

    }
    //

    /**
     * Method that asks when you press the x if you want to exit of the
     * application if you press OK the application will be closed otherwhise you
     * stay in the PlagueView
     *
     * @param we
     */
    private void setOncloseRequest(WindowEvent we) {
        Alert alert;
        tfSearch.clear();
        try {
            alert = new Alert(Alert.AlertType.WARNING,
                    "¿Desea Salir de la aplicación?", ButtonType.OK, ButtonType.CANCEL);//alert to ask the user to confirm
            alert.showAndWait();
            if (alert.getResult().getButtonData().isCancelButton()) {
                alert = new Alert(Alert.AlertType.WARNING,
                        "Se ha cancelado la accion", ButtonType.OK);//alert to advise that the action has being cancel
                alert.showAndWait();
                we.consume();// do as nothing has happen

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

    /*  public void handleFocusChanged(ObservableValue observable, Boolean oldValue,
            Boolean newValue) {
        Alert alert;
        if (newValue) {
            if (!tfSearch.getText().trim().matches("[a-zA-Z]")) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Debes rellenar el campo correctamente!", ButtonType.OK);
                alert.showAndWait();
            }
        }
        else{
            
        }
    }*/
}
