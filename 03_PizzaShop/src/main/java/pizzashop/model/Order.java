package pizzashop.model;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Integer id;
    private Integer tableNumber;
    private List<PairItemQuantity> orderedItems;
    private StatusType status;

    public Order(int tableNumber) {
        this.tableNumber = tableNumber;
        this.orderedItems = new ArrayList<>();
        this.status = StatusType.ORDERING;
    }

    public Order(int id, int tableNumber, List<PairItemQuantity> orderedItems, StatusType status) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.orderedItems = orderedItems;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public List<PairItemQuantity> getOrderItems() {
        return orderedItems;
    }

    public void setOrderItems(List<PairItemQuantity> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public void addOrderItem(PairItemQuantity item) {
        this.orderedItems.add(item);
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

}