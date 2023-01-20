package mathpar.web.learning.school.utils.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import mathpar.web.learning.school.entities.UserProfile;
import mathpar.web.learning.school.utils.enums.Role;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileResponse {
    private long profileId;
    private long accountId;
    private String profileName;
    private Role role;
    private SchoolResponse school;
    private Date creationDate;

    public UserProfileResponse(long profileId, long accountId, String profileName, Role role, Date creationDate) {
        this.profileId = profileId;
        this.accountId = accountId;
        this.profileName = profileName;
        this.role = role;
        this.creationDate = creationDate;
    }

    public UserProfileResponse(UserProfile profile){
        this.profileId = profile.getProfileId();
        this.accountId = profile.getAccountId();
        this.profileName = profile.getProfileName();
        this.role = profile.getRole();
        this.creationDate = profile.getCreationDate();
    }

    public UserProfileResponse withSchool(SchoolResponse school){
        this.school = school;
        return this;
    }
}