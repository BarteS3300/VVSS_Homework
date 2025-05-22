package pizzashop.repository;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PaymentRepository {
    private final String filename;
    private final List<Payment> paymentList;

    public PaymentRepository(String filename){
        this.paymentList = new ArrayList<>();
        this.filename = filename;
        readPayments();
    }

    private void readPayments(){
        File file = new File(filename);
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while((line = br.readLine())!=null){
                Payment payment = getPayment(line);
                paymentList.add(payment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Payment getPayment(String line){
        Payment item=null;
        if (line==null || line.isEmpty()) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(line, ",");
        int id = Integer.parseInt(st.nextToken());
        int orderID = Integer.parseInt(st.nextToken());

        String type= st.nextToken();
        double amount = Double.parseDouble(st.nextToken());
        item = new Payment(id, orderID, PaymentType.valueOf(type), amount);
        return item;
    }

    public void add(Payment payment){
        if (payment.getOrderId() <= 0)
            throw new IllegalArgumentException("Order ID must be positive");
        if (payment.getAmount() <= 0)
            throw new IllegalArgumentException("Amount must be greater than 0");

        paymentList.add(payment);
        writeAll();
    }

    public List<Payment> getAll(){
        return paymentList;
    }

    public void writeAll(){
        File file = new File(filename);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Payment p:paymentList) {
                System.out.println(p.toString());
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer size() {
        return paymentList.size();
    }
}