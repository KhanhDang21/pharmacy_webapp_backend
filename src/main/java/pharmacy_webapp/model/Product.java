package pharmacy_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;

    @DBRef
    private Manufacturer manufacturer;

    @DBRef
    private Categories categories;

    private String name;
    private String description;
    private List<String> urlImages;
    private int quantity;
    private double price;
    private int purchaseCount;
}
