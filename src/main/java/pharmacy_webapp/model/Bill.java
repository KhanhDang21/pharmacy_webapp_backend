package pharmacy_webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    private String id;

    @DBRef(lazy = true)
    @JsonIgnore
    private User user;

    @JsonProperty("userId")
    public String getUserId() {
        return user != null ? user.getId() : null;
    }

    @DBRef
    private ShippingAddress shippingAddress;

    private HashMap<String, Integer> products = new HashMap<>();

    private double totalAmount;
    private Integer paymentMethod;
    private Integer paymentStatus;
    private Integer oderStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime paidAt;

    private String note;
}
