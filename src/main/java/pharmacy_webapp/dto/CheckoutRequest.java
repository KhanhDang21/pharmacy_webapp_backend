package pharmacy_webapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {
    @NotBlank(message = "Địa chỉ thanh toán không được để trống")
    private String shippingAddressId;

    @NotNull(message = "Phương thức thanh toán không được để trống")
    private Integer paymentMethod;

    private String note;
}
