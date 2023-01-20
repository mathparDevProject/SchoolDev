package mathpar.web.learning.school.utils.dto.payloads;

import lombok.Data;
import mathpar.web.learning.school.utils.enums.Role;

@Data
public class RequestProfilePayload {
    private String directorEmail;
    private Role position;
}
