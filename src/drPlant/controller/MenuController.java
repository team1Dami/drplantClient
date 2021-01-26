/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

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

    /**
     * Method to initialice the components of the menubar
     *
     * @param root
     */
    public void initStage(Parent root) {

        allPlantsItem.setOnAction(this::handlePlantViewAction);
        myPlantsItem.setOnAction(this::handleMyPlantsViewAction);
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

        /*    FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("PlantView.fxml"));
            Parent root = (Parent) loader.load();
            PlantViewController controller = ((PlantViewController) loader.getController());
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root);*/
    }

    /**
     * Method to show the personal list of plants view when the user click on it
     *
     * @param event
     */
    @FXML
    private void handleMyPlantsViewAction(ActionEvent event) {
        /*    FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("MyPlantsView.fxml"));
            Parent root = (Parent) loader.load();
            MyPlantsViewController controller = ((MyPlantsViewController) loader.getController());
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root);*/
    }

    /**
     * Method to show the generic list of plagues view when the user click on it
     *
     * @param event
     */
    @FXML
    private void handlePlagueViewAction(ActionEvent event) {
       /*if (!stage.getTitle().equals("Lista de Plagas")) {
            try {
                Stage stage2 = new Stage();
                FXMLLoader loader
                        = new FXMLLoader(getClass().getResource("PlagueView.fxml"));
                Parent root = (Parent) loader.load();
                PlagueViewController controller = ((PlagueViewController) loader.getController());
                controller = (loader.getController());
                controller.setStage(stage2);
                controller.initStage(root);
                
                stage.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Can't opne PlagueView");
                Alert alert = new Alert(Alert.AlertType.WARNING, "Ops! Ha ocurrido un error inesperado!", ButtonType.OK);
                alert.showAndWait();
            }
        }*/
    }

    /**
     * Method to show the generic list of equipment view when the user click on
     * it
     *
     * @param event
     */
    @FXML
    private void handleEquipmentViewAction(ActionEvent event) {
        /*    FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("EquipmentView.fxml"));
            Parent root = (Parent) loader.load();
            EquipmentViewController controller = ((EquipmentViewController) loader.getController());
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root);*/
    }

    /**
     * Method to show the generic list of shops view when the user click on it
     *
     * @param event
     */
    @FXML
    private void handleShopViewAction(ActionEvent event) {
        /*    FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("ShopView.fxml"));
            Parent root = (Parent) loader.load();
            ShopViewController controller = ((ShopViewController) loader.getController());
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root);*/
    }

    /**
     * Method to show the personal profile's view when the user click on it
     *
     * @param event
     */
    @FXML
    private void handleViewProfileAction(ActionEvent event) {
        /*    FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("MyProfileView.fxml"));
            Parent root = (Parent) loader.load();
            MyProfileViewController controller = ((MyProfileViewController) loader.getController());
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root);*/
    }

    /**
     * Method close the user's session
     *
     * @param event
     */
    @FXML
    private void handleCloseSessionAction(ActionEvent event) {
        /*    FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            Parent root = (Parent) loader.load();
            LoginController controller = ((LoginViewController) loader.getController());
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root);*/
    }
}

