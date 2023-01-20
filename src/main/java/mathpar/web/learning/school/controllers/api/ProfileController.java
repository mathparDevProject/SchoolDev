package mathpar.web.learning.school.controllers.api;

import io.swagger.annotations.Api;
import mathpar.web.learning.school.entities.UserProfile;
import mathpar.web.learning.school.services.UserProfileService;
import mathpar.web.learning.school.utils.PublicApi;
import mathpar.web.learning.school.utils.ResponseMapper;
import mathpar.web.learning.school.utils.SecurityUtils;
import mathpar.web.learning.school.utils.dto.payloads.CreateProfilePayload;
import mathpar.web.learning.school.utils.dto.payloads.RequestProfilePayload;
import mathpar.web.learning.school.utils.dto.responses.RequestProfileResponse;
import mathpar.web.learning.school.utils.dto.responses.UserProfileResponse;
import mathpar.web.learning.school.utils.exceptions.InvalidAccountException;
import mathpar.web.learning.school.utils.exceptions.MalformedDataException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static mathpar.web.learning.school.utils.SchoolUrls.PROFILE_URL;

@PublicApi
@RestController
@Api(tags = "Profile")
public class ProfileController {
    private final UserProfileService userProfileService;

    public ProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/getAllProfiles")
    public List<UserProfileResponse> getAllProfiles(){
        return userProfileService.getProfiles(SecurityUtils.getUserAuthentication().getPrincipal())
                .stream().map(UserProfileResponse::new).collect(Collectors.toList());
    }

    @GetMapping(PROFILE_URL)
    public UserProfileResponse getProfile(@RequestParam(value = "profileId", required = false, defaultValue = "-1") long profileId){
        UserProfile profile;
        if (profileId<0) profile = SecurityUtils.getUserAuthentication().getDetails().getProfile();
        else profile = userProfileService.getProfile(profileId);
        return ResponseMapper.mapProfileToResponse(profile, false);
    }

    @PostMapping(PROFILE_URL)
    public UserProfileResponse createProfile(@RequestBody CreateProfilePayload payload){
        var authentication = SecurityUtils.getUserAuthentication().getDetails();
        var school = authentication.getSchool();
        return ResponseMapper.mapProfileToResponse(userProfileService.createProfile(payload.getEmail(), school, payload.getRole()), false);
    }

    @PostMapping("/requestProfile")
    @PreAuthorize("!isAnonymous()")
    public RequestProfileResponse requestProfile(@RequestBody RequestProfilePayload payload){
        if(!payload.getPosition().canBeRequested()) throw new MalformedDataException("This role can't be requested");
        try {
            userProfileService.requestProfile(payload.getDirectorEmail(), payload.getPosition());
        }catch (InvalidAccountException e){
            throw new MalformedDataException(e.getMessage());
        }
        return new RequestProfileResponse(true);
    }
}
