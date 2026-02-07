package pharmacy_webapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String birthDate;
    private String gender;
    private String email;
    private String phoneNumber;
    private Boolean isActive = true;

    private HashSet<String> roles = new HashSet<>();
}
