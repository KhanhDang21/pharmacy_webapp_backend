package pharmacy_webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentUrlResponse {
    private String billId;
    private String paymentUrl;
    private double totalAmount;
}