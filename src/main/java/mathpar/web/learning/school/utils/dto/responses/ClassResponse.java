package mathpar.web.learning.school.utils.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassResponse {
    private long id;
    private String name;
    private List<UserProfileResponse> students;

    public ClassResponse(long id, String name) {
        this.id = id;
        this.name = name;
        this.students = Collections.emptyList();
    }

    public ClassResponse withStudents(List<UserProfileResponse> students){
        this.students = students;
        return this;
    }
}
