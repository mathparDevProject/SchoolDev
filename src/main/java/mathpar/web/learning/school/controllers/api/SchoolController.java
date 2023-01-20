package mathpar.web.learning.school.controllers.api;

import io.swagger.annotations.Api;
import mathpar.web.learning.school.services.SchoolService;
import mathpar.web.learning.school.services.UserProfileService;
import mathpar.web.learning.school.utils.PublicApi;
import mathpar.web.learning.school.utils.ResponseMapper;
import mathpar.web.learning.school.utils.SecurityUtils;
import mathpar.web.learning.school.utils.dto.payloads.CreateSchoolPayload;
import mathpar.web.learning.school.utils.dto.responses.GetSchoolUsersResponse;
import mathpar.web.learning.school.utils.dto.responses.IsSchoolNameAvailableResponse;
import mathpar.web.learning.school.utils.dto.responses.SchoolResponse;
import mathpar.web.learning.school.utils.enums.Role;
import org.springframework.web.bind.annotation.*;

import static mathpar.web.learning.school.utils.ResponseMapper.mapProfileToResponse;
import static mathpar.web.learning.school.utils.ResponseMapper.mapSchoolToResponse;
import static mathpar.web.learning.school.utils.SchoolUrls.*;
import static mathpar.web.learning.school.utils.ValidationUtilities.hasText;

@PublicApi
@RestController
@Api(tags = "School")
public class SchoolController {
    private final UserProfileService userProfileService;
    private final SchoolService schoolService;

    public SchoolController(UserProfileService userProfileService, SchoolService schoolService) {
        this.userProfileService = userProfileService;
        this.schoolService = schoolService;
    }

    @GetMapping(SCHOOL_URL)
    public SchoolResponse getSchoolByUser(){
        var authentication = SecurityUtils.getUserAuthentication().getDetails();
        var school = authentication.getSchool();
        return mapSchoolToResponse(school, userProfileService.getDirector(school));
    }

    @PostMapping(SCHOOL_URL)
    public SchoolResponse createSchool(@RequestBody CreateSchoolPayload payload){
        var authentication = SecurityUtils.getUserAuthentication();
        var school = schoolService.createSchool(payload.getSchoolName(), payload.getSchoolAddress());
        var directorProfile = userProfileService.createProfile(authentication.getPrincipal(), school, Role.Director);
        return ResponseMapper.mapSchoolToResponse(school, directorProfile);
    }

    @GetMapping(GET_SCHOOL_PROFILES_URL)
    public GetSchoolUsersResponse getUsers(){
        var authenticationDetails = SecurityUtils.getUserAuthentication().getDetails();
        long schoolId = authenticationDetails.getSchool().getId();
        return new GetSchoolUsersResponse()
                .withDirector(ResponseMapper.mapProfileToResponse(schoolService.getDirector(schoolId), false))
                .withHeadTeachers(mapProfileToResponse(schoolService.getHeadTeachers(schoolId)))
                .withTeachers(mapProfileToResponse(schoolService.getTeachers(schoolId)))
                .withStudents(mapProfileToResponse(schoolService.getStudents(schoolId)));
    }
    
    @DeleteMapping(SCHOOL_URL)
    public void deleteSchool(){
        var authenticationDetails = SecurityUtils.getUserAuthentication().getDetails();
        long schoolId = authenticationDetails.getSchool().getId();
        schoolService.purgeSchool(schoolId);
    }

    @GetMapping(IS_SCHOOL_NAME_AVAILABLE_URL)
    public IsSchoolNameAvailableResponse isSchoolNameAvailable(@RequestParam("schoolName") String schoolName){
        hasText(schoolName, "School name can't be empty");

        return new IsSchoolNameAvailableResponse(schoolService.isSchoolNameUnassigned(schoolName));
    }
}
