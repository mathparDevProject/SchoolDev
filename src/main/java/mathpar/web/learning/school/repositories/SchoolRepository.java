package mathpar.web.learning.school.repositories;

import mathpar.web.learning.school.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    @Query(value = "select 1 from schools where `name`=?1", nativeQuery = true)
    Optional<Integer> isSchoolNameUnassigned(String schoolName);
}
