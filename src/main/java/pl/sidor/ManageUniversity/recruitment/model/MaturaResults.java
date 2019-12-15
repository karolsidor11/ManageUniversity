package pl.sidor.ManageUniversity.recruitment.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class MaturaResults  implements Serializable {

    private static final long serialVersionUID = -5933059697057372893L;

    @Column(name = "Polski")
    private Double polishResult;

    @Column(name = "Matematyka")
    private Double mathResult;

    @Column(name = "Jezyk_obcy")
    private Double languageResult;
}
