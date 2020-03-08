package pl.sidor.ManageUniversity.recruitment.model;

import lombok.*;

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

    public static MaturaResults getDefaultValues(){
        return MaturaResults.builder()
                .polishResult(0.0)
                .mathResult(0.0)
                .languageResult(0.0)
                .build();
    }
}
