package pl.sidor.ManageUniversity.H2Testing;


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
import pl.sidor.ManageUniversity.student.service.StudentService;
import pl.sidor.ManageUniversity.student.validation.CheckUniqeStudentPredicate;

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

    @Before
    public void setUp()  {
        studentRepo.create(Student.builder()
                .id(1L)
                .name("Karol")
                .lastName("Sidor")
                .email("test1@p.pl")
                .adres(new Adres())
                .phoneNumber(999222333)
                .build());
    }

    @Test
    public void should_add_student()  {

        // when
        studentRepo.create(Student.builder()
                .id(2L)
                .name("Jan")
                .lastName("Nowak")
                .email("test1@p.pl")
                .adres(new Adres())
                .phoneNumber(999222333)
                .build());

       Student byId = studentRepo.findByNameAndLastName("Jan","Nowak").getStudent();

        // then
        assertNotNull(byId);
        assertEquals("Jan", byId.getName());
        assertEquals("Nowak", byId.getLastName());

    }

    @Test(expected = UniversityException.class)
    public void should_throw_add_student()  {

        // when
        studentRepo.create(null);
    }

    @Test
    public  void  find_student_by_ID()  {

        //  when
        Optional<Student> byId = ofNullable(studentRepo.findByNameAndLastName("Karol", "Sidor").getStudent());

        // then
        assertNotNull(byId.get());
        assertEquals("Karol", byId.get().getName());
        assertEquals("Sidor", byId.get().getLastName());
    }

//    @Test(expected = UniversityException.class)
//    public void test_should_delete_student() throws Throwable {
//
//        //  when
//        studentRepo.delete(1L);
//        Student byId = studentRepo.findById(1L);
//
//    }
}
