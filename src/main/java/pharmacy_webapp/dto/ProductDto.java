package pharmacy_webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String manufacturerId;
    private String categoriesId;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int purchaseCount;
}
