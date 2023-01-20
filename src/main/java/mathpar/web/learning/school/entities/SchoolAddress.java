package mathpar.web.learning.school.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity(name = "school_addresses")
public class SchoolAddress implements Serializable {
    @Id
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School schoolId;
    @Column(name = "short_address")
    private String shortAddress;

    public SchoolAddress(String shortAddress, School school){
        this.shortAddress = shortAddress;
        this.schoolId = school;
    }

    @Override
    public String toString() {
        return "SchoolAddress{" +
                "schoolId=" + schoolId +
                '}';
    }
}
