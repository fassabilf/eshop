package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
@Getter
public class Payment {
    private String id;
    private String method;
    private PaymentStatus status; // Use Enum instead of String
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = determineStatus();
    }

    private PaymentStatus determineStatus() {
        if (method == null || paymentData == null) {
            return PaymentStatus.REJECTED;
        }

        switch (method) {
            case "VOUCHER":
                return validateVoucher(paymentData.get("voucherCode")) ? PaymentStatus.SUCCESS : PaymentStatus.REJECTED;
            case "BANK_TRANSFER":
                return validateBankTransfer(paymentData) ? PaymentStatus.SUCCESS : PaymentStatus.REJECTED;
            default:
                return PaymentStatus.REJECTED;
        }
    }

    private boolean validateVoucher(String code) {
        return code != null && code.length() == 16 && code.startsWith("ESHOP") &&
                code.replaceAll("\\D", "").length() == 8;
    }

    private boolean validateBankTransfer(Map<String, String> paymentData) {
        return paymentData.getOrDefault("bankName", "").trim().length() > 0 &&
                paymentData.getOrDefault("referenceCode", "").trim().length() > 0;
    }
}
