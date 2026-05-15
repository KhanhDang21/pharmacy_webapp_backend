package pharmacy_webapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "section")
public class Section {
    @Id
    private String id;
    private String title;
    private String type;
    private Boolean enabled;
    private Integer order;
}
