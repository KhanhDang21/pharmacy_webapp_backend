package pharmacy_webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddressDto {
    private String recipientName;
    private String numPhone;
    private String addressLine;
    private String district;
    private String city;
}
