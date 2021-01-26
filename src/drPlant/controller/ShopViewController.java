/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.classes.Shop;
import drPlant.classes.User;
import drPlant.factory.ShopManagerFactory;
import drPlant.interfaces.ShopManager;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;

/**
 * FXML Controller class
 * Controller of the view of the shops with a complete CRUD
 * 
 * @author gonza
 */
public class ShopViewController  {

    private Alert alert;
    private static final Logger logger = Logger.getLogger("drplant.Controller.ShopViewController");
    private Shop shop;
    
    @FXML
     private Stage stage;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView tabla;
    @FXML
    private TableColumn nombre;
    @FXML
    private TableColumn localizacion;
    @FXML
    private TableColumn comision;
    
    @FXML
    private TextField tfBuscar;
    
    private User user=null;
    private boolean admin=false;
    
    
      
  
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
    public void initStage(Parent root,User user) {
 
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("Lista Tiendas");
        
        if(user.getPrivilege().equals("ADMIN")){
        admin=true;
        }
        if(!admin){
            btnCrear.setVisible(false);
            btnEliminar.setVisible(false);
        }
        stage.show();
        
        stage.setOnShowing(this::handleWindowShowing);
        
        btnEliminar.setDisable(true);

        tfBuscar.textProperty().addListener(this::textChange);
        btnCrear.setOnAction(this::handleButtonCrear);
        btnEliminar.setOnAction(this::handleButtonEliminar);
        
        btnBuscar.setOnAction(this::handleButtonSearch);
        
        
        stage.setOnCloseRequest(this::setOncloseRequest);
        
        InsertDataIntoTable(tabla);
        
    }
    
    /**
     * method that asks when you press the x if you want to go back or not, if you press OK you go back to login otherwhise you stay in the signup
     * @param we 
     */
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
     
     /**
      * Method to enter the view to create a new shop
      * @param event 
      */
    private void handleButtonCrear(ActionEvent event) {
         
         FXMLLoader loader;
         Parent root;
         Stage newStage = new Stage();
         
         try {
            loader = new FXMLLoader(getClass().getResource("/drPlant/view/InfoTienda.fxml"));
            root = (Parent) loader.load();
            InfoTiendaController controller = (loader.getController());
            controller.setStage(newStage);
            //controller.setStage(stage);
            controller.initStage(root,admin);
            newStage.showAndWait();
            
            InsertDataIntoTable(tabla);
           
         } catch (IOException ex) {
             Logger.getLogger(ShopViewController.class.getName()).log(Level.SEVERE, null, ex);
         }
    } 
    /**
     * Method to remove a shop
     * @param event 
     */ 
    private void handleButtonEliminar(ActionEvent event) {
         
        Shop shop = (Shop) tabla.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Borrar la fila seleccionada?\n"
                + "Esta operación no se puede deshacer.",
                ButtonType.OK, ButtonType.CANCEL);
        alert.getDialogPane();
        Optional<ButtonType> result = alert.showAndWait();
        //If OK to deletion
        if (result.isPresent() && result.get() == ButtonType.OK) {

            ShopManagerFactory.getShopManager().remove(shop.getId());
            InsertDataIntoTable(tabla);
            
        }
        
    }
    
    /**
     *Method to control if there's a item selected in the table 
     * @param observableValue
     * @param oldValue
     * @param newValue 
     */
    private void handleTableSelectionChanged(ObservableValue observableValue,Object oldValue, Object newValue){//como cojer una fila seleccionada

      if(newValue!=null){
        btnEliminar.setDisable(false);
            
        }else{
          btnEliminar.setDisable(true);
      }
    }
    
    /**
     * Metodo que controla el evento que se lanza mientras se muestra la ventana
     * No reacciona
     * @param e evento de ventana
     */
    public void handleWindowShowing(WindowEvent e){
        
        tfBuscar.textProperty().addListener(this::textChange);
        btnCrear.setOnAction(this::handleButtonCrear);
        btnEliminar.setOnAction(this::handleButtonEliminar);
        
        btnBuscar.setOnAction(this::handleButtonSearch);
        
        
        stage.setOnCloseRequest(this::setOncloseRequest);
        
        InsertDataIntoTable(tabla);
    }
    
    /**
     * Method to search for a specific shop by shop name in the table
     * @param e 
     */
    public void handleButtonSearch(ActionEvent e){
        try{   
            if(tfBuscar.getText().isEmpty()){
                  alert = new Alert(Alert.AlertType.WARNING, "Escribe algo primero,JETA", ButtonType.OK);
                  InsertDataIntoTable(tabla);
                  alert.showAndWait();
            }else{
                
                ShopManager manager= ShopManagerFactory.getShopManager();
                Shop shop = manager.getShopByName(Shop.class, tfBuscar.getText());
                
                if(shop.getShop_name().equals(tfBuscar.getText())){
                     FXMLLoader loader;
                     Parent root;
                     Stage newStage = new Stage();
                     
                      InfoTiendaController controller = new InfoTiendaController();
                      loader = new FXMLLoader(getClass().getResource("/drPlant/view/InfoTienda.fxml"));
                      root = (Parent) loader.load();
                      controller = (loader.getController());
                      controller.setStage(newStage);
                      controller.initStage(root,admin,shop);
                      newStage.showAndWait();
                    
                }           

            }
        }catch(Exception w){
            logger.info("No me ha funcionado la busqueda, Recorcholis!");
            alert = new Alert(Alert.AlertType.WARNING, "No se ha encontrado", ButtonType.OK);
            alert.showAndWait();
        }
    }
    
    /**
     * Method to see the data of a shop when you double click in a item of the table
     * @param e 
     */
    public void handleTableClickBouble(MouseEvent e){
        
        if(e.getClickCount()==2){ 
             FXMLLoader loader;
             Parent root;
             Stage newStage = new Stage();
             Shop shopp=(Shop) tabla.getSelectionModel().getSelectedItem();
         
            try {
                InfoTiendaController controller = new InfoTiendaController();
                loader = new FXMLLoader(getClass().getResource("/drPlant/view/InfoTienda.fxml"));
                root = (Parent) loader.load();
                controller = (loader.getController());
                controller.setStage(newStage);
                controller.initStage(root,admin,shopp);
                newStage.showAndWait();
           } catch (IOException ex) {
                 Logger.getLogger(ShopViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
   
    /**
     * Method to insert all the shop into the table
     * @param tabla 
     */
    public void InsertDataIntoTable(TableView tabla){
        
         //prueba de la tabla
        nombre.setCellValueFactory(new PropertyValueFactory<>("shop_name"));
        localizacion.setCellValueFactory(new PropertyValueFactory<>("location"));
        comision.setCellValueFactory(new PropertyValueFactory<>("commission"));
        try{
        
            ObservableList<Shop>shops;
            ShopManager manager = ShopManagerFactory.getShopManager();
            shops = FXCollections.observableArrayList(manager.findAllShops(new GenericType <List<Shop>>(){}));
            tabla.setItems(shops);
            tabla.getSelectionModel().selectedItemProperty()
            .addListener(this::handleTableSelectionChanged);

            tabla.setOnMouseClicked(this::handleTableClickBouble);
            tabla.refresh();

            logger.info("Se carga la tabla correctamente");
        }catch(Exception w){
            logger.info("Se carga la tabla a fallado");
        }
    }
    
     /**
     * Text changed event handler. not more than 255 character in the textfield
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void textChange(ObservableValue observable, String oldValue, String newValue) {
   
        if (tfBuscar.getText().length() > 255 ) {
             alert = new Alert(Alert.AlertType.WARNING, "No te pases de listo", ButtonType.OK);
            alert.showAndWait();
        } 
    }
  
}
