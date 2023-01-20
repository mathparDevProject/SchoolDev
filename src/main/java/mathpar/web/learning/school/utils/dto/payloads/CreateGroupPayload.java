package mathpar.web.learning.school.utils.dto.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupPayload{
    @NotNull(message = "Name can't be empty")
    @Size(min = 3, max=30, message = "Name should be between 3 and 30 symbols")
    private String name;
    @Min(value = 1, message = "Teacher id must represent an existing user")
    private long teacherProfileId;
}