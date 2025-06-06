package com.examples.spring_jpa.examples;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByEmail(String email);

    List<Student> findStudentsByFirstNameEqualsIgnoreCaseAndAgeGreaterThanEqual(String firstName, Integer age); 

}
