package app.pharmacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PharmacyFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyFinderApplication.class, args);
	}

}
