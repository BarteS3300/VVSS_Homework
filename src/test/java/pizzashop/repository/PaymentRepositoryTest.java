package pizzashop.repository;

import org.junit.jupiter.api.*;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("payment-tests")
@DisplayName("PaymentRepository ECP & BVA Tests")
public class PaymentRepositoryTest {

    private PaymentRepository repository;
    private final String testFile = "data/test_payments.txt";

    @BeforeEach
    void setup() throws IOException {
        // Create the data directory if it doesn't exist
        Path dataDir = Paths.get("data");
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        // Clean up existing test file if it exists
        Files.deleteIfExists(new File(testFile).toPath());
        new File(testFile).createNewFile();
        // Create the repository with the test file
        repository = new PaymentRepository(testFile);
    }

    @AfterEach
    void teardown() throws IOException {
        Files.deleteIfExists(new File(testFile).toPath());
        new File(testFile).createNewFile();
        System.out.println("Test file deleted: " + testFile);
    }

    @Test
    @DisplayName("Add valid payment (ECP Valid)")
    void addValidPayment() {
        // Arrange
        Payment p = new Payment(1, 10, PaymentType.CASH, 50.0);

        // Act
        repository.add(p);

        // Assert
        List<Payment> payments = repository.getAll();
        assertEquals(1, payments.size());
        assertEquals(p.getAmount(), payments.get(0).getAmount());
    }

    @Test
    @DisplayName("Invalid orderId (ECP Invalid)")
    void invalidOrderId() {
        Payment p = new Payment(2, -1, PaymentType.CARD, 50.0);
        assertThrows(IllegalArgumentException.class, () -> repository.add(p));
    }

    @Test
    @DisplayName("Invalid amount (ECP Invalid)")
    void invalidAmount() {
        Payment p = new Payment(3, 11, PaymentType.CARD, -5.0);
        assertThrows(IllegalArgumentException.class, () -> repository.add(p));
    }

    @Test
    @DisplayName("Invalid orderId and amount (ECP Invalid)")
    void invalidOrderIdAndAmount() {
        Payment p = new Payment(4, -1, PaymentType.CASH, -5.0);
        assertThrows(IllegalArgumentException.class, () -> repository.add(p));
    }

    @Test
    @DisplayName("Minimum valid orderId and amount")
    void boundaryMinValid() {
        Payment p = new Payment(5, 1, PaymentType.CARD, 0.01);
        repository.add(p);
        assertEquals(1, repository.size());
    }

    @Test
    @DisplayName("Large valid amount")
    void boundaryMaxValid() {
        Payment p = new Payment(6, 2, PaymentType.CARD, 1000000.0);
        repository.add(p);
        assertEquals(1, repository.size());
    }

    @Test
    @DisplayName("Zero orderId (Invalid BVA)")
    void boundaryInvalidOrderId() {
        Payment p = new Payment(7, 0, PaymentType.CARD, 10.0);
        assertThrows(IllegalArgumentException.class, () -> repository.add(p));
    }

    @Test
    @DisplayName("Zero amount (Invalid BVA)")
    void boundaryInvalidAmount() {
        Payment p = new Payment(8, 5, PaymentType.CASH, 0.0);
        assertThrows(IllegalArgumentException.class, () -> repository.add(p));
    }
}