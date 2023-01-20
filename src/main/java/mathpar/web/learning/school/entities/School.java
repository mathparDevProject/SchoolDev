package mathpar.web.learning.school.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity(name = "schools")
public class School implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "approved")
    private boolean approved;
    @ToString.Exclude
    @OneToOne(mappedBy = "schoolId", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    private SchoolAddress address;

    public School(long id){
        this.id = id;
    }

    public School(String name, String address){
        this.name = name;
        this.address = new SchoolAddress(address, this);
    }
}
