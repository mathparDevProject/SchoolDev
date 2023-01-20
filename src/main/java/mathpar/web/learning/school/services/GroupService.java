package mathpar.web.learning.school.services;

import mathpar.web.learning.school.entities.School;
import mathpar.web.learning.school.entities.SchoolGroup;
import mathpar.web.learning.school.entities.UserProfile;
import mathpar.web.learning.school.repositories.GroupRepository;
import mathpar.web.learning.school.utils.exceptions.MalformedDataException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public SchoolGroup createGroup(String name, long teacherId, School school){
        return groupRepository.save(new SchoolGroup(name, new UserProfile(teacherId), school));
    }

    @Transactional
    public void assignTeacher(long groupId, long teacherId){
        groupRepository.updateTeacher(teacherId, groupId);
    }

    public List<UserProfile> getStudents(long groupId){
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new MalformedDataException("This group doesn't exist"))
                .getStudents();
    }

    @Transactional
    public void removeGroup(long groupId){
        groupRepository.deleteById(groupId);
    }

    public void addStudents(long groupId, List<Long> students){
        var group = getGroupById(groupId);
        var additionalUsersList = students.stream().map(UserProfile::new).collect(Collectors.toList());
        additionalUsersList.removeAll(group.getStudents());
        group.getStudents().addAll(additionalUsersList);
        groupRepository.save(group);
    }

    @Transactional
    public void removeStudents(long groupId, List<Long> students){
        groupRepository.deleteAllStudentsById(groupId, students);
    }

    public SchoolGroup getGroupById(long groupId){
        return groupRepository.findById(groupId).orElseThrow(() -> new MalformedDataException("This group isn't exist"));
    }

    @Transactional
    public void deleteAllSchoolGroups(long schoolId){
        groupRepository.deleteAllBySchoolId(schoolId);
    }
}
