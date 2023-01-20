package mathpar.web.learning.school.utils.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RegistrationTokenResponse {
    private String token;
    private Date expirationDate;
}
