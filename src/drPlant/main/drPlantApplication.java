package drplant.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import drPlant.controller.EquipmentViewController;
import drPlant.factory.UserManagerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class drPlantApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            EquipmentViewController controller = new EquipmentViewController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/drPlant/view/EquipmentView.fxml"));

            try {
                root = (Parent) loader.load();
                controller = (loader.getController());
                controller.setStage(primaryStage);
                controller.initStage(root);
            } catch (IOException ex) {
                //Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            //Logger.getLogger(LoginLogoutCliente.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        /*System.out.println("Mete el email");
        String cadena = "";
        InputStreamReader entrada = new InputStreamReader(System.in);
        BufferedReader teclado = new BufferedReader(entrada);
        try {
        cadena = teclado.readLine();
        } catch (IOException e) {
        System.out.println("Error en la entrada de datos");
        }
        System.out.println("El email es "+cadena);
        UserManagerFactory.getUserManager().resetPassword(cadena);*/
    }

}
