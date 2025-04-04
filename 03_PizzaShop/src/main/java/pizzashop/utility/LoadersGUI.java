package pizzashop.utility;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pizzashop.controller.OrdersGUIController;
import pizzashop.service.PizzaService;

import java.io.IOException;
import java.util.Optional;

public class LoadersGUI {

    public void loadKitchenGUI() {
        VBox vBoxKitchen = null;

        try {
            vBoxKitchen = FXMLLoader.load(getClass().getResource("/fxml/kitchenGUIFXML.fxml"));
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

//    public void displayOrdersForm(PizzaService service){
//        VBox vBoxOrders = null;
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OrdersGUIFXML.fxml"));
//
//            //vBoxOrders = FXMLLoader.load(getClass().getResource("/fxml/OrdersGUIFXML.fxml"));
//            vBoxOrders = loader.load();
//            OrdersGUIController ordersCtrl= loader.getController();
//            ordersCtrl.setService(service, tableNumber);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Stage stage = new Stage();
//        stage.setTitle("Table"+getTableNumber()+" order form");
//        stage.setResizable(false);
//        // disable X on the window
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                // consume event
//                event.consume();
//            }
//        });
//        stage.setScene(new Scene(vBoxOrders));
//        stage.show();
//    }
}
