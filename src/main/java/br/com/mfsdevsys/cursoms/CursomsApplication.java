package br.com.mfsdevsys.cursoms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
//(exclude={DataSourceAutoConfiguration.class}) 
@RestController
public class CursomsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursomsApplication.class, args);
	}

}
