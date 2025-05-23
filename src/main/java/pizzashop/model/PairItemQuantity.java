package pizzashop.model;

public class PairItemQuantity {
    private Integer idItem;
    private Integer quantity;

    public PairItemQuantity(Integer idItem, Integer quantity) {
        this.idItem = idItem;
        this.quantity = quantity;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return idItem + "-" + quantity;
    }
}
