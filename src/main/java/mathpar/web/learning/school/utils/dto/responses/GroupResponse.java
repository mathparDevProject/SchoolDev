package mathpar.web.learning.school.utils.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupResponse {
    private long id;
    private SchoolResponse school;
    private String name;
    private UserProfileResponse teacher;
    private List<UserProfileResponse> students;

    public GroupResponse(long id, String name){
        this.id = id;
        this.name = name;
        this.students = Collections.emptyList();

    }

    public GroupResponse withSchool(SchoolResponse schoolResponse){
        this.school = schoolResponse;
        return this;
    }

    public GroupResponse withTeacher(UserProfileResponse teacher){
        this.teacher = teacher;
        return this;
    }

    public GroupResponse withStudents(List<UserProfileResponse> students){
        this.students = students;
        return this;
    }
}
