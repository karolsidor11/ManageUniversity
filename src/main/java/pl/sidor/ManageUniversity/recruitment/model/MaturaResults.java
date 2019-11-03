package pl.sidor.ManageUniversity.recruitment.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaturaResults {

    @Column(name = "Polski")
    private Double polishResult;

    @Column(name = "Matematyka")
    private Double mathResult;

    @Column(name = "Jezyk_obcy")
    private Double languageResult;
}
