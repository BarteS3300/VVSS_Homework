package pizzashop.service;

import pizzashop.model.*;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.OrderRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.model.StatusType;
import pizzashop.utility.Observable;
import pizzashop.utility.Observer;
import pizzashop.validation.MenuItemValidation;
import pizzashop.validation.OrderValidation;
import pizzashop.validation.PaymentValidation;
import pizzashop.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PizzaService implements Observable<Observer> {

    private Validator<MenuItem> menuItemValidator;
    private Validator<Order> orderValidator;
    private Validator<Payment> paymentValidator;

    private MenuRepository menuRepo;
    private OrderRepository orderRepo;
    private PaymentRepository payRepo;
    private List<Observer> observers = new ArrayList<>();

    private static PizzaService instance = null;


    public static PizzaService getInstance() {
        if (instance == null) {
            instance = new PizzaService();
        }
        return instance;
    }

    private PizzaService(){
        menuItemValidator = new MenuItemValidation();
        orderValidator = new OrderValidation();
        paymentValidator = new PaymentValidation();
    }

    public void setMenuRepository(MenuRepository menuRepo) {
        this.menuRepo = menuRepo;
    }

    public void setOrderRepository(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    public void setPaymentRepository(PaymentRepository payRepo) {
        this.payRepo = payRepo;
    }

    public MenuItem getMenuItem(int id){
        List<MenuItem> menu = menuRepo.getMenu();
        for (MenuItem item : menu) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Payment> getPayments(){return payRepo.getAll(); }

    public List<Order> getOrdersPreparingOrCooking(){
        return orderRepo.getAll().stream()
                .filter(order -> order.getStatus() == StatusType.PREPARING || order.getStatus() == StatusType.COOKING)
                .collect(Collectors.toList());
    }

    public List<PairItemQuantity> createNewPairs(){
        List<PairItemQuantity> pairs = new ArrayList<>();
        List<MenuItem> menu = menuRepo.getMenu();
        for (MenuItem item : menu) {
            pairs.add(new PairItemQuantity(item.getId(), 0));
        }
        return pairs;
    }

    public void addPayment(int orderId, PaymentType type, double amount){
        Payment payment= new Payment(payRepo.size(), orderId, type, amount);
        payRepo.add(payment);
    }

    public double getTotalAmount(PaymentType type){
        double total = 0.0f;
        List<Payment> list = getPayments();
        if (list.isEmpty()) {
            return total; }
        if (type == PaymentType.CARD) {
            for (Payment payment : list) {
                if (payment.getType().equals(PaymentType.CARD)) {
                    total += payment.getAmount();
                }
            }
        } else {
            for (Payment payment : list) {
                if (payment.getType().equals(PaymentType.CASH)) {
                    total += payment.getAmount();
                }
            }
        }
        return total;
    }

    public void addOrder(Order order) {
        order.setId(orderRepo.size());
        orderRepo.add(order);
        notifyObservers();
    }

    public void cookOrder(Order order) {
        order.setStatus(StatusType.COOKING);
        orderRepo.update(order);
        notifyObservers();
    }

    public void preparedOrder(Order order) {
        order.setStatus(StatusType.PREPARED);
        orderRepo.update(order);
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    private void notifyKitchenObs(){
        if(observers.isEmpty()) return;
        observers.forEach(Observer::update);
    }
}