/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import drPlant.main.drPlantApplication;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import org.junit.Assert;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
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
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import org.testfx.matcher.control.TableViewMatchers;

/**
 *
 * @author rubir
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlantTest extends ApplicationTest {

    private TableView table = lookup("#tblPlants").queryTableView();;
    private static final String OVERSIZED_TEXT = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
   

      
@BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(drPlantApplication.class);
        
    }
@Test
    public void testA_initialize(){
        clickOn("#tfLogin");
        write("ruben");
        clickOn("#tfPasswd");
        write("abcd");
        clickOn("Entrar");
    } 

    @Test
    public void testA_initialInteraction() {
        //clickOn("#txtSearch");
        //verifyThat("#tfSearch",  hasText(""));
        verifyThat("#btnSearch", isEnabled());
        verifyThat("#btnAdd", isEnabled());
        verifyThat("#btnEdit", isDisabled());
        verifyThat("#btnRemove", isDisabled());
        verifyThat("#clScienceName", isVisible());
        verifyThat("#clCommonName", isVisible());
        verifyThat("#clAnimal", isVisible());
        verifyThat("#clType", isVisible());
        verifyThat("#clClimate", isVisible());
    }

    @Test
    public void testB_VerifyAddPlant() {
        int rowCount=table.getItems().size();
        clickOn("#btnAdd");
        clickOn("#txtScience");
        write("Prueba");
        clickOn("#txtCommon");
        write("PruebaCommon");
        clickOn("#cbType");
        clickOn("Interior");
        clickOn("#cbClimate");
        clickOn("Calido");
        
        clickOn("#btnSave");
        clickOn("Aceptar");
        closeCurrentWindow();
        clickOn("#btnRefresh");
        Assert.assertNotEquals(rowCount,table.getItems().size());
    }
    
    
    
    @Test
    public void testC_VerifyTableChange() throws InterruptedException{
        clickOn("Acalifa");
        clickOn("Acalifa");        
        write("Hola");
        press(KeyCode.ENTER);        
        assertThat(table, TableViewMatchers.hasTableCell("Hola"));
        clickOn("Hola");
        clickOn("Hola");
        write("Acalifa");
    }
    @Test
    public void testD_VerifyDelete(){
        int rowCount=table.getItems().size();
        clickOn("Prueba");
        clickOn("#btnRemove");
        clickOn("Aceptar");
        assertNotEquals(rowCount,table.getItems().size());
    }
    @Test
    public void testE_verifySearchByClimate(){
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",rowCount,0);
        clickOn("#cbClimate");
        clickOn("Calido");
        clickOn("#btnSearch");
        assertNotEquals(table.getItems().size(),rowCount);
    }
    @Test
    public void testF_verifySearchByTypeAndClimate(){
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",rowCount,0);
        clickOn("#cbType");
        clickOn("Interior");
        clickOn("#btnSearch");
        assertNotEquals(table.getItems().size(),rowCount);
        
    }
    
    @Test
    public void testG_verifySearchClimate(){
        clickOn("Plantas");
        clickOn("Lista general");
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",rowCount,0);
        clickOn("#cbClimate");
        clickOn("Calido");
        clickOn("#btnSearch");
        assertNotEquals(table.getItems().size(),rowCount);
        
    }
    @Test
    public void testH_verifySearchClimateAndPet(){
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",rowCount,0);
        clickOn("#cbPet");
        clickOn("Perro");
        clickOn("#btnSearch");
        assertNotEquals(table.getItems().size(),rowCount);
        
    }
    @Test
    public void testI_verifySearchByName(){
        clickOn("Plantas");
        clickOn("Lista general");
        clickOn("#txtSearch");
        write("Nepeta cataria");
        clickOn("#btnSearch");
        
        assertThat(table, TableViewMatchers.hasTableCell("Nepeta cataria"));
        Assert.assertEquals(table.getItems().size(),1);
        
    }
    @Test
    public void testJ_verifySearchByAllTypes(){
        clickOn("Plantas");
        clickOn("Lista general");
        clickOn("#cbType");
        clickOn("Exterior");
        clickOn("#cbPet");
        clickOn("Perro");
        clickOn("#cbClimate");
        clickOn("Calido");
        clickOn("#btnSearch");
        
        assertThat(table, TableViewMatchers.hasTableCell("hot"));
        assertThat(table, TableViewMatchers.hasTableCell("indoor"));
        assertThat(table, TableViewMatchers.hasTableCell("dog"));
        
    }
    
    
}
