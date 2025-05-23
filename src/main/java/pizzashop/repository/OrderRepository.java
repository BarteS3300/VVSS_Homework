package pizzashop.repository;

import pizzashop.model.Order;
import pizzashop.model.PairItemQuantity;
import pizzashop.model.StatusType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class OrderRepository {
    private static String filename = "data/orders.txt";
    private List<Order> listOrders = new ArrayList<>();

    public OrderRepository() {
        readOrders();
    }

    private void readOrders() {
        File file = new File(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                Order order = readOrder(line);
                listOrders.add(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Order readOrder(String line) {
        Order order = null;
        if (line == null || line.equals("")) return null;
        StringTokenizer st = new StringTokenizer(line, ",");
        int id = Integer.parseInt(st.nextToken());
        int tableNumber = Integer.parseInt(st.nextToken());
        StatusType status = StatusType.valueOf(st.nextToken());
        List<PairItemQuantity> orderdItems = new ArrayList<>();
        while (st.hasMoreTokens()) {
            StringTokenizer st2 = new StringTokenizer(st.nextToken(), "-");
            int idItem = Integer.parseInt(st2.nextToken());
            int quantity = Integer.parseInt(st2.nextToken());
            PairItemQuantity pair = new PairItemQuantity(idItem, quantity);
            orderdItems.add(pair);
        }

        order = new Order(id, tableNumber, orderdItems, status);
        return order;
    }

    public List<Order> getAll() {
        return listOrders;
    }

    public void add(Order order) {
        listOrders.add(order);
        writeAll();
    }

    public void update(Order order) {
        for (int i = 0; i < listOrders.size(); i++) {
            if (listOrders.get(i).getId() == order.getId()) {
                listOrders.set(i, order);
                writeAll();
            }
        }
    }

    private void writeAll() {
        File file = new File(filename);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for (Order order : listOrders) {
                StringBuilder orderString = new StringBuilder();
                orderString.append(order.getId()).append(",").append(order.getTableNumber()).append(",").append(order.getStatus());
                for(PairItemQuantity pair : order.getOrderItems()){
                    orderString.append(",").append(pair.getIdItem()).append("-").append(pair.getQuantity());
                }
                bw.write(orderString.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer size() {
        return listOrders.size();
    }
}
