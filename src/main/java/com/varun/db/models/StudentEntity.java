package com.varun.db.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Student", schema = "FeesRegistration", catalog = "")
public class StudentEntity {
    private int studentId;
    private String studentFName;
    private String studentMName;
    private String studentLName;
    private Integer studentAge;
    private Date studentDataOfBirth;
    private String studentPhNo;
    private Collection<RegistrationEntity> registrationsByStudentId;

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "studentId", insertable=false, updatable=false)
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "studentFName")
    public String getStudentFName() {
        return studentFName;
    }

    public void setStudentFName(String studentFName) {
        this.studentFName = studentFName;
    }

    @Basic
    @Column(name = "studentMName")
    public String getStudentMName() {
        return studentMName;
    }

    public void setStudentMName(String studentMName) {
        this.studentMName = studentMName;
    }

    @Basic
    @Column(name = "studentLName")
    public String getStudentLName() {
        return studentLName;
    }

    public void setStudentLName(String studentLName) {
        this.studentLName = studentLName;
    }

    @Basic
    @Column(name = "studentAge")
    public Integer getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(Integer studentAge) {
        this.studentAge = studentAge;
    }

    @Basic
    @Column(name = "studentDataOfBirth")
    public Date getStudentDataOfBirth() {
        return studentDataOfBirth;
    }

    public void setStudentDataOfBirth(Date studentDataOfBirth) {
        this.studentDataOfBirth = studentDataOfBirth;
    }

    @Basic
    @Column(name = "studentPhNo")
    public String getStudentPhNo() {
        return studentPhNo;
    }

    public void setStudentPhNo(String studentPhNo) {
        this.studentPhNo = studentPhNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity that = (StudentEntity) o;
        return studentId == that.studentId &&
                Objects.equals(studentFName, that.studentFName) &&
                Objects.equals(studentMName, that.studentMName) &&
                Objects.equals(studentLName, that.studentLName) &&
                Objects.equals(studentAge, that.studentAge) &&
                Objects.equals(studentDataOfBirth, that.studentDataOfBirth) &&
                Objects.equals(studentPhNo, that.studentPhNo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(studentId, studentFName, studentMName, studentLName, studentAge, studentDataOfBirth, studentPhNo);
    }

    @OneToMany(mappedBy = "studentByStudentId")
    public Collection<RegistrationEntity> getRegistrationsByStudentId() {
        return registrationsByStudentId;
    }

    public void setRegistrationsByStudentId(Collection<RegistrationEntity> registrationsByStudentId) {
        this.registrationsByStudentId = registrationsByStudentId;
    }
}
