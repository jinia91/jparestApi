package jpabook.pracjpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PracjpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracjpashopApplication.class, args);
	}

}
