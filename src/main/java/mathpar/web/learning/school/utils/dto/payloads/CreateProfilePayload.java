package mathpar.web.learning.school.utils.dto.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;
import mathpar.web.learning.school.utils.enums.Role;

@Data
@NoArgsConstructor
public class CreateProfilePayload {
    private String email;
    private Role role;
}
