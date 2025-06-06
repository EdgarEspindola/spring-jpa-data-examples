package com.examples.spring_jpa.examples;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.email = :age")
    Optional<Student> findStudentByEmail(@Param("age") String email);

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName AND s.age >= :age")
    List<Student> findStudentsByFirstNameEqualsIgnoreCaseAndAgeGreaterThanEqual(@Param("firstName") String firstName,
            @Param("age") Integer age);

}
