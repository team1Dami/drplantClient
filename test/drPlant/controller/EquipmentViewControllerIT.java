/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.controller;

import drPlant.main.drPlantApplication;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import static org.junit.Assert.*;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;

/**
 *
 * @author eneko
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EquipmentViewControllerIT extends ApplicationTest {

    /*@Override
    public void start(Stage stage) throws Exception {
    Parent root;
    User u = new User();
    u.setPrivilege(UserPrivilege.ADMIN);
    EquipmentViewController controller = new EquipmentViewController();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/EquipmentView.fxml"));
    try {
    root = (Parent) loader.load();
    controller = (loader.getController());
    controller.setStage(stage);
    controller.initStage(root, u);
    
    } catch (IOException ex) {
    }*/
    private TableView table;
    

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(drPlantApplication.class);

    }

    @Test
    public void test0LoginAndEquipment() {
        clickOn("#tfLogin");
        write("AdminEneko");
        clickOn("#tfPasswd");
        write("admin25");
        clickOn("Entrar");
        clickOn("Equipamiento");
        clickOn("Lista general");

    }

    @Test
    public void test1CheckDefaultValues() {
        verifyThat("#btnAdd", isEnabled());
        verifyThat("#btnSearch", isEnabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#btnEdit", isDisabled());
        verifyThat("#txtSearch", hasText(""));
    }

    @Test
    public void test2AddNewEquipment() {
        table = lookup("#equipmentTable").queryTableView();
        int rowCount = table.getItems().size();
        String login = "username" + new Random().nextInt();
        clickOn("#btnAdd");
        clickOn("#inputNombre");
        write(login);
        clickOn("#inputDescription");
        write(login);
        clickOn("#choiceBoxUso");
        clickOn("Sustrato");
        doubleClickOn("#inputPrecio");
        write("9.5");
        clickOn("Guardar");
        clickOn("Aceptar");
        int newRowCount = table.getItems().size();
        assertEquals("The row has not been added!!!", rowCount + 1, newRowCount);
    }
    
    @Test
    public void test3_deleteUser() {
        //get row count
        table = lookup("#equipmentTable").queryTableView();
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and click it
        doubleClickOn("Nombre");
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat("#btnDelete", isEnabled());//note that id is used instead of fx:id
        clickOn("#btnDelete");
        /*verifyThat("¿Borrar la fila seleccionada?\n"
                                    + "Esta operación no se puede deshacer.",
                    isVisible());  */  
        clickOn("Aceptar");
        clickOn("Aceptar");
        assertEquals("The row has not been deleted!!!",
                    rowCount-1,table.getItems().size());
        
    }

}
