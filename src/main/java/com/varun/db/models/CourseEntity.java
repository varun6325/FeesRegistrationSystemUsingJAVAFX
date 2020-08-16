package com.varun.db.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Course", schema = "FeesRegistration", catalog = "")
public class CourseEntity {
    private int courseId;
    private String courseName;
    private String courseDesc;
    private BigDecimal courseFees;
    private Collection<RegistrationEntity> registrationsByCourseId;

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "courseId", insertable=false, updatable=false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "courseName")
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Basic
    @Column(name = "courseDesc")
    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    @Basic
    @Column(name = "courseFees")
    public BigDecimal getCourseFees() {
        return courseFees;
    }

    public void setCourseFees(BigDecimal courseFees) {
        this.courseFees = courseFees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity that = (CourseEntity) o;
        return courseId == that.courseId &&
                Objects.equals(courseName, that.courseName) &&
                Objects.equals(courseDesc, that.courseDesc) &&
                Objects.equals(courseFees, that.courseFees);
    }

    @Override
    public int hashCode() {

        return Objects.hash(courseId, courseName, courseDesc, courseFees);
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseDesc='" + courseDesc + '\'' +
                ", courseFees=" + courseFees +
                '}';
    }

    @OneToMany(mappedBy = "courseByCourseId", fetch = FetchType.LAZY)
    public Collection<RegistrationEntity> getRegistrationsByCourseId() {
        return registrationsByCourseId;
    }

    public void setRegistrationsByCourseId(Collection<RegistrationEntity> registrationsByCourseId) {
        this.registrationsByCourseId = registrationsByCourseId;
    }
}
