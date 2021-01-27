/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.classes.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author saray
 */
public class MenuController {

    private static final Logger logger = Logger.getLogger("drPlant.controller.MenuController");

    @FXML
    private Stage stage;
    @FXML
    private MenuBar menu;
    @FXML
    private MenuItem allPlantsItem;
    @FXML
    private MenuItem myPlantsItem;
    @FXML
    private MenuItem allPlaguesItem;
    @FXML
    private MenuItem allEquipmentItem;
    @FXML
    private MenuItem allShopsItem;
    @FXML
    private MenuItem myProfileItem;
    @FXML
    private MenuItem closeSesionItem;

    private static User user = null;
    private Stage stageViejo;

    /**
     * Method to initialice the components of the menubar
     *
     * @param root
     */
    public void initStage(Parent root){

        allPlantsItem.setOnAction(this::handlePlantViewAction);
        allPlaguesItem.setOnAction(this::handlePlagueViewAction);
        allEquipmentItem.setOnAction(this::handleEquipmentViewAction);
        allShopsItem.setOnAction(this::handleShopViewAction);
        myProfileItem.setOnAction(this::handleViewProfileAction);
        closeSesionItem.setOnAction(this::handleCloseSessionAction);

    }

    /**
     * Method to show the generic list of plants view when the user click on it
     *
     * @param event event - The event on click
     */
    @FXML
    private void handlePlantViewAction(ActionEvent event) {
        Parent root;
        Stage stage2 = new Stage();

        ListPlantController controller = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/PlantList.fxml"));
        stageViejo.close();//Close the stage of the old stage
        try {
            root = (Parent) loader.load();
            //controller = (loader.getController());
            controller = (ListPlantController)loader.getController();
            controller.setStage(stage2);
            //stage.close();
            controller.initStage(root, user);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Can't open PlantView");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ops! Ha ocurrido un error inesperado!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Method to show the generic list of plagues view when the user click on it
     *
     * @param event
     */
    @FXML
    private void handlePlagueViewAction(ActionEvent event) {
        Parent root;
        Stage stage2 = new Stage();

        PlagueViewController controller = new PlagueViewController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/PlagueView.fxml"));
        stageViejo.close();//Close the stage of the old stage
        try {
            root = (Parent) loader.load();
            controller = (loader.getController());
            controller.setStage(stage2);
            //stage.close();
            controller.initStage(root,user);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Can't open PlagueView");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ops! Ha ocurrido un error inesperado!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Method to show the generic list of equipment view when the user click on
     * it
     *
     * @param event
     */
    @FXML
    private void handleEquipmentViewAction(ActionEvent event) {
        Parent root;
        Stage stage2 = new Stage();

        EquipmentViewController controller = new EquipmentViewController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/EquipmentView.fxml"));
        stageViejo.close();//Close the stage of the old stage
        try {
            root = (Parent) loader.load();
            controller = (loader.getController());
            controller.setStage(stage2);
            controller.initStage(root,user);
            //stage.close();

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Can't open EquipmentView");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ops! Ha ocurrido un error inesperado!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Method to show the generic list of shops view when the user click on it
     *
     * @param event
     */
    @FXML
    private void handleShopViewAction(ActionEvent event) {
        Parent root;
        
        Stage stage2 = new Stage();

        ShopViewController controller = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/ShopView.fxml"));
        stageViejo.close();//Close the stage of the old stage
        try {
            root = (Parent) loader.load();
            //controller = (loader.getController());
            controller = (ShopViewController)loader.getController();
            //stage.close();
            controller.setStage(stage2);  
            controller.initStage(root, user);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Can't open ShopView");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ops! Ha ocurrido un error inesperado!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Method to show the personal profile's view when the user click on it
     *
     * @param event
     */
    @FXML
    private void handleViewProfileAction(ActionEvent event) {

        Parent root;
        Stage stage2 = new Stage();

        /* MyProfileViewController controller = new MyProfileViewController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/MyProfileView.fxml"));
        try {
            root = (Parent) loader.load();
            controller = (loader.getController());
            controller.setStage(stage2);
            controller.initStage(root);
            stage.close();

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Can't open MyProfileView");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ops! Ha ocurrido un error inesperado!", ButtonType.OK);
            alert.showAndWait();
        }*/
    }

    /**
     * Method close the user's session
     *
     * @param event
     */
    @FXML
    private void handleCloseSessionAction(ActionEvent event) {
        Parent root;
        Stage stage2 = new Stage();

        LoginController controller = new LoginController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/Login.fxml"));
        stageViejo.close();//Close the stage of the old stage
        try {
            root = (Parent) loader.load();
            controller = (loader.getController());
            controller = (loader.getController());
            controller.setStage(stage2);
            controller.initStage(root);
            stage.close();

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Can't open LoginView");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ops! Ha ocurrido un error inesperado!", ButtonType.OK);
            alert.showAndWait();
        }
    }
    /**
     * Method to take the user
     * @param user 
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * Method to take the stage of the window
     * @param stage 
     */
    public void setStageOld(Stage stage) {
        this.stageViejo = stage;
    }

}
