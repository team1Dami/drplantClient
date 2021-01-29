/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testPlague;

import drPlant.classes.Plague;
import drPlant.controller.MenuController;
import drPlant.main.drPlantApplication;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;

import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

/**
 *
 * @author saray
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlagueControllerTest extends ApplicationTest {

    private Stage stage;
    private TextField tfLogin;
    private TextField tfPasswd;
    private TextField tfSearch;
    private ChoiceBox chBox;
    private TableView tbPlague;
    private TableColumn colImage;
    private TableColumn colScientName;
    private TableColumn colCommonName;
    private TableColumn colType;
    private Button btnSearch;
    private Button btnAdd;
    private Button btnEdit;
    private Button btnDelete;
    private MenuController menuControllerController;

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(drPlantApplication.class);
    }

    @Test
    public void testA_initialInteraction() {
        clickOn("#tfLogin");
        write("admin");
        clickOn("#tfPasswd");
        write("serv1doR");
        clickOn("#btnLogin");

        clickOn("Plagas");
        clickOn("Lista general");
    }

    @Test
    // @Ignore
    public void testB_initialStateForAdminUser() {   
        
        verifyThat("#tfSearch",  (TextField t) -> t.isFocused());
        verifyThat("#tbPlague", isVisible());
        verifyThat("#colImage", isVisible());
        verifyThat("#colScientName", isVisible());
        verifyThat("#colCommonName", isVisible());
        verifyThat("#colType", isVisible());
                      
        verifyThat("#btnAdd", isVisible());
        verifyThat("#btnEdit", isDisabled());
        verifyThat("#btnDelete", isDisabled());
    }

    @Test
    // @Ignore
    public void testC_CheckButtonEditAndButtonDeleteareEnabledOnClickRowOfTheTable() {

        tbPlague = lookup("#tbPlague").queryTableView();

        int rowCount = tbPlague.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //look for 1st row in table view and click it
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        verifyThat("#btnEdit", isEnabled());
        verifyThat("#btnDelete", isEnabled());
    }

    @Test
    public void testD_CheckTextFieldSearchIsNotEmptyAfterClickOnTableRow() {
        tbPlague = lookup("#tbPlague").queryTableView();

        int rowCount = tbPlague.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //look for 1st row in table view and click it
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        Plague selectedPlague = (Plague)tbPlague.getSelectionModel()
                                     .getSelectedItem();
        
        String scientRow = ((Plague)tbPlague.getItems()
                                     .get(tbPlague.getItems().size()-(tbPlague.getItems().size())))
                                     .getScienceName();
        assertEquals("TextField has not data!", scientRow, selectedPlague.getScienceName());
    }
    
    @Test
    @Ignore
    public void testE_CheckInfoPlagueViewOnClickButtonSearchAfterSelectedRow(){
        tbPlague = lookup("#tbPlague").queryTableView();

        int rowCount = tbPlague.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //look for 1st row in table view and click it
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn("#btnSearch");
        verifyThat("#infoPlague", isVisible());
        closeCurrentWindow();
    }
    
    @Test
    @Ignore
    public void testF_CheckTableEditable(){
        tbPlague = lookup("#tbPlague").queryTableView();

        int rowCount = tbPlague.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //look for 1st row in table view and click it
        Node row = lookup(".table-row-cell").nth(1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        String commonRow = ((Plague)tbPlague.getItems()
                                     .get(tbPlague.getItems().size()-(tbPlague.getItems().size()-1)))
                                     .getCommonName();
        doubleClickOn(commonRow);
        doubleClickOn(commonRow);
        press(KeyCode.DELETE);
        write("pulgón");
        press(KeyCode.ENTER);
    }
    
    @Test
    @Ignore
    public void testG_CheckDeletePlague(){
        tbPlague = lookup("#tbPlague").queryTableView();

        int rowCount = tbPlague.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        Node row = lookup(".table-row-cell").nth(0).query();
        
        clickOn(row);
        clickOn("#btnDelete");
        clickOn("Aceptar");
        
        assertEquals("The row has not been deleted!!!",rowCount-1,tbPlague.getItems().size());
        
    }
    
    @Test
    @Ignore
    public void testH_CheckAddPlague(){
        tbPlague = lookup("#tbPlague").queryTableView();

        int rowCount = tbPlague.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        
        clickOn("#btnAdd");
        clickOn("#tfScientName");      
        write("Fungis");      
        clickOn("#tfCommonName");
        write("Hongos");
        clickOn("#txAreaDescription");
        write("Hongos que infectan plantas");
        clickOn("#txAreaControl");
        write("Difícil control");
        clickOn("#txAreaRemedy");
        write("Sin remedio");      
        clickOn("#rbSevere");
        
        clickOn("#btnSaveChanges");
        clickOn("Sí");
        clickOn("Aceptar");
        
        assertEquals("The row has not been added!!!",rowCount+1,tbPlague.getItems().size());
        
    }
    @Test
    @Ignore
    public void testI_CheckEditPlague(){
        tbPlague = lookup("#tbPlague").queryTableView();

        int rowCount = tbPlague.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        
        Node row = lookup(".table-row-cell").nth(0).query();
        
        clickOn(row);
        clickOn("#btnEdit");
        clickOn("#rbMedium");
        clickOn("#btnSaveChanges");
        clickOn("Sí");
        clickOn("Aceptar");
    }

    @Test
    public void testJ_CheckDisabledButtonsIfRowIsUnSelected(){       
        tbPlague = lookup("#tbPlague").queryTableView();

        int rowCount = tbPlague.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        
        Node row = lookup(".table-row-cell").nth(0).query();       
        clickOn(row);
        
        press(KeyCode.CONTROL);
        clickOn(row);
        
        verifyThat("#btnEdit", isDisabled());
        verifyThat("#btnDelete", isDisabled());
    }
}
