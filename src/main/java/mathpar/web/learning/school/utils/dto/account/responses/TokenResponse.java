package mathpar.web.learning.school.utils.dto.account.responses;

import lombok.Data;

import java.util.Date;

@Data
public class TokenResponse {
    private String token;
    private Date expirationDate;
}
