package mathpar.web.learning.school.controllers;

import mathpar.web.learning.school.services.UserProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivateSchoolController {
    private final UserProfileService userProfileService;

    public PrivateSchoolController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/getSchoolId")
    public long getSchoolId(@RequestParam("profileId") long profileId){
        return userProfileService.getSchoolId(profileId);
    }
}
