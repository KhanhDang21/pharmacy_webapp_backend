package pharmacy_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.model.Bill;
import pharmacy_webapp.service.BillService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private BillService billService;

    @GetMapping("/vnpay-return")
    public ResponseEntity<ApiResponse<Map<String, Object>>> vnpayReturn(@RequestParam Map<String, String> params) {
        try {
            Bill bill = billService.handleVNPayCallback(params);

            Map<String, Object> result = new HashMap<>();
            result.put("billId", bill.getId());
            result.put("paymentStatus", bill.getPaymentStatus());
            result.put("paymentStatusText", bill.getPaymentStatus() == 1 ? "Payment successfully" : "Payment fail");
            result.put("orderStatus", bill.getOderStatus());
            result.put("totalAmount", bill.getTotalAmount());
            result.put("transactionNo", params.get("vnp_TransactionNo"));
            result.put("bankCode", params.get("vnp_BankCode"));
            result.put("responseCode", params.get("vnp_ResponseCode"));

            return ResponseEntity.ok(
                    ApiResponse.success("Payment process successfully", result)
            );
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("params", params);

            return ResponseEntity.badRequest().body(
                    ApiResponse.error("Error payment process: " + e.getMessage())
            );
        }
    }

    @GetMapping("/test-config")
    public ResponseEntity<ApiResponse<Map<String, String>>> testConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("vnpay_status", "Configured");
        config.put("message", "VNPay payment gateway is ready");

        return ResponseEntity.ok(
                ApiResponse.success("Vnpay OK", config)
        );
    }
}