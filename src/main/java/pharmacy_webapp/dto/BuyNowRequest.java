package pharmacy_webapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyNowRequest {
    @NotEmpty(message = "Danh sách sản phẩm không được để trống")
    private HashMap<String, Integer> products;

    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    private String shippingAddressId;

    @NotNull(message = "Phương thức thanh toán không được để trống")
    private Integer paymentMethod;

    private String note;
}
