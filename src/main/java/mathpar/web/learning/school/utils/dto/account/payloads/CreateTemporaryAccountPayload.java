package mathpar.web.learning.school.utils.dto.account.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTemporaryAccountPayload {
    private String email;
}
