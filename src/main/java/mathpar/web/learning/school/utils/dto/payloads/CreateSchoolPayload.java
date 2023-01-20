package mathpar.web.learning.school.utils.dto.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchoolPayload{
    private String schoolName;
    private String schoolAddress;
}