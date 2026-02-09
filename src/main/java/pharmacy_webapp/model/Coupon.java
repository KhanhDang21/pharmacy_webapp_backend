package pharmacy_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    private String id;

    private String name;
    private String description;
    private double discountValue;
    private int quantity;
    private String endTime;
//    private int typeCoupon;
}
