package pharmacy_webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String birthDate;
}
