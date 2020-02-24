package pl.sidor.ManageUniversity.evaluation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sidor.ManageUniversity.student.model.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRatingsCard implements Serializable {

    private static final long serialVersionUID = -356863025903339985L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn()
    private Student student;

    private Integer year;

    private Integer term;

    private Double groups;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RatingSet> ratingSetList;
}
