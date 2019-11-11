package configuration.data;

import pl.sidor.ManageUniversity.dto.CandidateDto;
import pl.sidor.ManageUniversity.model.Adres;
import pl.sidor.ManageUniversity.recruitment.enums.StudyDirection;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.model.MaturaResults;

public class TestCandidateData {

    public static Candidate prepareCandidate(){
        return Candidate.builder()
                .id(1L)
                .name("Karol")
                .lastName("Sidor")
                .adres(new Adres())
                .email("karolsidor11@wp.pl")
                .maturaResults(new MaturaResults())
                .studyDirection(StudyDirection.INFORMATYKA)
                .phoneNumber(505050500)
                .build();
    }

    public static CandidateDto prepareCandidateDto(){
        return CandidateDto.builder()
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .build();
    }
}
