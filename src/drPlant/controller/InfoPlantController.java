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
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.ClientErrorException;
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
    private ChoiceBox cbClimate;

    @FXML
    private ChoiceBox cbType;

    @FXML
    private Label lblType;

    @FXML
    private Label lblClimate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblCares;
    
    

    private User user;
    private boolean isAdmin;
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

        } else {
            txtCommon.setEditable(true);
            txtDescription.setEditable(true);
            txtCares.setEditable(true);
            cbClimate.setDisable(false);
            cbType.setDisable(false);
            chbCat.setDisable(false);
            chbDog.setDisable(false);

            btnEdit.setVisible(false);
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

        if (plant != null) {
            plant = PlantManagerFactory.getPlantManager().find(Plant.class, plant.getScienceName());
            setValues();
        }

        txtCommon.focusedProperty().addListener(this::handleNameChange);

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

        btnEdit.setVisible(false);
    }

    private void handleButtonSave(ActionEvent e) {

        /* if (!ValidateText(txtDescription.getText())) {
            System.out.println("Solo letras");
        }*/
        boolean correct = true;
        if (!ValidateText(txtDescription.getText())) {
            correct = false;
        }
        if (!ValidateText(txtCares.getText())) {
            correct = false;
        }
        if (correct) {
            Plant newPlant = new Plant();
            newPlant.setScienceName(txtScience.getText());
            newPlant.setCommonName(txtCommon.getText());
            newPlant.setCares(txtCares.getText());
            if (cbClimate.getValue().equals("Calido")) {
                newPlant.setClimate(Climate.hot);
            } else if (cbClimate.getValue().equals("Frio")) {
                newPlant.setClimate(Climate.cold);
            } else if (cbClimate.getValue().equals("Humedo")) {
                newPlant.setClimate(Climate.wet);
            } else if (cbClimate.getValue().equals("Seco")) {
                newPlant.setClimate(Climate.dry);
            }
            if (cbType.getValue().equals("Interior")) {
                newPlant.setPlantType(PlantType.indoor);
            } else if (cbType.getValue().equals("Exterior")) {
                newPlant.setPlantType(PlantType.outdoor);
            } else if (cbType.getValue().equals("Suculenta")) {
                newPlant.setPlantType(PlantType.succulent);
            }
            if (chbCat.isSelected() & chbDog.isSelected()) {
                newPlant.setPetfriendly(PetFriendly.both);
            } else if (chbCat.isSelected() & !chbDog.isSelected()) {
                newPlant.setPetfriendly(PetFriendly.cat);
            } else if (!chbCat.isSelected() & chbDog.isSelected()) {
                newPlant.setPetfriendly(PetFriendly.dog);
            } else if (!chbCat.isSelected() & !chbDog.isSelected()) {
                newPlant.setPetfriendly(PetFriendly.no);
            }
            if (plant != null) {
                try {
                    Plant plantSearch = PlantManagerFactory.getPlantManager().find(Plant.class, newPlant.getScienceName());

                    PlantManagerFactory.getPlantManager().edit(newPlant);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha editado correctamente");
                    alert.show();

                } catch (InternalServerErrorException server) {

                }
            } else {
                try {
                    plant = PlantManagerFactory.getPlantManager().find(Plant.class, txtScience.getText());
                } catch (ClientErrorException client) {
                    plant = null;
                }
                if (plant == null) {
                    try {
                        PlantManagerFactory.getPlantManager().create(newPlant);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha añadido correctamente");
                        alert.show();

                    } catch (ClientErrorException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error al crear");
                        alert.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Planta ya existe");
                    alert.show();
                }
            }
        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR,"Introduce correct values");
            alert.show();
        }

    }

    private void handleButtonCancel(ActionEvent e) {
        btnCancel.setDisable(true);
        btnSave.setDisable(true);
        btnEdit.setVisible(true);
        btnEdit.setDisable(false);
        txtCares.setEditable(false);
        txtCommon.setEditable(false);
        txtScience.setEditable(false);
        txtDescription.setEditable(false);
        chbCat.setDisable(true);
        chbDog.setDisable(true);
        cbClimate.setDisable(true);
        cbType.setDisable(true);
        setValues();
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
    
    private boolean ValidateName(String text) {
        // Patron para validar el email
        Pattern pattern = Pattern.compile("[a-zA-Z]*");

        Matcher mather = pattern.matcher(text);
        if (!mather.find()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean ValidateText(String text) {
        // Patron para validar el email
        if (text.length() > 500) {
            return false;
        } else {
            return true;
        }
    }

}
