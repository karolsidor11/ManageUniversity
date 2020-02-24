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

    private Double polishResult;

    private Double mathResult;

    private Double languageResult;
}
