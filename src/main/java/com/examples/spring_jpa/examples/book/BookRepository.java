package com.examples.spring_jpa.examples.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN FETCH b.student")
    List<Book> findAllWithStudents();

    @Query("SELECT new com.examples.spring_jpa.examples.book.BookResponse(b.id, b.title) " +
            " FROM Book b")
    List<BookResponse> findAllBookResponse();

    @Query("SELECT new com.examples.spring_jpa.examples.book.BookResponse(b.id, b.title) " +
            " FROM Book b " +
            " WHERE b.id = :id")
    Optional<BookResponse> findBookResponseById(@Param("id") Long id);
}
