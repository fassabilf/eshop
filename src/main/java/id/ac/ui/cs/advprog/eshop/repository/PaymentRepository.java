package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.*;

public class PaymentRepository {
    private final Map<String, Payment> payments = new HashMap<>();

    public void save(Payment payment) {
        if (payments.containsKey(payment.getId())) {
            // Menggunakan data baru untuk validasi ulang
            Payment updatedPayment = new Payment(
                    payment.getId(),
                    payment.getMethod(),
                    payment.getPaymentData()
            );
            payments.put(payment.getId(), updatedPayment);
        } else {
            payments.put(payment.getId(), payment);
        }
    }




    public Payment findById(String id) {
        return payments.get(id);
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(payments.values());
    }
}
