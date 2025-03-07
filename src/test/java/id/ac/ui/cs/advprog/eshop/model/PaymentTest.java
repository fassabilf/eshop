package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
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
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherInvalidLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234");

        Payment payment = new Payment("2", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherMissingPrefix() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALID1234ABC5678");

        Payment payment = new Payment("3", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherMissingNumbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGHJKLMN");

        Payment payment = new Payment("4", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherEmptyOrNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "");

        Payment payment = new Payment("5", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());

        paymentData.put("voucherCode", null);
        payment = new Payment("6", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "1234567890");

        Payment payment = new Payment("7", "BANK_TRANSFER", paymentData);
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferMissingReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("8", "BANK_TRANSFER", paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());

        paymentData.put("referenceCode", null);
        payment = new Payment("9", "BANK_TRANSFER", paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferMissingBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "1234567890");

        Payment payment = new Payment("10", "BANK_TRANSFER", paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());

        paymentData.put("bankName", null);
        payment = new Payment("11", "BANK_TRANSFER", paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentWithUnknownMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("randomKey", "randomValue");

        Payment payment = new Payment("12", "UNKNOWN_METHOD", paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentWithNullMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("13", null, paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentWithNullPaymentData() {
        Payment payment = new Payment("14", "VOUCHER", null);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentWithEmptyPaymentData() {
        Map<String, String> paymentData = new HashMap<>();

        Payment payment = new Payment("15", "BANK_TRANSFER", paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }
}
