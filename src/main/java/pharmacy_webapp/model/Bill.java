package pharmacy_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private List<Product> products;

    @DBRef
    private ShippingAddress shippingAddress;

    @DBRef
    private HashSet<Coupon> coupons;

    private double totalAmount;
    private String paymentMethod;
    private String paymentStatus;
    private String oderStatus;
}
