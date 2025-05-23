package pizzashop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PizzaServiceTestStep2 {

    @Mock
    private Payment mockPayment;

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
        List<Payment> result=service.getPayments();
        assertEquals(0,result.size());
    }

    @Test
    void addPayment() {
        Payment payment = getPayment(3, PaymentType.CASH,33.3);

        service.addPayment(payment.getOrderId(), payment.getType(), payment.getAmount());

        List<Payment> result = service.getPayments();
        assertEquals(1, result.size());
        assertEquals(result.get(0).getOrderId(), 3);
        assertEquals(result.get(0).getOrderId(), 3);
    }

    Payment getPayment(int orderId, PaymentType type, double amount) {
        when(mockPayment.getOrderId()).thenReturn(orderId);
        when(mockPayment.getType()).thenReturn(type);
        when(mockPayment.getAmount()).thenReturn(amount);
        return mockPayment;
    }
}