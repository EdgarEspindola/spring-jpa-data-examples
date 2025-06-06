package com.examples.spring_jpa.examples;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamplesApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			// Example usage of the repository
			Student student1 = new Student("John", "Doe", 20, "john.doe@example.com");
			studentRepository.save(student1);
			System.out.println("Total students: " + studentRepository.count());
			System.out.println(studentRepository.findAll());
			System.out.println(studentRepository.findById(1L));
			System.out.println(studentRepository.existsById(1L));
			System.out.println(studentRepository.existsById(2L));
			studentRepository.deleteById(1L);
			System.out.println("Total students after deletion: " + studentRepository.count());
		};
	}

}
