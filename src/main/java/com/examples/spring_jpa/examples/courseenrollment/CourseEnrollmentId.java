package com.examples.spring_jpa.examples.courseenrollment;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class CourseEnrollmentId {
    private Long studentId;

    private Long courseId;

    public CourseEnrollmentId() {
    }

    public CourseEnrollmentId(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseEnrollmentId)) return false;
        CourseEnrollmentId that = (CourseEnrollmentId) o;
        return Objects.equals(studentId, that.studentId) &&
               Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }

    @Override
    public String toString() {
        return "CourseEnrollmentId [studentId=" + studentId + ", courseId=" + courseId + "]";
    }

    

}
