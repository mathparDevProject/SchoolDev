package mathpar.web.learning.school.repositories;

import mathpar.web.learning.school.entities.School;
import mathpar.web.learning.school.entities.UserProfile;
import mathpar.web.learning.school.utils.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    List<UserProfile> findBySchoolAndRole(School school, Role role);

    @Query(value = "select roles from user_profiles where profile_id=?1", nativeQuery = true)
    Role getUserType(long userId);

    void deleteBySchool(School school);

    List<UserProfile> findAllByAccountId(long accountId);

    @Query(value = "select school_id from user_profiles where profile_id=?1", nativeQuery = true)
    Long getSchoolId(long profileId);
}
