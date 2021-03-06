package pl.sidor.ManageUniversity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@SpringBootApplication
public class ManageUniversityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageUniversityApplication.class, args);
	}

}
