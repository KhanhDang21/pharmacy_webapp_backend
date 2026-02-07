package pharmacy_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "blacklist_token")
public class BlackListedToken {
    @Id
    private String id;
    private String token;

    @Indexed(expireAfter = "PT0S")
    private Date expiryTime;
}
