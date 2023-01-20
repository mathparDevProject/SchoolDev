package mathpar.web.learning.school.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import mathpar.web.learning.school.utils.enums.Role;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name = "user_profiles")
public class UserProfile implements Serializable {
    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long profileId;
    @Column(name = "profile_name")
    private String profileName;
    @Column(name = "account_id")
    private long accountId;
    @ManyToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;
    @CreationTimestamp
    @Column(name = "creation_date")
    private Date creationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private Role role;

    public UserProfile(long profileId){
        this.profileId = profileId;
    }

    //Only this method should be used to create new profile. It sets id, populate default values and verifies data
    public static UserProfile of(long accountId, School school, Role role){
        var profile = new UserProfile();
        profile.setProfileName(school.getName()+"-"+role.name());
        profile.setAccountId(accountId);
        profile.setSchool(school);
        profile.setRole(role);
        return profile;
    }
}
