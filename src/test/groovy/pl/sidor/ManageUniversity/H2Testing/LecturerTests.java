package pl.sidor.ManageUniversity.H2Testing;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.response.LecturerResponse;
import pl.sidor.ManageUniversity.lecturer.service.LecturerService;
import pl.sidor.ManageUniversity.model.Adres;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Ignore
public class LecturerTests {

    @Autowired
    private LecturerService lecturerService;

    @Test
    public  void should_add_lecturer(){
        //  given:
        Lecturer lecturer = getLecturer().getLecturer();
        //  when
        lecturerService.create(lecturer);
        Lecturer byId = lecturerService.findById(9L).getLecturer();

        // then
        assertEquals(byId.getName(), "Karol");
    }

    @Test
    public void should_findLecturer_by_id() {
        // given
        lecturerService.create(getLecturer().getLecturer());
        //  when
        Lecturer byId = lecturerService.findById(9L).getLecturer();

        // then
        assertEquals(byId.getName(), "Karol");
        assertEquals(byId.getLastName(), "Sidor");
        assertEquals(byId.getEmail(), "karol@wp.pl");
    }

    @Test
    public void should_delete_lecturer_by_id() {
        lecturerService.create(getLecturer().getLecturer());

        Lecturer byId = lecturerService.findById(9L).getLecturer();
        lecturerService.delete(byId.getId());
    }

    private LecturerResponse getLecturer(){
       return lecturerService.create(Lecturer.builder()
                .id(9L)
                .name("Karol")
                .lastName("Sidor")
                .email("karol@wp.pl")
                .grade("Doktor")
                .adres(new Adres())
                .phoneNumber(909090909)
                .build());
    }
}
