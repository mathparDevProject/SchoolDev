package mathpar.web.learning.school.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity(name = "school_groups")
public class SchoolGroup {
    @Id
    @ToString.Include
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ToString.Include
    @Column(name = "name")
    private String name;
    @ManyToOne(optional = false)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;
    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", referencedColumnName = "profile_id")
    private UserProfile teacher;
    @ManyToMany
    @JoinTable(name = "group_student",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "profile_id"))
    private List<UserProfile> students;

    public SchoolGroup(String name, UserProfile teacher, School school) {
        this.name = name;
        this.teacher = teacher;
        this.school = school;
    }
}
