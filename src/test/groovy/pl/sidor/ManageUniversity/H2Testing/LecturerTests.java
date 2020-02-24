package pl.sidor.ManageUniversity.H2Testing;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.service.LecturerService;
import pl.sidor.ManageUniversity.model.Adres;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Ignore
public class LecturerTests {

    @Autowired
    private LecturerService lecturerService;


    @Before
    public void setUp() throws Throwable {
       lecturerService.create(Lecturer.builder()
                .id(9L)
                .name("Karol")
                .lastName("Sidor")
                .email("jan@wp.pl")
                .grade("Doktor")
                .adres(new Adres())
                .phoneNumber(909090909)
                .build());

    }

    @Test
    public  void should_add_lecturer() throws Throwable {

        //  given:
        Lecturer lecturer= Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Nowak")
                .email("jan11@wp.pl")
                .grade("Doktor")
                .adres(new Adres())
                .phoneNumber(909090909)
                .build();
        //  when
        lecturerService.create(lecturer);
        Lecturer byId = lecturerService.findById(1L);

        // then
        assertEquals(byId.getName(), "Jan");

    }

    @Test
    public void should_findLecturer_by_id() throws Throwable {
        // given
        lecturerService.create(Lecturer.builder()
                .id(2L)
                .name("Karol")
                .lastName("Sidor")
                .email("janek@wp.pl")
                .grade("Doktor")
                .adres(new Adres())
                .phoneNumber(909090909)
                .build());
        //  when
        Lecturer byId = lecturerService.findById(2L);

        // then
        assertEquals(byId.getName(), "Karol");
        assertEquals(byId.getLastName(), "Sidor");
        assertEquals(byId.getEmail(), "janek@wp.pl");
    }

    @Test
    public void should_delete_lecturer_by_id() throws Throwable {

        lecturerService.create(Lecturer.builder()
                .id(2L)
                .name("Marek")
                .lastName("Nowak")
                .email("nowakowski@wp.pl")
                .grade("Profesor")
                .adres(new Adres())
                .build());

        Lecturer byId = lecturerService.findById(2L);
        lecturerService.delete(byId.getId());
    }
}
