package pizzashop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTestStep3 {

    PaymentRepository payRepo;
    PizzaService service;

    @BeforeEach
    void setUp() {
        payRepo = new PaymentRepository("data/test_payments.txt");
        service= PizzaService.getInstance();
        service.setPaymentRepository(payRepo);
    }

    @AfterEach
    void tearDown() {
        payRepo.getAll().clear();
        payRepo.writeAll();
    }

    @Test
    void getPayments() {
        Payment payment = new Payment(3, 3, PaymentType.CASH,33.3);

        service.addPayment(payment.getOrderId(), payment.getType(), payment.getAmount());

        List<Payment> result = service.getPayments();
        assertEquals(1, result.size());
        assertEquals(result.get(0).getOrderId(), 3);
        assertEquals(result.get(0).getType(), PaymentType.CASH);
    }

    @Test
    void addPayment() {
        Payment payment1 = new Payment(0, 1, PaymentType.CASH,33.3);
        Payment payment2 = new Payment(0, 2, PaymentType.CARD,33.3);
        Payment payment3 = new Payment(0, 3, PaymentType.CASH,33.3);

        service.addPayment(payment1.getOrderId(), payment1.getType(), payment1.getAmount());
        service.addPayment(payment2.getOrderId(), payment2.getType(), payment2.getAmount());
        service.addPayment(payment3.getOrderId(), payment3.getType(), payment3.getAmount());

        List<Payment> result = service.getPayments();
        assertEquals(3, result.size());

        assertEquals(result.get(0).getOrderId(), 1);
        assertEquals(result.get(1).getOrderId(), 2);
        assertEquals(result.get(2).getOrderId(), 3);
    }
}