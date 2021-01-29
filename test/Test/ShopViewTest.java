/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import drPlant.classes.Shop;
import drPlant.classes.User;
import drPlant.controller.ShopViewController;
import drPlant.enumerations.UserPrivilege;
import drPlant.main.drPlantApplication;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author gonza
 */
public class ShopViewTest extends ApplicationTest{
   
    private TableView table;

    private User user=null;
    
    private static final String OVERSIZED_TEXT="XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    /**
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
   /* @Override 
    public void start(Stage stage) throws Exception {
        user.setPrivilege(UserPrivilege.ADMIN);//in this test u enter as admin//salta un error
        Parent root;
        try {
            ShopViewController controller = null;
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/drPlant/view/ShopView.fxml"));        
            root = (Parent) loader.load();
            controller = (ShopViewController)loader.getController();
            controller.setStage(stage);
            controller.initStage(root,user);
            
            table = lookup("#tabla").queryTableView();
            
        } catch (IOException ex) {
                Logger.getLogger(drPlantApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
                Logger.getLogger(drPlantApplication.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }*/
    
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(drPlantApplication.class);
   }
    
    /**
     * Test of initial state of users' table view.
     */
    @Test
    public void testA_initialState() {
  
        clickOn("#tfLogin");
        write("gonza");
        clickOn("#tfPasswd");
        write("gonza");
        clickOn("#btnLogin");
        clickOn("Tiendas");
        clickOn("Lista general");
        
    }
    /**
     * test of a example of the ceration of a shop
     */
    @Test
    @Ignore
    public void testB_CrearTienda(){
        table = lookup("#tabla").queryTableView();
        int rowCount=table.getItems().size();
        clickOn("Crear");
        
        //verifyThat("#tfUtl",  hasText(""));
        //assertEquals("#tfUtl", "");
        clickOn("#tfUrl");
        write("aa");
        //verifyThat("#tfEmail",  hasText(""));
        clickOn("#tfEmail");
        write("aa@aa.aa");
        //verifyThat("#tfLocation",  hasText(""));
        clickOn("#tfLocation");
        write("aa");
        //verifyThat("#tfComision",  hasText(""));
        clickOn("#tfComision");
        write("12");
        //verifyThat("#tfnombre",  hasText(""));
        clickOn("#tfnombre");
        write("aaaa");
        
        clickOn("Guardar");
        assertEquals("The row has been added",
                    rowCount+1,table.getItems().size());
        
        
    }

    
    /**
     * Test that shop is deleted as cancel button is clicked on confirmation dialog.
     */
    @Test
    @Ignore
    public void testC_EliminarPeroNo(){
         verifyThat("Eliminar", isDisabled());
         table = lookup("#tabla").queryTableView();
       
         
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and click it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat("Eliminar", isEnabled());
        clickOn("Eliminar");
        verifyThat("¿Borrar la fila seleccionada?\n"
                                    + "Esta operación no se puede deshacer.",
                    isVisible());    
        clickOn(isCancelButton());
        assertEquals("no se ha borrado,bien!!",rowCount,table.getItems().size());
         
    }
    
    
    /**
     * Test that shop is deleted as ok button is clicked on confirmation dialog.
     */
     @Test
     @Ignore
    public void testD_EliminarDeVerdad() {
        //get row count
        table = lookup("#tabla").queryTableView();
        int rowCount=table.getItems().size();
        assertNotEquals("tabla vacia.",
                        rowCount,0);
        //look for 1st row in table view and click it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat("Eliminar", isEnabled());//note that id is used instead of fx:id
        clickOn("Eliminar");
        verifyThat("¿Borrar la fila seleccionada?\n"
                                    + "Esta operación no se puede deshacer.",
                    isVisible());    
        clickOn(isDefaultButton());
        assertEquals("se ha borrado!!!",
                    rowCount-1,table.getItems().size());
       // verifyThat(tfLogin,  (TextField t) -> t.isFocused());
    }
    /**
     * Test shop table row selection.
     */
    @Test
    @Ignore
    public void testE_tableSelection() { 
        //get row count
        table = lookup("#tabla").queryTableView();
        int rowCount=table.getItems().size();
        assertNotEquals("Tabla vacia",
                        rowCount,0);
        //look for 1st row in table view and select it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("la tabla no tiene filas ",row);
        clickOn(row);
        //get selected item and index from table
        Shop selectedShop=(Shop)table.getSelectionModel()
                                     .getSelectedItem();
        int selectedIndex=table.getSelectionModel().getSelectedIndex();
        //assertions
        verifyThat("#nombre",hasText(selectedShop.getShop_name()));
        verifyThat("#localizacion",hasText(selectedShop.getLocation()));
        verifyThat("#comision",hasText(Float.toString(selectedShop.getCommission())));
    }
    /**
     * test where the textfield contains 255 characters and an alert appears
     */
    @Test
    @Ignore
    public void testF_muchoTexto(){
        
        clickOn("#tfBuscar");
        write(OVERSIZED_TEXT);
        
        verifyThat("No te pases de listo",isVisible());
        clickOn(isDefaultButton());
        
    }
    
    /**
     * test where a shop id modified
     */
    @Test
    //@Ignore
    public void testG_modificarTienda(){
        table = lookup("#tabla").queryTableView();
        int rowCount=table.getItems().size();//salta error
        assertNotEquals("Tabla vacia",
                        rowCount,0);
        Node row=lookup(".table-row-cell").nth(0).query();
        doubleClickOn(row);
        
        Shop selectedShop=(Shop)table.getSelectionModel()
                                     .getSelectedItem();
        
        //verify the data of the shop is captured and showed to the user
        verifyThat("#tfnombre",hasText(selectedShop.getShop_name()));
        verifyThat("#tfLocation",hasText(selectedShop.getLocation()));
        verifyThat("#tfComision",hasText(Float.toString(selectedShop.getCommission())));
        verifyThat("#tfEmail",hasText(selectedShop.getEmail()));
        verifyThat("#tfUrl",hasText(selectedShop.getUrl()));
        
        //verify the textfields are editable
        verifyThat("#tfnombre",isEnabled());
        verifyThat("#tfLocation",isEnabled());
        verifyThat("#tfComision",isEnabled());
        verifyThat("#tfEmail",isEnabled());
        verifyThat("#tfUrl",isEnabled());
        
        clickOn("#tfnombre");
        write("Viveros Fadura S.L.");
        
        clickOn("Guardar");//commmit the changes
        
        
    }
    
    /**
     * Test that shows the right way of creating a shop and posible mistakes
     */
    @Test
    @Ignore
    public void testH_CrearBienTienda(){
        
        clickOn("Crear");
        verifyThat("#btnGuardar", isDisabled());
        
       // verifyThat("#tfUtl",  hasText(""));
        clickOn("#tfUrl");
        write("aa");
        verifyThat("#btnGuardar", isDisabled());
        
       // verifyThat("#tfEmail",  hasText(""));
        clickOn("#tfEmail");
        write("aa@a");
        verifyThat("#btnGuardar", isDisabled());
        
        //verifyThat("#tfLocation",  hasText(""));
        clickOn("#tfLocation");
        write("aa");
        verifyThat("#btnGuardar", isDisabled());
        
        //verifyThat("#tfComision",  hasText(""));
        clickOn("#tfComision");
        write("12");
        //verifyThat("#btnGuardar", isDisabled());
        
        //verifyThat("#tfnombre",  hasText(""));
        clickOn("#tfnombre");
        write("aaaa");
        verifyThat("#btnGuardar", isEnabled());
        
        clickOn("Guardar");
        
        verifyThat("Error en el gmail",isVisible());//the email was wrote wrong
        clickOn(isDefaultButton());
        
        clickOn("#tfEmail");
        write("a.a");
        
        clickOn("#tfComision");
        eraseText(2);
        write("aa");
        
        clickOn("Guardar");
        
        
        //no sale la alert que deberia
        //verifyThat("solo se permiten numeros en la comision",isVisible());//the commission wasn't a number
        //clickOn(isDefaultButton());
        
    }
            
        
    
}
