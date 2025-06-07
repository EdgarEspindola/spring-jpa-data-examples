package com.examples.spring_jpa.examples;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.github.javafaker.Faker;

@SpringBootApplication
public class ExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamplesApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			generateFakeStudents(studentRepository);
			System.out.println("Total students: " + studentRepository.count());

			Pageable pageable = PageRequest.of(0, 10);
			Page<Student> pagedStudents = studentRepository.findAll(pageable);
			System.out.println(pagedStudents);
			System.out.println(pagedStudents.getTotalPages());
			System.out.println(pagedStudents.getTotalElements());
			System.out.println(pagedStudents.getSize());
			pagedStudents.get().forEach(System.out::println);

			pageable = PageRequest.of(1, 10);
			pagedStudents = studentRepository.findAll(pageable);
			System.out.println("-----------");
			pagedStudents.get().forEach(System.out::println);


			studentRepository.findAll();
		};
	}

	private void generateFakeStudents(StudentRepository studentRepository) {
		Faker faker = new Faker();
		for (int i = 0; i < 100; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = "%s.%s@domain.test".formatted(firstName, lastName);
			Student student = new Student(firstName, lastName, faker.number().numberBetween(18, 30), email);
			studentRepository.save(student);
		}
	}

	private void outputStudentsSorted(StudentRepository studentRepository) {
		Sort sort = Sort.by(Sort.Direction.ASC, "firstName").and(Sort.by("age").descending());
		List<Student> allStudents = studentRepository.findAll(sort);
		
		allStudents.forEach(student -> {
			System.out.println("Student: " + student.getFirstName() +  ", Age: " + student.getAge());
		});
	}

}
