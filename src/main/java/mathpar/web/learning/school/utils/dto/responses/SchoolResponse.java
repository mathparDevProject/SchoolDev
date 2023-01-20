package mathpar.web.learning.school.utils.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchoolResponse{
    private long id;
    private String schoolName;
    private String schoolAddress;
    private UserProfileResponse director;

    public SchoolResponse(long schoolId, String schoolName, String schoolAddress){
        this.id = schoolId;
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
    }

    public SchoolResponse withDirector(UserProfileResponse director){
        this.director = director;
        return this;
    }
}