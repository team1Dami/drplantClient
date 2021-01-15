/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class ShopViewController implements Initializable {

    private Alert alert;
    private static final Logger logger = Logger.getLogger("drplant.Controller.ShopViewController");
    
    @FXML
     private Stage stage;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnBuscar;
      
      
      
      
      
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("Lista Tiendas");
        stage.show();

        //tfLogin.textProperty().addListener(this::textChange);
 
        btnCrear.setOnAction(this::handleButtonCrear);

        stage.setOnCloseRequest(this::setOncloseRequest);
    }
    
    //method that asks when you press the x if you want to go back or not, if you press OK you go back to login otherwhise you stay in the signup
     private void setOncloseRequest(WindowEvent we){
        
         try {
           alert = new Alert(Alert.AlertType.WARNING,
                   "Desea Salir de esta ventana", ButtonType.OK,ButtonType.CANCEL);//alert to ask the user to confirm
           alert.showAndWait();
           if(alert.getResult().getButtonData().isCancelButton()){          
               alert = new Alert(Alert.AlertType.WARNING,
                       "Se ha cancelado la accion",ButtonType.OK);//alert to advise that the action has being cancel
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
     
     private void handleButtonCrear(ActionEvent event) {
         
                FXMLLoader loader;
                Parent root;
                Stage newStage = new Stage();

                try {
                     PopUpEmailController controller = new PopUpEmailController();
                     loader = new FXMLLoader(getClass().getResource("InfoTienda.fxml"));
                     root = (Parent) loader.load();
                     controller = (loader.getController());
                     controller.setStage(newStage);
                     controller.initStage(root);
                     newStage.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(ShopViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        

    
    
}
