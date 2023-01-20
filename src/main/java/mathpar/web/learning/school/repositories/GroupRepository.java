package mathpar.web.learning.school.repositories;

import mathpar.web.learning.school.entities.SchoolGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<SchoolGroup, Long> {
    @Modifying
    @Query(value = "update school_groups set teacher_id=?1 where id=?2", nativeQuery = true)
    void updateTeacher(long teacherId, long groupId);

    @Modifying
    @Query(value = "delete from group_student where group_id=?1 and student_id in ?2", nativeQuery = true)
    void deleteAllStudentsById(long groupId, List<Long> studentIds);

    void deleteAllBySchoolId(long schoolId);
}
