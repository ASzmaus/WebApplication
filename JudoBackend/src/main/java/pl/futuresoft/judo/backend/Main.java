package pl.futuresoft.judo.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;


@SpringBootApplication
@EnableScheduling
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder bCryptEncoder() {
		return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
	}
	
	@Bean
	HttpSessionStrategy sessionStrategy() {
	    return new HeaderHttpSessionStrategy();	  }


}

