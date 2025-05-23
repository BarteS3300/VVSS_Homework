package pizzashop.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceTestGetTotalAmount {

    private final String testFile = "data/test_payments.txt";
    private PaymentRepository repository = new PaymentRepository(testFile);;

    private final PizzaService service = PizzaService.getInstance();

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
//        repository= new PaymentRepository("data_test/payments.txt");
        service.setPaymentRepository(repository);
    }


    @AfterEach
    void teardown() throws IOException {
        Files.deleteIfExists(new File(testFile).toPath());
        new File(testFile).createNewFile();
        System.out.println("Test file deleted: " + testFile);
    }

    @AfterAll
    static void clearTestFile() throws IOException {
        FileWriter writer = new FileWriter(new File("data/test_payments.txt"));
        writer.write(""); // Clear the file content
        writer.close();
    }

    @Test
    void getTotalAmountTest1() {
        service.addPayment(5, PaymentType.CARD, 13.00);
        double result = service.getTotalAmount(PaymentType.CARD);
        assertEquals(13.0f, result);
    }

    @Test
    void getTotalAmountTest2() {
        double result = service.getTotalAmount(PaymentType.CASH);
        assertEquals(0.0f, result);
    }

    @Test
    void getTotalAmountTest3() {
        double result = service.getTotalAmount(PaymentType.CARD);
        assertEquals(0.0f, result);
    }

    @Test
    void getTotalAmountTest4() {
        service.addPayment(1, PaymentType.CARD, 12.0f);
        service.addPayment(2, PaymentType.CASH, 13.0f);
        service.addPayment(3, PaymentType.CARD, 12.0f);
        double result = service.getTotalAmount(PaymentType.CASH);
        assertEquals(13.0f, result);
    }

    @Test
    void getTotalAmountTest5() {
        service.addPayment(5, PaymentType.CASH, 13.0f);
        double result = service.getTotalAmount(PaymentType.CARD);
        assertEquals(0.0f, result);
    }

}