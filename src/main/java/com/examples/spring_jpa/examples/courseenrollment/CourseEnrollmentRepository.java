package com.examples.spring_jpa.examples.courseenrollment;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, CourseEnrollmentId> {

    @Override
    @EntityGraph(attributePaths = {"student", "course"})
    List<CourseEnrollment> findAll();
}
