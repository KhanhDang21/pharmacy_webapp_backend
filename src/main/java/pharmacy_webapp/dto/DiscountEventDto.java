package pharmacy_webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountEventDto {
    private String name;
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;

    private Integer discountPercent;

    private HashSet<String> productIds;
}
