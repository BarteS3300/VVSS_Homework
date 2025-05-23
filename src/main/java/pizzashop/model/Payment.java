package pizzashop.model;

public class Payment {
    private Integer id;
    private Integer orderId;
    private PaymentType type;
    private Double amount;
    public Payment(Integer id, Integer orderId, PaymentType type, Double amount) {
        this.id = id;
        this.orderId = orderId;
        this.type = type;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return id + "," + orderId + "," + type + "," + amount;
    }
}
