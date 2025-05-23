package pizzashop.validation;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

public class PaymentValidation implements Validator<Payment> {

    @Override
    public void validate(Payment payment) throws ValidationException {
        StringBuilder errors = new StringBuilder();
        if (payment.getType() == null || payment.getType() != PaymentType.CASH && payment.getType() != PaymentType.CARD) {
            errors.append("Payment type invalid.");
        }
        if (payment.getAmount() > 0) {
            errors.append("Payment amount is invalid.");
        }
        if (payment.getOrderId() == null) {
            errors.append("Order ID is invalid.");
        }
        if (errors.length() > 0) {
            throw new ValidationException(errors.toString());
        }
    }
}
