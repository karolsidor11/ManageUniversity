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
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "Study_Year")
    private Integer year;

    @Column(name = "Study_term")
    private Integer term;

    @Column(name = "Study_group")
    private Double group;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "StudentCard_2_StudentRating",
            joinColumns = @JoinColumn(name = "StudentCard_ID"),
            inverseJoinColumns = @JoinColumn(name = "StudentRating_ID"))
    private List<RatingSet> ratingSetList;
}
