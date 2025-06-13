package com.examples.spring_jpa.examples.studentidcard;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentIdCardRepository extends JpaRepository<StudentIdCard, Long> {

    @Query("SELECT c FROM StudentIdCard c JOIN FETCH c.student WHERE c.id = :id")
    Optional<StudentIdCard> findStudentIdCardNumberById(@Param("id") Long id);
}
