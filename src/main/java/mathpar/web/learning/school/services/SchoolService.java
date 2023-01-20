package mathpar.web.learning.school.services;

import mathpar.web.learning.school.entities.School;
import mathpar.web.learning.school.entities.UserProfile;
import mathpar.web.learning.school.repositories.SchoolRepository;
import mathpar.web.learning.school.repositories.UserProfileRepository;
import mathpar.web.learning.school.utils.enums.Role;
import mathpar.web.learning.school.utils.exceptions.MalformedDataException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final UserProfileRepository userProfileRepository;

    public SchoolService(SchoolRepository schoolRepository, UserProfileRepository userProfileRepository) {
        this.schoolRepository = schoolRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public School createSchool(String name, String address){
        return schoolRepository.save(new School(name, address));
    }

    public School getSchoolById(long schoolId){
        return schoolRepository.findById(schoolId).orElseThrow(MalformedDataException::new);
    }

    public UserProfile getDirector(long schoolId){
        return userProfileRepository.findBySchoolAndRole(new School(schoolId), Role.Director).get(0);
    }

    public List<UserProfile> getHeadTeachers(long schoolId){
        return userProfileRepository.findBySchoolAndRole(new School(schoolId), Role.HeadTeacher);
    }

    public List<UserProfile> getTeachers(long schoolId){
        return userProfileRepository.findBySchoolAndRole(new School(schoolId), Role.Teacher);
    }

    public List<UserProfile> getStudents(long schoolId){
        return userProfileRepository.findBySchoolAndRole(new School(schoolId), Role.Student);
    }

    public boolean isSchoolNameUnassigned(String schoolName){
        return schoolRepository.isSchoolNameUnassigned(schoolName).isEmpty();
    }

    @Transactional
    public void purgeSchool(long schoolId){
        schoolRepository.deleteById(schoolId);
    }
}
