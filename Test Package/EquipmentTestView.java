package TestFX;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import drPlant.controller.EquipmentViewController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

/**
 *
 * @author eneko
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EquipmentTestView extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        try {
            EquipmentViewController controller = new EquipmentViewController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/EquipmentView.fxml"));

            root = (Parent) loader.load();
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(EquipmentTestView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(EquipmentTestView.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    public void test1() throws InterruptedException {
        verifyThat("#btnAdd", isEnabled());

    }

}
