/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.classes.Plague;
import drPlant.classes.User;
import drPlant.factory.PlagueManagerFactory;
import drPlant.interfaces.PlagueManager;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 * Controller class for plague' management view . It contains event handlers and
 * initialization code for the view defined in PlagueView.fmxl file.
 *
 * @author saray
 */
public class PlagueViewController {

    private static final Logger logger = Logger.getLogger("drPlant.controller.PlagueViewController");
    /**
     *
     */
    @FXML
    private Stage stage;
    /**
     *
     */
    @FXML
    private TextField tfSearch;
    /**
     *
     */
    @FXML
    private ChoiceBox chBox;
    /**
     *
     */
    @FXML
    private TableView tbPlague;
    /**
     *
     */
    @FXML
    private TableColumn colImage;
    /**
     *
     */
    @FXML
    private TableColumn colScientName;
    /**
     *
     */
    @FXML
    private TableColumn colCommonName;
    /**
     *
     */
    @FXML
    private TableColumn colType;
    /**
     *
     */
    @FXML
    private Button btnSearch;
    /**
     *
     */
    @FXML
    private Button btnAdd;
    /**
     *
     */
    @FXML
    private Button btnEdit;
    /**
     *
     */
    @FXML
    private Button btnDelete;
    /**
     *
     */
    private boolean isAdmin;
    /**
     *
     */
    private PlagueManager plagueManager;
    /**
     *
     */
    private ObservableList<Plague> plagues;
    /**
     *
     */
    private User user;
    /**
     *
     */
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
     * Method of the logic layer called to show all pests in the table:
     * findAllPlagues();
     *
     * Behaviour of the tableView: In case of being <strong>Admin</strong> the
     * following columns of the table will be editable:
     * <ul>
     * <li>Common name</li>
     * <li>Type</li>
     * </ul>
     * Buttons will also be displayed under the table:
     * <ul>
     * <li> Add to </li>
     * <li> Edit (disabled when any row is selected) </li>
     * <li> Remove (disabled when any row is selected)</li>
     * </ul>
     *
     *
     * Double click on a row if the user is not admin: A modal window
     * (infoPlague.fxml) opens where the complete pest information is displayed.
     * To close the infoPlague window, press the close button of the window
     * itself.
     *
     * In case of <strong>User</strong> the table will not be editable. In case
     * of <strong>admin</strong>
     * Allows to modify:
     * <ul>
     * <li> Common name </li>
     * <li> Type </li>
     * </ul>
     *
     * ChoiceBox to filter the plagues. Shows "by Scient Name" by default. The
     * other filters are:
     * <ul>
     * <li> by Common name</li>
     * <li> type: light </li>
     * <li> type:middle </li>
     * <li> type: severe </li>
     * </ul>
     *
     * TextField Searched shows a promp text to indicates to the user what kind
     * of data has to be write in.
     *
     * Search button enabled and visible for both types of user.
     *
     * @param event event type: Window event
     */
    private void handleWindowShowing(WindowEvent event) {

        // table
        //colImage.setCellValueFactory(new PropertyValueFactory<>("photo"));
        colScientName.setCellValueFactory(new PropertyValueFactory<>("scienceName"));
        colCommonName.setCellValueFactory(new PropertyValueFactory<>("commonName"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        plagues = FXCollections.observableArrayList(plagueManager.findAllPlagues(new GenericType<List<Plague>>() {
        }));
        tbPlague.setItems((ObservableList) plagues);

        tbPlague.getSelectionModel().selectedItemProperty().addListener(this::handlePlagueTableSelectionChanged);
        if (isAdmin) {
            tbPlague.setEditable(true);
            colCommonName.setCellFactory(TextFieldTableCell.forTableColumn());
            colCommonName.setOnEditCommit(t -> {
                updateCommonName((CellEditEvent<Plague, String>) t);
            });
        } else {
            tbPlague.setRowFactory(new Callback() {
                @Override
                public Object call(Object tv) {
                    TableRow<Plague> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2 && (!row.isEmpty())) {
                            Plague plague = plagueManager.find(Plague.class, tfSearch.getText().trim());
                            Alert alert;

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
                                    alert = new Alert(Alert.AlertType.INFORMATION, "Ops! No se ha podido abrir la ventana de información", ButtonType.OK);
                                    alert.showAndWait();
                                    logger.log(Level.SEVERE, "PlagueView controller: Error opening users managing window: {0}", ex.getMessage());
                                }
                            } catch (ClientErrorException e) {
                                alert = new Alert(Alert.AlertType.INFORMATION, "Ops! Ha ocurrido un error inesperado, inténtelo de nuevo más tarde", ButtonType.OK);
                                alert.showAndWait();
                                //    Logger.getLogger(log(Level.SEVERE, e.getMessage(), e);
                            }
                        }
                    });
                    return row;
                }
            });
        }

        tfSearch.setPromptText("Introduce la plaga que buscas");
        tfSearch.requestFocus();

        chBox.setItems(FXCollections.observableArrayList(
                "Nombre científico", "Nombre común", "Selecciona un tipo de gravedad:",
                "Leve", "Medio", "Grave"));
        chBox.setValue("Nombre científico");

        if (isAdmin) {
            btnAdd.setVisible(true);
            btnEdit.setVisible(true);
            btnDelete.setVisible(true);
            btnEdit.setDisable(true);
            btnDelete.setDisable(true);

            btnAdd.setOnAction(this::handleAddAction);
            btnEdit.setOnAction(this::handleEditAction);
            btnDelete.setOnAction(this::handleDeleteAction);
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
     * It gathers the text that has been introduced in the textFieldBuscador and
     * the option selected in the choice box in case of finding the plague will
     * show it in the list (if is more than one plague) or will opened the
     * infoView to show the info about the plague.
     *
     * If the user has <strong>User privileges</strong> any area of the infoView
     * will be editable.
     *
     * @param event event - onClick event
     */
    @FXML
    private void handleSearchAction(ActionEvent event) {
        Alert alert;

        if (tfSearch.getText().isEmpty() && chBox.getSelectionModel().getSelectedItem().equals("Nombre científico")) {
            plagues = FXCollections.observableArrayList(plagueManager.findAllPlagues(new GenericType<List<Plague>>() {
            }));
            tbPlague.setItems((ObservableList) plagues);

        } else {

            if (chBox.getSelectionModel().getSelectedItem().equals("Nombre científico")) {

                try {
                    Plague plague = plagueManager.find(Plague.class, tfSearch.getText().trim());

                    if (plague != null) {
                        Parent root;
                        Stage stage2 = new Stage();

                        InfoPlagueController controller = new InfoPlagueController();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/infoPlague.fxml"));

                        try {
                            root = (Parent) loader.load();
                            controller = (loader.getController());
                            controller.setStage(stage2);
                            controller.initStage(root, isAdmin, plague);
                        } catch (IOException ex) {
                            //  Logger.getLogger(drPlantApplication.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    }
                } catch (ClientErrorException e) {
                    alert = new Alert(Alert.AlertType.INFORMATION, "No se ha encontrado la plaga que buscas", ButtonType.OK);
                    alert.showAndWait();
                    //    Logger.getLogger(drPlantApplication.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                }
            }

            if (chBox.getSelectionModel().getSelectedItem().equals("Nombre común")) {

                try {
                    Plague plague = plagueManager.findPlagueByCommonName(Plague.class, tfSearch.getText().trim());

                    if (plague != null) {
                        Parent root;
                        Stage stage2 = new Stage();

                        InfoPlagueController controller = new InfoPlagueController();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/infoPlague.fxml"));

                        try {
                            root = (Parent) loader.load();
                            controller = (loader.getController());
                            controller.setStage(stage2);
                            controller.initStage(root, isAdmin, plague);
                        } catch (IOException ex) {
                            //  Logger.getLogger(drPlantApplication.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    }
                } catch (ClientErrorException e) {
                    alert = new Alert(Alert.AlertType.INFORMATION, "No se ha encontrado la plaga que buscas", ButtonType.OK);
                    alert.showAndWait();
                    //  Logger.getLogger(drPlantApplication.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                }
            }

            if (chBox.getSelectionModel()
                    .getSelectedItem().equals("Selecciona un tipo de gravedad:")) {

                alert = new Alert(Alert.AlertType.INFORMATION, "Debes seleccionar un filtro!", ButtonType.OK);
                alert.showAndWait();

            }

            if (chBox.getSelectionModel()
                    .getSelectedItem().equals("Leve")) {
                try {
                    String searchedType = "light";
                    plagues = FXCollections.observableArrayList(plagueManager.findPlaguesByType(new GenericType<List<Plague>>() {
                    }, searchedType));
                    tbPlague.setItems(plagues);
                } catch (ClientErrorException e) {
                    alert = new Alert(Alert.AlertType.INFORMATION, "Ops! se ha producido un error inesperado", ButtonType.OK);
                    alert.showAndWait();
                }
            }

            if (chBox.getSelectionModel()
                    .getSelectedItem().equals("Medio")) {
                try {
                    String searchedType = "middle";
                    plagues = FXCollections.observableArrayList(plagueManager.findPlaguesByType(new GenericType<List<Plague>>() {
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

            if (chBox.getSelectionModel()
                    .getSelectedItem().equals("Grave")) {
                try {
                    String searchedType = "severe";
                    plagues = FXCollections.observableArrayList(plagueManager.findPlaguesByType(new GenericType<List<Plague>>() {
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
    }

    /**
     * The infoPlague.fxml modal window will open. all next fields (common name,
     * description, control, remedy and type) will be editable.
     *
     * @param event event- ActionEvent
     */
    @FXML
    private void handleAddAction(ActionEvent event) {
        Parent root;
        Stage stage2 = new Stage();
        try {
            InfoPlagueController controller = new InfoPlagueController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/infoPlague.fxml"));

            root = (Parent) loader.load();
            controller = (loader.getController());
            controller.setStage(stage2);
            controller.initStage(root, isAdmin, null);
        } catch (IOException ex) {
            //  Logger.getLogger(drPlantApplication.class.getName(abcd)).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Highlight the selected row and set the scient name of the plague in
     * textField searched If the user is <strong>admin</strong> enable edit
     * button and delete button
     *
     * When the row is unselected, the buttons edit and delete will be disabled
     * and the textField cleared.
     *
     * @param event event- ObservableValue
     */
    @FXML
    private void handlePlagueTableSelectionChanged(ObservableValue observable,
            Object oldValue,
            Object newValue) {

        if (newValue != null) {
            Plague p = (Plague) tbPlague.getSelectionModel().getSelectedItem();

            if (isAdmin) {
                btnEdit.setDisable(false);
                btnDelete.setDisable(false);
            }
            tfSearch.setText(p.getScienceName());
        } else {
            tfSearch.setText("");
            tfSearch.requestFocus();;
            btnEdit.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    /**
     *
     * Shows the infoPlague view with the fields editables:
     * <ul>
     * <li> common name </li>
     * <li> description </li>
     * <li> control </li>
     * <li> remedy </li>
     * Radiobuttons:
     * <ul>
     * <li> light </li>
     * <li> middle </li>
     * <li> severe </li>
     * </ul>
     * </ul>
     *
     * @param event event- ActionEvent
     */
    @FXML
    private void handleEditAction(ActionEvent event) {

        Parent root;
        Stage stage2 = new Stage();

        Plague plagueEdit = (Plague) tbPlague.getSelectionModel().getSelectedItem();

        plagueEdit
                = plagueManager.find(Plague.class,
                        plagueEdit.getScienceName());

        try {
            InfoPlagueController controller = new InfoPlagueController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/infoPlague.fxml"));

            root = (Parent) loader.load();
            controller = (loader.getController());
            controller.setStage(stage2);
            controller.initStage(root, true, plagueEdit);

        } catch (IOException ex) {
            ex.getMessage();//Logger.getLogger(drPlantApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * An alert will be displayed indicating the action with the options YES /
     * NO. If press YES the selected pest will be removed. Method of the logic
     * layer called: remove(); If press NOT, the alert will be closed.
     *
     * @param event event - ActionEvent
     */
    @FXML
    private void handleDeleteAction(ActionEvent event) {

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
            tbPlague.refresh();
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
            if (!alert.getResult().getButtonData().isCancelButton()) {
                Platform.exit();
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

    /**
     * Update the value commonName in the table cell editable After, the table
     * will be refreshed
     *
     * @param t table to be edited
     */
    private void updateCommonName(CellEditEvent<Plague, String> t) {
        Plague plague = t.getRowValue();
        plague.setCommonName(t.getNewValue());
        plagueManager = PlagueManagerFactory.getPlagueManager();
        plagueManager.edit(plague);
        tbPlague.refresh();
    }
}
