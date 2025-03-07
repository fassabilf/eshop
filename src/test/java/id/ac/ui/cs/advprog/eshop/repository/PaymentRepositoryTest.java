package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {
    private PaymentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PaymentRepository();
    }

    @Test
    void testSaveAndRetrievePayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("1", "VOUCHER", paymentData);
        repository.save(payment);

        Payment result = repository.findById("1");
        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
    }

    @Test
    void testFindByIdWhenNotExists() {
        Payment result = repository.findById("100");
        assertNull(result);
    }


    @Test
    void testGetAllPayments() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("3", "VOUCHER", paymentData1);
        repository.save(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "BCA");
        paymentData2.put("referenceCode", "1234567890");
        Payment payment2 = new Payment("4", "BANK_TRANSFER", paymentData2);
        repository.save(payment2);

        assertEquals(2, repository.getAllPayments().size());
    }
}
