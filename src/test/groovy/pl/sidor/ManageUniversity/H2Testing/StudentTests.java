package pl.sidor.ManageUniversity.H2Testing;


import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.model.Adres;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;
import pl.sidor.ManageUniversity.student.service.StudentService;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Ignore
public class StudentTests {

    @Autowired
    private StudentService studentRepo;
    @Autowired
    private StudentRepo repo;

    @Before
    public void setUp() {
        studentRepo.create(getStudent());
    }

    @After
    public void after() {
        repo.deleteAll();
    }

    @Test
    public void should_add_student() {
        // when
        studentRepo.create(getStudent());
        Student byId = studentRepo.findByNameAndLastName("Karol", "Sidor").getStudent();

        // then
        assertNotNull(byId);
        assertEquals("Karol", byId.getName());
        assertEquals("Sidor", byId.getLastName());
    }

    @Test(expected = UniversityException.class)
    public void should_throw_add_student() {
        // when
        studentRepo.create(null);
    }

    @Test
    public void find_student_by_ID() {
        //  when
        Optional<Student> byId = ofNullable(studentRepo.findByNameAndLastName("Karol", "Sidor").getStudent());

        // then
        assertNotNull(byId.get());
        assertEquals("Karol", byId.get().getName());
        assertEquals("Sidor", byId.get().getLastName());
    }

    private Student getStudent() {
        return Student.builder()
                .id(1L)
                .name("Karol")
                .lastName("Sidor")
                .email("test1@p.pl")
                .adres(new Adres())
                .phoneNumber(999222333)
                .build();
    }
}
