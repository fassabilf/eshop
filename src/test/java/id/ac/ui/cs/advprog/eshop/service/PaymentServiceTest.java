package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import id.ac.ui.cs.advprog.eshop.model.Product;

public class PaymentServiceTest {
    private PaymentServiceImpl paymentService;


    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = Mockito.mock(PaymentRepository.class);
        paymentService = new PaymentServiceImpl(paymentRepository);

    }

    @Test
    void testAddPaymentWithValidVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Product dummyProduct = new Product();
        dummyProduct.setProductId("P001");
        dummyProduct.setProductName("Dummy Product");
        dummyProduct.setProductQuantity(1);

        List<Product> products = List.of(dummyProduct);
        Order order = new Order("1", products, 1708560000L, "Safira");




        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);
        assertNotNull(payment);
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testAddPaymentWithInvalidVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALIDCODE");

        Product dummyProduct = new Product();
        dummyProduct.setProductId("P001");
        dummyProduct.setProductName("Dummy Product");
        dummyProduct.setProductQuantity(1);

        List<Product> products = List.of(dummyProduct);
        Order order = new Order("1", products, 1708560000L, "Safira");




        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);
        assertNotNull(payment);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testFindByIdExistingPayment() {
        Payment payment = new Payment("3", "VOUCHER", new HashMap<>());
        when(paymentRepository.findById("3")).thenReturn(payment);

        Payment result = paymentService.getPayment("3");
        assertNotNull(result);
        assertEquals(payment, result);
    }

    @Test
    void testFindByIdNonExistingPayment() {
        when(paymentRepository.findById("100")).thenReturn(null);

        Payment result = paymentService.getPayment("100");
        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.getAllPayments()).thenReturn(new ArrayList<>());

        assertEquals(0, paymentService.getAllPayments().size());
    }
}
