/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

//import drPlant.classes.Plant;
import DrPlant.enumerations.Climate;
import DrPlant.enumerations.PetFriendly;
import DrPlant.enumerations.PlantType;
import DrPlant.enumerations.UserPrivilege;
import drPlant.classes.Plant;
import drPlant.classes.User;
import drPlant.factory.PlantManagerFactory;
//import drPlant.factory.PlantManagerFactory;
import drPlant.factory.UserManagerFactory;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author Ruben
 */
public class InfoPlantController {

    private static final Logger LOGGER = Logger.getLogger("drPlant.controller.PlantFilterController");
    @FXML
    private Stage stage;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtScience;

    @FXML
    private TextField txtCommon;

    @FXML
    private TextArea txtCares;

    @FXML
    private TextArea txtDescription;

    @FXML
    private CheckBox chbDog;

    @FXML
    private CheckBox chbCat;

    @FXML
    private CheckBox chbSave;

    @FXML
    private ChoiceBox cbClimate;

    @FXML
    private ChoiceBox cbType;

    @FXML
    private DatePicker date;
    
    @FXML
    private Label lblType;
    
    @FXML
    private Label lblClimate;
    
    @FXML
    private Label lblDescription;
    
    @FXML
    private Label lblCares;

    private User user;
    private boolean isAdmin = true;
    private Plant plant;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        /*if(user.getPrivilege()==UserPrivilege.ADMIN){
            isAdmin=true;
        }*/
        this.user = user;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
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
    public void initStage(Parent root, boolean isAdmin) {
        this.isAdmin = isAdmin;

        Scene scene = new Scene(root);
        stage = new Stage();
        //Set stage properties
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Lista plantas");
        stage.setResizable(false);
        stage.setOnShowing(this::handleShowWindow);

        stage.show();

    }

    public void initStage(Parent root, Plant plant, User user) {
        if (user.getPrivilege() == UserPrivilege.ADMIN) {
            isAdmin = true;
        } else {
            isAdmin = false;
        }
        this.plant = plant;
        Scene scene = new Scene(root);
        stage = new Stage();
        //Set stage properties
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Info planta");
        stage.setResizable(false);
        stage.setOnShowing(this::handleShowWindow);

        stage.show();

    }

    private void handleShowWindow(WindowEvent e) {

        if (!isAdmin) {
            txtScience.setEditable(false);
            txtCommon.setEditable(false);
            txtDescription.setEditable(false);
            txtCares.setEditable(false);
            cbClimate.setDisable(true);
            cbType.setDisable(true);
            chbCat.setDisable(true);
            chbDog.setDisable(true);
            btnEdit.setVisible(false);
            btnEdit.setDisable(false);
            btnCancel.setVisible(false);
            btnCancel.setDisable(false);
            btnSave.setVisible(false);
            btnSave.setDisable(false);
            //chbSave

        } else {
            txtCommon.setEditable(true);
            txtDescription.setEditable(true);
            txtCares.setEditable(true);
            cbClimate.setDisable(false);
            cbType.setDisable(false);
            chbCat.setDisable(false);
            chbDog.setDisable(false);

            btnEdit.setVisible(false);
            //btnEdit.setDisable(false);
            btnCancel.setVisible(true);
            btnCancel.setDisable(false);
            btnSave.setVisible(true);
            btnSave.setDisable(false);
        }

        cbType.setItems(FXCollections.observableArrayList(
                "Interior", "Exterior", "Suculenta")
        );
        cbClimate.setItems(FXCollections.observableArrayList(
                "Calido", "Frio", "Humedo", "Seco")
        );

        plant = PlantManagerFactory.getPlantManager().find(Plant.class, plant.getScienceName());
        setValues();

        txtScience.focusedProperty().addListener(this::handleNameChange);

        btnSave.setOnAction(this::handleButtonSave);
        btnCancel.setOnAction(this::handleButtonCancel);
        btnEdit.setOnAction(this::handleButtonEdit);
    }

    private void setValues() {
        txtScience.setText(plant.getScienceName());
        txtCommon.setText(plant.getCommonName());
        txtCares.setText(plant.getCares());
        txtDescription.setText(plant.getDescription());
        if (plant.getPlantType() == PlantType.indoor) {
            cbType.setValue("Interior");
        } else if (plant.getPlantType() == PlantType.outdoor) {
            cbType.setValue("Exterior");
        } else if (plant.getPlantType() == PlantType.succulent) {
            cbType.setValue("Suculenta");
        }

        if (plant.getPetfriendly() == PetFriendly.both) {
            chbCat.setSelected(true);
            chbDog.setSelected(true);
        } else if (plant.getPetfriendly() == PetFriendly.cat) {
            chbCat.setSelected(true);
            chbDog.setSelected(false);
        } else if (plant.getPetfriendly() == PetFriendly.dog) {
            chbCat.setSelected(false);
            chbDog.setSelected(true);
        }
        if (plant.getClimate() == Climate.cold) {
            cbClimate.setValue("Frío");
        } else if (plant.getClimate() == Climate.hot) {
            cbClimate.setValue("Calido");
        } else if (plant.getClimate() == Climate.dry) {
            cbClimate.setValue("Seco");
        } else if (plant.getClimate() == Climate.wet) {
            cbClimate.setValue("Humedo");
        }
    }

    private void handleButtonEdit(ActionEvent e) {
        txtCares.setEditable(true);
        txtCommon.setEditable(true);
        txtScience.setEditable(true);
        txtDescription.setEditable(true);
        chbCat.setDisable(false);
        chbDog.setDisable(false);
        cbClimate.setDisable(false);
        cbType.setDisable(false);
        btnCancel.setDisable(false);
        btnSave.setDisable(false);

    }

    private void handleButtonSave(ActionEvent e) {

        if (!ValidateText(txtDescription.getText())) {
            System.out.println("Solo letras");
        }

        plant.setCommonName(txtCommon.getText());
        plant.setCares(txtCares.getText());
        /*if(cbClimate.getValue().toString().equals("Calido")){
            plant.setClimate(Climate.hot);
        }*/
        if (cbClimate.getValue().equals("Calido")) {
            plant.setClimate(Climate.hot);
        } else if (cbClimate.getValue().equals("Frio")) {
            plant.setClimate(Climate.cold);
        } else if (cbClimate.getValue().equals("Humedo")) {
            plant.setClimate(Climate.wet);
        } else if (cbClimate.getValue().equals("Seco")) {
            plant.setClimate(Climate.dry);
        }
        if (cbType.getValue().equals("Interior")) {
            plant.setPlantType(PlantType.indoor);
        } else if (cbType.getValue().equals("Exterior")) {
            plant.setPlantType(PlantType.outdoor);
        } else if (cbType.getValue().equals("Suculenta")) {
            plant.setPlantType(PlantType.succulent);
        }
        if (chbCat.isSelected() & chbDog.isSelected()) {
            plant.setPetfriendly(PetFriendly.both);
        } else if (chbCat.isSelected() & !chbDog.isSelected()) {
            plant.setPetfriendly(PetFriendly.cat);
        } else if (!chbCat.isSelected() & chbDog.isSelected()) {
            plant.setPetfriendly(PetFriendly.dog);
        } else if (!chbCat.isSelected() & !chbDog.isSelected()) {
            plant.setPetfriendly(PetFriendly.no);
        }
        try {
            Plant planta = PlantManagerFactory.getPlantManager().find(Plant.class, plant.getScienceName());
            if (planta == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
            } else {
                PlantManagerFactory.getPlantManager().edit(plant);
            }
        } catch (InternalServerErrorException server) {
            
        }

    }

    private void handleButtonCancel(ActionEvent e) {
        btnCancel.setDisable(true);
        btnSave.setDisable(true);
        txtCares.setEditable(false);
        txtCommon.setEditable(false);
        txtScience.setEditable(false);
        txtDescription.setEditable(false);
        chbCat.setDisable(true);
        chbDog.setDisable(true);
        cbClimate.setDisable(true);
        cbType.setDisable(true);
        //this.stage.close();
    }

    private void handleNameChange(ObservableValue observable,
            Boolean oldValue,
            Boolean newValue) {
        if (newValue) {
            LOGGER.info("onFocus");
        } else if (oldValue) {
            LOGGER.info("onBlur");
            if (!ValidateText(txtScience.getText())) {
                System.out.println("mal");
            }
        }
    }

    /*private void handleCommonNameChange(ObservableValue observable,
            Boolean oldValue,
            Boolean newValue) {
        if (newValue) {
            LOGGER.info("onFocus");
        } else if (oldValue) {
            LOGGER.info("onBlur");
            if (!ValidateText(txtCommon.getText())) {
                System.out.println("mal");
            }
        }
    }

    private void handleDescriptionChange(ObservableValue observable,
            Boolean oldValue,
            Boolean newValue) {
        if (newValue) {
            LOGGER.info("onFocus");
        } else if (oldValue) {
            LOGGER.info("onBlur");
            if (!ValidateText(txtDescription.getText())) {
                System.out.println("mal");
            }
        }
    }*/

    private boolean ValidateText(String text) {
        // Patron para validar el email
        Pattern pattern = Pattern.compile("[a-zA-Z]*");

        Matcher mather = pattern.matcher(text);
        if (!mather.find()) {
            return false;
        } else {
            return true;
        }
    }
}
