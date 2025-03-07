package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    void testCreatePaymentVoucherSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("1", "VOUCHER", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherInvalidLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234");

        Payment payment = new Payment("2", "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherMissingPrefix() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALID1234ABC5678");

        Payment payment = new Payment("3", "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherMissingNumbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGHJKLMN");

        Payment payment = new Payment("4", "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "1234567890");

        Payment payment = new Payment("5", "BANK_TRANSFER", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferMissingReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("6", "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferMissingBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "1234567890");

        Payment payment = new Payment("7", "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }
}
