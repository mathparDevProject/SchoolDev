package mathpar.web.learning.school.utils.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSchoolUsersResponse {
    private UserProfileResponse director;
    private List<UserProfileResponse> headTeachers = new ArrayList<>();
    private List<UserProfileResponse> teachers = new ArrayList<>();
    private List<UserProfileResponse> students = new ArrayList<>();

    public GetSchoolUsersResponse withDirector(UserProfileResponse director){
        this.director = director;
        return this;
    }

    public GetSchoolUsersResponse withHeadTeachers(List<UserProfileResponse> headTeachers){
        this.headTeachers = headTeachers;
        return this;
    }

    public GetSchoolUsersResponse withTeachers(List<UserProfileResponse> teachers){
        this.teachers = teachers;
        return this;
    }

    public GetSchoolUsersResponse withStudents(List<UserProfileResponse> students){
        this.students = students;
        return this;
    }
}