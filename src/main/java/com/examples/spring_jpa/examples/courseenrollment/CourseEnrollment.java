package com.examples.spring_jpa.examples.courseenrollment;

import com.examples.spring_jpa.examples.course.Course;
import com.examples.spring_jpa.examples.student.Student;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.ForeignKey;

@Entity
public class CourseEnrollment {

    @EmbeddedId
    private CourseEnrollmentId courseEnrollmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "enrollment_student_id_fk"))
    @MapsId("studentId")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "enrollment_course_id_fk"))
    @MapsId("courseId")
    private Course course;

    public CourseEnrollment() {
    }

    public CourseEnrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.courseEnrollmentId = new CourseEnrollmentId(student.getId(), course.getId());
    }

    public CourseEnrollmentId getCourseEnrollmentId() {
        return courseEnrollmentId;
    }

    public void setCourseEnrollmentId(CourseEnrollmentId courseEnrollmentId) {
        this.courseEnrollmentId = courseEnrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    
}
