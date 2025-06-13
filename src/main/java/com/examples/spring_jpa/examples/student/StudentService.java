package com.examples.spring_jpa.examples.student;

import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Optional<Student> getStudentsWithBooks(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
           Hibernate.initialize(student.get().getBooks()); // Initialize the collection
        //  student.get().getBooks().size(); // Access the collection to trigger initialization
        }
        return student;
    }
}
