package mathpar.web.learning.school.utils.dto.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkGroupActionPayload {
    private long groupId;
    private List<Long> students;
}