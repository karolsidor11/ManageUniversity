package pl.sidor.ManageUniversity.H2Testing;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.service.SubjectService;

import static org.junit.Assert.assertEquals;
import static pl.sidor.ManageUniversity.utils.TestSubjectData.prepareSubject;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class SubjectTests {

    @Autowired
    private SubjectService subjectService;

    @Test
    public void should_save_subject() throws Throwable {

        //  given
        Subject subject = prepareSubject();

        // when
        Subject save = subjectService.save(subject).getSubject();

        // then
        assertEquals(save.getName(),"Java" );
        assertEquals(save.getRoomNumber(),22 );
    }

    @Test
    public void should_find_subject() throws Throwable {

        //  given
        Subject subject = prepareSubject();

        // when
        Subject save = subjectService.save(subject).getSubject();

        Subject byId = subjectService.getById(save.getId()).getSubject();

        // then
        assertEquals(byId.getName(),"Java" );
        assertEquals(byId.getRoomNumber(),22 );
    }


    @Test
    public void should_delete_subject() throws Throwable {

        // when
        Subject save = subjectService.save(prepareSubject()).getSubject();

       subjectService.delete(save.getId());

    }
}
