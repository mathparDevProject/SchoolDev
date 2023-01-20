package mathpar.web.learning.school.utils.dto.payloads;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static mathpar.web.learning.school.utils.Constants.EMAIL_REGEX;

@Data
public class CreateStudentPayload{
    @NotNull(message = "Name can't be empty")
    @Size(min = 3, max=30, message = "Name should be between 3 and 30 symbols")
    private String name;
    @Pattern(regexp = EMAIL_REGEX, message = "Email need to be valid")
    @NotNull(message = "Email can't be empty")
    private String email;
}