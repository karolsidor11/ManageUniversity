package pl.sidor.ManageUniversity.evaluation.model;

import lombok.Builder;
import lombok.Data;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Builder
public class RatingSet implements Serializable {

    private static final long serialVersionUID = 5431802168750467368L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @OneToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "Rating")
    @NotNull
    private double rating;

}
