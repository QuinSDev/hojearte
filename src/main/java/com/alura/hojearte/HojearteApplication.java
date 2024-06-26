package com.alura.hojearte;

import com.alura.hojearte.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HojearteApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HojearteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.mostrarElMenu();
	}
}
