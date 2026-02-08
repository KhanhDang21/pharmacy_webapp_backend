package pharmacy_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shopping_cart")
public class ShoppingCart {
    @Id
    private String id;

    @DBRef
    private User user;

    private HashMap<String, Integer> items = new HashMap<>();
}
