package com.example.Sesiones_7_8_9;

import com.example.Sesiones_7_8_9.entities.Laptop;
import com.example.Sesiones_7_8_9.repositories.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Sesiones789Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Sesiones789Application.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);

		// Crear laptops
		Laptop laptop = new Laptop(null, "Apple", "MacBook Pro", 1999.9, 10, 595);
		Laptop laptop2 = new Laptop(null, "Dell", "Dell Pro", 999.9, 5, 395);

		repository.save(laptop);
		repository.save(laptop2);
	}

}
