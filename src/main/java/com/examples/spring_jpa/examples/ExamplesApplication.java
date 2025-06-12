package com.examples.spring_jpa.examples;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import com.github.javafaker.Faker;

@SpringBootApplication
public class ExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamplesApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository) {
		return args -> {
			oneToOneUnidirectional(studentRepository, studentIdCardRepository);

			oneToOneBidirectional(studentRepository);
		};
	}

	private void oneToOneBidirectional(StudentRepository studentRepository) {
		StudentIdCard studentIdCard = new StudentIdCard();
		studentIdCard.setCardNumber("ID-12345");

		Student student = new Student("John", "Doe", 20, "jeniffer.doe@domain.test");
		student.setStudentIdCard(studentIdCard);
		studentIdCard.setStudent(student);
		studentRepository.save(student);

		System.out.println("----------------");
		Optional<Student> studentById = studentRepository.findById(1L);
		System.out.println(studentById.get().getStudentIdCard());

		System.out.println("----------------");
		studentRepository.deleteById(1L);
	}

	private void oneToOneUnidirectional(StudentRepository studentRepository,
			StudentIdCardRepository studentIdCardRepository) {
		Student student = new Student("John", "Doe", 20, "john.doe@domain.test");
		student = studentRepository.save(student);

		StudentIdCard studentIdCard = new StudentIdCard();
		studentIdCard.setCardNumber("ID-12346");
		studentIdCard.setStudent(student);
		studentIdCardRepository.save(studentIdCard);

		System.out.println("----------------");
		Optional<StudentIdCard> card = studentIdCardRepository.findById(1L);
		System.out.println(card.get().getId());
		System.out.println(card.get().getCardNumber());	
		//System.out.println(card.get().getStudent()); Could not initialize proxy object - no session available

		Optional<StudentIdCard> studentIdCardNumberById = studentIdCardRepository.findStudentIdCardNumberById(1L);
		System.out.println(studentIdCardNumberById.get().getStudent());
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
