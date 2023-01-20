package mathpar.web.learning.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import mathpar.web.learning.school.entities.SchoolClass;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassRepository extends JpaRepository<SchoolClass, Long> {
    List<SchoolClass> findAllBySchoolId(long schoolId);

    void deleteAllBySchoolId(long schoolId);

    @Modifying
    @Query(value = "delete from class_student where student_id in ?1 and class_id=?2", nativeQuery = true)
    void deleteStudentsFromClass(List<Long> userIds, long classId);
}
