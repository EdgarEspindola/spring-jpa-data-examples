package com.examples.spring_jpa.examples;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.examples.spring_jpa.examples.book.Book;
import com.examples.spring_jpa.examples.book.BookRepository;
import com.examples.spring_jpa.examples.course.Course;
import com.examples.spring_jpa.examples.courseenrollment.CourseEnrollmentRepository;
import com.examples.spring_jpa.examples.student.Student;
import com.examples.spring_jpa.examples.student.StudentRepository;
import com.examples.spring_jpa.examples.student.StudentService;
import com.examples.spring_jpa.examples.studentidcard.StudentIdCard;
import com.examples.spring_jpa.examples.studentidcard.StudentIdCardRepository;
import com.github.javafaker.Faker;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class ExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamplesApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			// oneToOneUnidirectional(studentRepository, studentIdCardRepository);
			// oneToOneBidirectional(studentRepository);
			// oneToManyExamples(studentRepository, bookRepository, studentService);
			// oneToManyRemove(studentRepository, bookRepository);
			// ManyToManyExamples(studentRepository, courseEnrollmentRepository);
			Book book = new Book();
			book.setTitle("Spring Data JPA");
			
			Student student = new Student("John", "Doe", 20, "john.doe@example.com");
			student.addBook(book);
			Student persistedStudent = studentRepository.save(student);
			System.out.println("Total students before deletion: " + studentRepository.count());

			
			// studentRepository.deleteById(1L);
			System.out.println("Total students after deletion: " + studentRepository.count());

			System.out.println("---Checking auditing fields---");
			System.out.println("Student created by: " + persistedStudent.getCreatedBy());
			System.out.println("Student created at: " + persistedStudent.getCreatedAt());
			System.out.println("Student last modified by: " + persistedStudent.getLastModifiedBy());
			System.out.println("Student last modified at: " + persistedStudent.getLastModifiedAt());



		};
	}

	private void ManyToManyExamples(StudentRepository studentRepository,
			CourseEnrollmentRepository courseEnrollmentRepository) {
		Student john = new Student("John", "Doe", 20, "john.doe@example.com");
		Student jane = new Student("Jane", "Doe", 22, "jane.doe@example.com");

		Course mathematics = new Course("Mathematics", "Science");
		Course physics = new Course("Physics", "Science");

		john.addCourseEnrollment(mathematics);
		john.addCourseEnrollment(physics);
		jane.addCourseEnrollment(mathematics);
		studentRepository.saveAll(Set.of(john, jane));

		courseEnrollmentRepository.findAll().forEach(courseEnrollment -> {
			System.out.println(courseEnrollment.getCourseEnrollmentId());
			System.out.println("Course Enrollment: " + courseEnrollment.getCourse().getName() + " - "
					+ courseEnrollment.getStudent().getFirstName() + " "
					+ courseEnrollment.getStudent().getLastName());
			System.out.println();
		});

		System.out.println("Remove mathematics course from John");
		john.removeCourseEnrollment(mathematics);
		studentRepository.save(john);

		courseEnrollmentRepository.findAll().forEach(courseEnrollment -> {
			System.out.println(courseEnrollment.getCourseEnrollmentId());
			System.out.println("Course Enrollment: " + courseEnrollment.getCourse().getName() + " - "
					+ courseEnrollment.getStudent().getFirstName() + " "
					+ courseEnrollment.getStudent().getLastName());
			System.out.println();
		});
	}

	private void oneToManyRemove(StudentRepository studentRepository, BookRepository bookRepository) {
		Student student = new Student("John", "Doe", 20, "john.doe@example.com");

		Book book = new Book();
		book.setTitle("Spring Data JPA");
		book.setCreatedAt(Instant.now());

		student.addBook(book);
		studentRepository.save(student);

		studentRepository.findAllStudentsWithBooks().forEach(s -> {
			System.out.println(s);
			s.getBooks().forEach(System.out::println);
		});

		System.out.println("Total books: " + bookRepository.count());

		student.removeBook(book);
		studentRepository.save(student);

		System.out.println("Total books after removal: " + bookRepository.count());

		studentRepository.findAllStudentsWithBooks().forEach(s -> {
			System.out.println(s);
			System.out.println("Books size: " + s.getBooks().size());
		});

		System.out.println("Total students: " + studentRepository.count());
	}

	private void oneToManyExamples(StudentRepository studentRepository, BookRepository bookRepository,
			StudentService studentService) {
		Student student = new Student("John", "Doe", 20, "john.doe@example.com");

		Book book = new Book();
		book.setTitle("Spring Data JPA");
		book.setCreatedAt(Instant.now());
		book.setStudent(student);

		student.setBooks(Set.of(book));
		studentRepository.save(student);

		System.out.println("All books:");
		bookRepository.findAllWithStudents().forEach(b -> {
			System.out.println("Book ID: " + b.getId() + ", Title: " + b.getTitle() + ", Student: "
					+ b.getStudent().getFirstName() + " " + b.getStudent().getLastName());
		});

		System.out.println("\nAll students:");
		studentRepository.findAllStudentsWithBooks().forEach(s -> {
			System.out.println("Student ID: " + s.getId() + ", Name: " + s.getFirstName() + " " + s.getLastName()
					+ ", Age: " + s.getAge() + ", Email: " + s.getEmail());

			s.getBooks().forEach(savedBook -> {
				System.out.println("  Book ID: " + savedBook.getId() + ", Title: " + savedBook.getTitle());
			});
		});

		System.out.println("\nUsing studentService to get student with books:");
		studentService.getStudentsWithBooks(1L).ifPresent(s -> {
			System.out.println("Student with ID 1: " + s.getFirstName() + " " + s.getLastName());
			s.getBooks().forEach(b -> System.out.println("  Book: " + b.getTitle()));
		});

		System.out.println("\nUsing entity graph to fetch student with books:");
		studentRepository.findAllWithEntityGraphs().forEach(s -> {
			System.out.println("Student: " + s.getFirstName() + " " + s.getLastName());
			s.getBooks().forEach(b -> System.out.println("  Book: " + b.getTitle()));
		});
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
		// System.out.println(card.get().getStudent()); Could not initialize proxy
		// object - no session available

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
			System.out.println("Student: " + student.getFirstName() + ", Age: " + student.getAge());
		});
	}

}
