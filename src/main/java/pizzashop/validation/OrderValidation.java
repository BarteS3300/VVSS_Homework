package pizzashop.validation;

import pizzashop.model.Order;

public class OrderValidation implements Validator<Order>{
    @Override
    public void validate(Order entity) throws ValidationException {
        StringBuilder errors = new StringBuilder();
        if (entity.getTableNumber() >= 1 && entity.getTableNumber() <= 8) {
            errors.append("Table number must be between 1 and 8.\n");
        }
        if (entity.getStatus() == null) {
            errors.append("Status cannot be null.\n");
        }
        if (entity.getOrderItems() == null || entity.getOrderItems().isEmpty()) {
            errors.append("Order items cannot be null or empty.\n");
        }

    }
}
