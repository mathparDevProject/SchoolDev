package mathpar.web.learning.school.utils;

import mathpar.web.learning.school.entities.School;
import mathpar.web.learning.school.entities.SchoolClass;
import mathpar.web.learning.school.entities.UserProfile;
import mathpar.web.learning.school.utils.dto.responses.ClassResponse;
import mathpar.web.learning.school.utils.dto.responses.SchoolResponse;
import mathpar.web.learning.school.utils.dto.responses.UserProfileResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseMapper {
    public static SchoolResponse mapSchoolToResponse(School school, UserProfile director){
        var response = new SchoolResponse(school.getId(), school.getName(), school.getAddress()!=null?school.getAddress().getShortAddress():null);
        if(director!=null){
            response.withDirector(mapProfileToResponse(director, false));
        }
        return response;
    }

    public static UserProfileResponse mapProfileToResponse(UserProfile userProfile, boolean includeSchool){
        var response = new UserProfileResponse(userProfile.getProfileId(), userProfile.getAccountId(), userProfile.getProfileName(), userProfile.getRole(), userProfile.getCreationDate());
        if(includeSchool){
            var school = userProfile.getSchool();
            response.withSchool(new SchoolResponse(school.getId(), school.getName(), school.getAddress().getShortAddress()));
        }
        return response;
    }

    public static List<UserProfileResponse> mapProfileToResponse(List<UserProfile> userProfiles){
        return userProfiles.stream().map(user -> mapProfileToResponse(user, false)).collect(Collectors.toList());
    }

    public static ClassResponse mapClassToResponse(SchoolClass classToMap, boolean includeStudents){
        var response = new ClassResponse(classToMap.getId(), classToMap.getName());
        if (includeStudents) response.withStudents(mapProfileToResponse(classToMap.getStudents()));
        return response;
    }
}
