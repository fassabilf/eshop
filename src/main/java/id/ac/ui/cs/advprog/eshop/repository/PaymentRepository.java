package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentRepository {
    private final Map<String, Payment> payments = new ConcurrentHashMap<>();

    public void save(Payment payment) {
        payments.put(payment.getId(), new Payment(
                payment.getId(),
                payment.getMethod(),
                new HashMap<>(payment.getPaymentData()) // Memastikan data baru diproses ulang
        ));
    }



    public Payment findById(String id) {
        return payments.get(id);
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(payments.values());
    }
}
