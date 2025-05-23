package pizzashop.utility;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.apache.log4j.helpers.Loader;
import pizzashop.controller.KitchenGUIController;
import pizzashop.controller.OrdersGUIController;
import pizzashop.service.PizzaService;

import java.io.IOException;
import java.util.Optional;

public class KitchenGUI {

    PizzaService service;

    public void KitchenGUI(PizzaService service) {
        this.service = service;
        VBox vBoxKitchen = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kitchenGUIFXML.fxml"));

            //vBoxOrders = FXMLLoader.load(getClass().getResource("/fxml/OrdersGUIFXML.fxml"));
            vBoxKitchen = loader.load();
            KitchenGUIController kitchenCtrl= loader.getController();
            kitchenCtrl.setService(service);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Kitchen");
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit Kitchen window?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = exitAlert.showAndWait();
                if (result.get() == ButtonType.YES){
                    //Stage stage = (Stage) this.getScene().getWindow();
                    stage.close();
                }
                // consume event
                else if (result.get() == ButtonType.NO){
                    event.consume();
                }
                else {
                    event.consume();
                }
            }

            });
        stage.setAlwaysOnTop(false);
        stage.setScene(new Scene(vBoxKitchen));
        stage.show();
        stage.toBack();
    }
}
