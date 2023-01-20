package mathpar.web.learning.school.utils.dto.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignTeacherToGroupPayload{
    private long teacherId;
    private long groupId;
}
