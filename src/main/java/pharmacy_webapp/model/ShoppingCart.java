package pharmacy_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    @Id
    private String id;

    @DBRef
    private User user;

    private ArrayList<CartItem> items = new ArrayList<>();
}
