package pizzashop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentRepositoryTestStep1 {

    @Mock
    private Payment mockPayment;

    PaymentRepository repository;

    @BeforeEach
    void setUp() {
        repository=new PaymentRepository("data/test_payments.txt");
    }

    @AfterEach
    void tearDown() {
        repository.getAll().clear();
        repository.writeAll();
    }

    @Test
    void getAll() {
        assertEquals(0,repository.getAll().size());
    }

    @Test
    void add() {
        Payment payment=getPayment(1,  3, PaymentType.CASH,33.3);

        repository.add(payment);
        List<Payment> paymentList=repository.getAll();
        assertEquals(1,paymentList.size());
        assertEquals(payment.getType(), paymentList.get(0).getType());
        assertEquals(payment.getId(), paymentList.get(0).getId());
    }

    Payment getPayment(int id, int orderId, PaymentType type, double amount) {
        when(mockPayment.getId()).thenReturn(id);
        when(mockPayment.getOrderId()).thenReturn(orderId);
        when(mockPayment.getType()).thenReturn(type);
        when(mockPayment.getAmount()).thenReturn(amount);
        return mockPayment;
    }
}