package mathpar.web.learning.school.utils.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationResponse {
    private final SchoolResponse school;
    private final RegistrationTokenResponse token;
}
