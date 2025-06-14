package com.examples.spring_jpa.examples.student;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.email = :age")
    Optional<Student> findStudentByEmail(@Param("age") String email);

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName AND s.age >= :age")
    List<Student> findStudentsByFirstNameEqualsIgnoreCaseAndAgeGreaterThanEqual(@Param("firstName") String firstName,
            @Param("age") Integer age);

    @Query(value = "SELECT * FROM Student s WHERE s.first_name = :firstName AND s.age >= :age", nativeQuery = true)
    List<Student> findStudentsByFirstNameEqualsIgnoreCaseAndAgeGreaterThanEqualNative(
            @Param("firstName") String firstName,
            @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.email = :email")
    int deleteStudentByEmail(@Param("email") String email);

    @Query("SELECT s FROM Student s JOIN FETCH s.books")
    List<Student> findAllStudentsWithBooks();

    @EntityGraph(attributePaths = {"books", "courseEnrollments"})
    @Query("SELECT s FROM Student s")
    List<Student> findAllStudentsWithBooksAndCourseEnrollments();

    @EntityGraph(attributePaths = {"books"})
    @Query("SELECT s FROM Student s")
    List<Student> findAllWithEntityGraphs();

    @Query("SELECT s FROM Student s JOIN FETCH s.books WHERE s.id = :id")
    List<Student> findStudentsByIdWithBooks(@Param("id") String id);

}
