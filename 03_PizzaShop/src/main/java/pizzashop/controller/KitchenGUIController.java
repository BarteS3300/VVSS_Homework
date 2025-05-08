package pizzashop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import pizzashop.model.Order;
import pizzashop.model.PairItemQuantity;
import pizzashop.model.StatusType;
import pizzashop.service.PizzaService;
import pizzashop.utility.Observer;

import java.util.Calendar;

public class KitchenGUIController implements Observer {
    @FXML
    private ListView<Order> kitchenOrdersList;
    @FXML
    public Button cook;
    @FXML
    public Button ready;

    public static  ObservableList<Order> ordersToPrepare = FXCollections.observableArrayList();
    private Object selectedOrder;
    private Calendar now = Calendar.getInstance();
    private String extractedTableNumberString = new String();
    private int extractedTableNumberInteger;
    private PizzaService service;
    //thread for adding data to kitchenOrderList

    public void setService(PizzaService service) {
        this.service = service;
        System.out.println("Service set in KitchenGUIController");
        service.addObserver(this);
        update();
        cook.disableProperty().bind(kitchenOrdersList.getSelectionModel().selectedItemProperty().isNull());
    }

    public void initialize() {

        //Setting the ListView to display the Order object
        kitchenOrdersList.setCellFactory(new Callback<ListView<Order>, ListCell<Order>>() {
            @Override
            public ListCell<Order> call(ListView<Order> param) {
                return new ListCell<Order>() {
                    @Override
                    protected void updateItem(Order item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            if (item.getStatus() == StatusType.COOKING)
                                sb.append(" Cooking started at: ").append(now.get(Calendar.HOUR)).append(":").append(now.get(Calendar.MINUTE)).append(": ");
                            for (PairItemQuantity pair : item.getOrderItems()) {
                                sb.append(service.getMenuItem(pair.getIdItem()).getName()).append(" x").append(pair.getQuantity()).append(", ");
                            }
                            sb.delete(sb.length() - 2, sb.length());
                            sb.append(";");
                            setText(sb.toString());

                        }
                    }
                };
            }
        });

        //Controller for Cook Button
        cook.setOnAction(event -> {
            Order selectedOrder = kitchenOrdersList.getSelectionModel().getSelectedItem();
            service.cookOrder(selectedOrder);
            System.out.println("Cooking");
            update();
        });
        //Controller for Ready Button
        ready.setOnAction(event -> {
            Order selectedOrder = kitchenOrdersList.getSelectionModel().getSelectedItem();
            service.preparedOrder(selectedOrder);
            kitchenOrdersList.getItems().remove(selectedOrder);
            System.out.println("--------------------------");
            System.out.println("Table " + selectedOrder.getTableNumber() +" ready at: " + now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE));
            System.out.println("--------------------------");
            System.out.println("Ready");
        });
    }

    @Override
    public void update() {
        System.out.println("Update method called in KitchenGUIController");
        ordersToPrepare.setAll(service.getOrdersPreparingOrCooking());
        kitchenOrdersList.setItems(ordersToPrepare);
        kitchenOrdersList.refresh();
    }
}