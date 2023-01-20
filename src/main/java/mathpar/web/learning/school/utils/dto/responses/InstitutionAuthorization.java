package mathpar.web.learning.school.utils.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mathpar.web.learning.school.utils.enums.Role;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionAuthorization {
    private long entityId;
    private List<Role> availableRoles;
}