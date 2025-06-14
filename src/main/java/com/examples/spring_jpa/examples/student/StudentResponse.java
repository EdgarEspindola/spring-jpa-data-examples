package com.examples.spring_jpa.examples.student;

import java.time.Instant;
import java.util.Set;

import com.examples.spring_jpa.examples.book.BookResponse;
import com.examples.spring_jpa.examples.course.CourseResponse; 

public record StudentResponse(
    Long id,
    String firstName,
    String lastName,
    Set<BookResponse> books,
    Set<CourseResponse> courseEnrollments,
    Instant createdAt
) {

}
