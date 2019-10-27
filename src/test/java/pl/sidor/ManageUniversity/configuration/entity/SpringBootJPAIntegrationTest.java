package pl.sidor.ManageUniversity.configuration.entity;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sidor.ManageUniversity.model.Adres;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.repository.StudentRepo;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class SpringBootJPAIntegrationTest {


    @Autowired
    private GenericEntityRepository genericEntityRepository;

    @Autowired
    private StudentRepo studentRepo;

    @Test
    public void givenGenericEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
        GenericEntity genericEntity = genericEntityRepository
                .save(new GenericEntity("test"));

        GenericEntity foundEntity = genericEntityRepository.findById(genericEntity.getId()).get();

        assertNotNull(foundEntity);
        assertEquals(genericEntity.getValue(), foundEntity.getValue());
    }

    @Test
    public void addStudent(){
        Student kArol = studentRepo.save(Student.builder().id(1L).name("KArol")
                .lastName("asa")
                .adres(new Adres())
                .phoneNumber(999222333).build());

        assertNotNull(kArol);
        assertEquals("KArol", kArol.getName());


    }
}
