package com.examples.spring_jpa.examples.student;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.spring_jpa.examples.book.BookResponse;
import com.examples.spring_jpa.examples.course.CourseResponse;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAllStudentsWithBooksAndCourseEnrollments().stream()
                .map(student -> new StudentResponse(
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getBooks()
                            .stream()
                            .map(book -> new BookResponse(book.getId(), book.getTitle()))
                            .collect(Collectors.toSet()),
                        student.getCourseEnrollments()
                                .stream()
                                .map(courseEnrollment -> new CourseResponse(
                                        courseEnrollment.getCourse().getId(),
                                        courseEnrollment.getCourse().getName(),
                                        courseEnrollment.getCourse().getDepartment()))
                                .collect(Collectors.toSet()),
                        student.getCreatedAt()))
                .toList();
    }
}
