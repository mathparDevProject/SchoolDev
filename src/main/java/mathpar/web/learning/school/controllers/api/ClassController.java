package mathpar.web.learning.school.controllers.api;

import io.swagger.annotations.Api;
import mathpar.web.learning.school.services.ClassService;
import mathpar.web.learning.school.utils.PublicApi;
import mathpar.web.learning.school.utils.SecurityUtils;
import mathpar.web.learning.school.utils.dto.payloads.BulkClassActionPayload;
import mathpar.web.learning.school.utils.dto.payloads.CreateClassPayload;
import mathpar.web.learning.school.utils.dto.responses.ClassResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static mathpar.web.learning.school.utils.ResponseMapper.mapClassToResponse;
import static mathpar.web.learning.school.utils.SchoolUrls.*;

@RestController
@PublicApi
@Api(tags = "Class")
public class ClassController {
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping(GET_SCHOOL_CLASSES)
    public List<ClassResponse> getClasses(){
        var authenticationDetails = SecurityUtils.getUserAuthentication().getDetails();
        return classService.getClassesForSchool(authenticationDetails.getSchool().getId()).stream()
                .map(clazz -> mapClassToResponse(clazz, false)).collect(Collectors.toList());
    }

    @GetMapping(CLASS_URL)
    public ClassResponse getClass(@RequestParam("classId") long classId){
        return mapClassToResponse(classService.getClassById(classId), true);
    }

    @PostMapping(CLASS_URL)
    public ClassResponse createClass(@RequestBody CreateClassPayload payload){
        var authenticationDetails = SecurityUtils.getUserAuthentication().getDetails();
        return mapClassToResponse(classService.createClass(payload.getName(), authenticationDetails.getSchool()), false);
    }

    @DeleteMapping(CLASS_URL)
    public void deleteClass(@RequestParam("classId") long classId){
        classService.deleteClass(classId);
    }

    @PostMapping(ADD_STUDENTS_TO_CLASS_URL)
    public void addStudents(@RequestBody BulkClassActionPayload payload){
        classService.addStudents(payload.getStudents(), payload.getClassId());
    }

    @PostMapping(REMOVE_STUDENTS_FROM_CLASS_URL)
    public void removeStudents(@RequestBody BulkClassActionPayload payload){
        classService.removeStudents(payload.getStudents(), payload.getClassId());
    }
}
