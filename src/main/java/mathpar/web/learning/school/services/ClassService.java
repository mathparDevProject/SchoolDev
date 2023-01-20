package mathpar.web.learning.school.services;

import mathpar.web.learning.school.entities.School;
import mathpar.web.learning.school.entities.SchoolClass;
import mathpar.web.learning.school.entities.UserProfile;
import mathpar.web.learning.school.repositories.ClassRepository;
import mathpar.web.learning.school.utils.exceptions.MalformedDataException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {
    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public SchoolClass createClass(String name, School school){
        return classRepository.save(new SchoolClass(name, school));
    }

    @Transactional
    public void deleteClass(long classId){
        classRepository.deleteById(classId);
    }

    public void addStudents(List<Long> students, long classId){
        var studentClass = getClassById(classId);
        var additionalUsersList = students.stream().map(UserProfile::new).collect(Collectors.toList());
        additionalUsersList.removeAll(studentClass.getStudents());
        studentClass.getStudents().addAll(additionalUsersList);
        classRepository.save(studentClass);
    }

    @Transactional
    public void removeStudents(List<Long> students, long classId){
        classRepository.deleteStudentsFromClass(students, classId);
    }

    public SchoolClass getClassById(long classId){
        return classRepository.findById(classId).orElseThrow(() -> new MalformedDataException("This group isn't exist"));
    }

    public List<SchoolClass> getClassesForSchool(long schoolId){
        return classRepository.findAllBySchoolId(schoolId);
    }

    @Transactional
    public void deleteAllSchoolClasses(long schoolId){
        classRepository.deleteAllBySchoolId(schoolId);
    }
}
