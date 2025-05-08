package pizzashop.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pizzashop.model.*;
import pizzashop.service.PaymentAlert;
import pizzashop.service.PizzaService;
import pizzashop.utility.Observer;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrdersGUIController implements Observer {
    @FXML
    private ComboBox<Integer> orderQuantity;
    @FXML
    private TableView<PairItemQuantity> orderTable;
    @FXML
    private TableColumn<PairItemQuantity, Integer> tableQuantity;
    @FXML
    protected TableColumn<PairItemQuantity, String> tableMenuItem;
    @FXML
    private TableColumn<PairItemQuantity, Double> tablePrice;
    @FXML
    private Label pizzaTypeLabel;
    @FXML
    private Button addToOrder;
    @FXML
    private Label orderStatus;
    @FXML
    private Button placeOrder;
    @FXML
    private Button orderServed;
    @FXML
    private Button payOrder;
    @FXML
    private Button newOrder;

    private Order tableOrder;

    private List<Double> orderPaymentList = FXCollections.observableArrayList();
    public static double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private PizzaService service;
    private int tableNumber;

    private ObservableList<PairItemQuantity> menuQuantity = FXCollections.observableArrayList();
    private Calendar now = Calendar.getInstance();
    private static double totalAmount;

    public OrdersGUIController(){ }

    public void setService(PizzaService service, int tableNumber){
        this.service = service;
        //this.service.addObserver(this);
        this.tableNumber = tableNumber;
        initData();
    }

    private void newOrder(){
        this.tableOrder = new Order(tableNumber);
        addToOrder.disableProperty().bind(Bindings.isEmpty(orderTable.getSelectionModel().getSelectedItems()));
        menuQuantity.setAll(service.createNewPairs());
        orderTable.setItems(menuQuantity);
        placeOrder.setDisable(true);
        orderServed.setDisable(true);
        payOrder.setDisable(true);
    }

    private void initData(){
        newOrder();

        //Controller for Place Order Button
        placeOrder.setOnAction(event ->{
            menuQuantity.stream()
                    .filter(pair -> pair.getQuantity() > 0)
                    .forEach(pair -> {
                        tableOrder.addOrderItem(pair);
                    });
            System.out.println("--------------------------");
            tableOrder.setStatus(StatusType.PREPARING);
            service.addOrder(tableOrder);
            System.out.println(service.getOrdersPreparingOrCooking());
            orderStatus.setText("Order placed at: " +  now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE));
            orderServed.setDisable(false);
            addToOrder.disableProperty().unbind();
            addToOrder.setDisable(true);
            placeOrder.setDisable(true);
        });

        //Controller for Order Served Button
        orderServed.setOnAction(event -> {orderStatus.setText("Served at: " + now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE));
        payOrder.setDisable(false);
        orderServed.setDisable(true);
        });

        //Controller for Pay Order Button
        payOrder.setOnAction(event -> {
            orderPaymentList = tableOrder.getOrderItems().stream()
                    .map(pair -> service.getMenuItem(pair.getIdItem()).getPrice() * pair.getQuantity())
                    .collect(Collectors.toList());
            setTotalAmount(orderPaymentList.stream().mapToDouble(payment -> payment.doubleValue()).sum());
            orderStatus.setText("Total amount: " + getTotalAmount());
            System.out.println("--------------------------");
            System.out.println("Table: " + tableNumber);
            System.out.println("Total: " + getTotalAmount());
            System.out.println("--------------------------");
            PaymentAlert pay = new PaymentAlert(service);
            pay.showPaymentAlert(tableNumber, this.getTotalAmount());
            newOrder();
        });
    }

    public void initialize(){

        //populate table view with menuData from OrderGUI
//        table.setEditable(true);
        tableMenuItem.setCellValueFactory(pair -> new SimpleStringProperty(service.getMenuItem(pair.getValue().getIdItem()).getName()));
        tablePrice.setCellValueFactory(pair -> new SimpleDoubleProperty(service.getMenuItem(pair.getValue().getIdItem()).getPrice()).asObject());
        tableQuantity.setCellValueFactory(new PropertyValueFactory<PairItemQuantity, Integer>("quantity"));

        //Populate Combo box for Quantity
        ObservableList<Integer> quantityValues =  FXCollections.observableArrayList(0, 1, 2,3,4,5);
        orderQuantity.getItems().addAll(quantityValues);
        orderQuantity.setPromptText("Quantity");

        //bind pizzaTypeLabel and quantity combo box with the selection on the table view
        orderTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PairItemQuantity>() {
        @Override
        public void changed(ObservableValue<? extends PairItemQuantity> observable, PairItemQuantity oldValue, PairItemQuantity newValue) {
            return;
        }
        });
//
//
        //Controller for Add to order Button
        addToOrder.setOnAction(event -> {
            orderTable.getSelectionModel().selectedItemProperty().get().setQuantity(orderQuantity.getValue());
            orderTable.refresh();
            placeOrder.setDisable(false);
        });

        //Controller for Exit table Button
        newOrder.setOnAction(event -> {
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Exit table?",ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.get() == ButtonType.YES){
                Stage stage = (Stage) newOrder.getScene().getWindow();
                stage.close();
                }
        });
    }

    @Override
    public void update() {

    }
}