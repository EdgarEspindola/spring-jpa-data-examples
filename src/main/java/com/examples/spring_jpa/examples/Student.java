package com.examples.spring_jpa.examples;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String lastName;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, unique = true)
    private String email;

    public Student(Long id, String firstName, String lastName, Integer age, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public Student(String firstName, String lastName, Integer age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        return Objects.equals(id, other.id) &&
               Objects.equals(firstName, other.firstName) &&
               Objects.equals(lastName, other.lastName) &&
               Objects.equals(age, other.age) &&
               Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, email);
    }

}
