package pl.sidor.ManageUniversity;

import com.sun.glass.ui.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ComponentScan("pl.sidor")
@SpringBootTest(classes = {Application.class})
public class ManageUniversityApplicationTests {

	@Test
	public void contextLoads() {
	}

}
