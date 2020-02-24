package pl.sidor.ManageUniversity.utils;

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
                .maturaResults(prepareResultMatura())
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

    private  static  MaturaResults prepareResultMatura(){
        return  MaturaResults.builder()
                .languageResult(100D)
                .mathResult(90D)
                .polishResult(100D)
                .build();
    }
}
