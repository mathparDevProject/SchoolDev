package mathpar.web.learning.school.controllers.api;

import io.swagger.annotations.Api;
import mathpar.web.learning.school.services.GroupService;
import mathpar.web.learning.school.services.UserProfileService;
import mathpar.web.learning.school.utils.PublicApi;
import mathpar.web.learning.school.utils.ResponseMapper;
import mathpar.web.learning.school.utils.SecurityUtils;
import mathpar.web.learning.school.utils.dto.payloads.AssignTeacherToGroupPayload;
import mathpar.web.learning.school.utils.dto.payloads.BulkGroupActionPayload;
import mathpar.web.learning.school.utils.dto.payloads.CreateGroupPayload;
import mathpar.web.learning.school.utils.dto.responses.GroupResponse;
import mathpar.web.learning.school.utils.enums.Role;
import mathpar.web.learning.school.utils.exceptions.MalformedDataException;
import org.springframework.web.bind.annotation.*;

import static mathpar.web.learning.school.utils.ResponseMapper.mapSchoolToResponse;
import static mathpar.web.learning.school.utils.ResponseMapper.mapProfileToResponse;
import static mathpar.web.learning.school.utils.SchoolUrls.*;

@RestController
@PublicApi
@Api(tags = "Group")
public class GroupController {
    private final GroupService groupService;
    private final UserProfileService userProfileService;

    public GroupController(GroupService groupService, UserProfileService userProfileService) {
        this.groupService = groupService;
        this.userProfileService = userProfileService;
    }

    @GetMapping(GROUP_URL)
    public GroupResponse getStudents(@RequestParam("groupId") long groupId){
        var group = groupService.getGroupById(groupId);
        return new GroupResponse(group.getId(), group.getName())
                .withStudents(mapProfileToResponse(group.getStudents()))
                .withTeacher(ResponseMapper.mapProfileToResponse(group.getTeacher(), false));
    }

    @PostMapping(GROUP_URL)
    public GroupResponse createGroup(@RequestBody CreateGroupPayload payload){
        var authenticationDetails = SecurityUtils.getUserAuthentication().getDetails();
        var teacher = userProfileService.getProfile(payload.getTeacherProfileId());
        if(!teacher.getRole().equals(Role.Teacher))
            throw new MalformedDataException("Only teacher can be assigned to the group");
        var group = groupService.createGroup(payload.getName(), payload.getTeacherProfileId(), authenticationDetails.getSchool());
        return new GroupResponse(group.getId(), group.getName())
                .withSchool(mapSchoolToResponse(group.getSchool(), null))
                .withTeacher(ResponseMapper.mapProfileToResponse(teacher, false));
    }

    @DeleteMapping(GROUP_URL)
    public void deleteGroup(@RequestParam("groupId") long groupId){
        groupService.removeGroup(groupId);
    }

    @PostMapping(ASSIGN_TEACHER_TO_GROUP)
    public void assignTeacherToGroup(@RequestBody AssignTeacherToGroupPayload payload){
        var userType = userProfileService.getUserType(payload.getTeacherId());
        if(!userType.equals(Role.Teacher))
            throw new MalformedDataException("Group can be assigned only to teacher");
        groupService.assignTeacher(payload.getGroupId(), payload.getTeacherId());
    }

    @PostMapping(ADD_STUDENTS_TO_GROUP_URL)
    public void addStudents(@RequestBody BulkGroupActionPayload payload){
        //TODO Add check for userIds are actually students and belong to this school
        groupService.addStudents(payload.getGroupId(), payload.getStudents());
    }

    @PostMapping(REMOVE_STUDENTS_FROM_GROUP_URL)
    public void deleteStudents(@RequestBody BulkGroupActionPayload payload){
        groupService.removeStudents(payload.getGroupId(), payload.getStudents());
    }
}
