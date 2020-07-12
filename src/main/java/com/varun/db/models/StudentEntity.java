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
    private String studentPhNo;

    private Collection<RegistrationEntity> registrationsByStudentId;
    private Date studentDateOfBirth;
    private String studentAddress;
    private String studentEmail;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @Column(name = "studentPhNo")
    public String getStudentPhNo() {
        return studentPhNo;
    }

    public void setStudentPhNo(String studentPhNo) {
        this.studentPhNo = studentPhNo;
    }

    @Basic
    @Column(name = "studentEmail")
    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
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
                Objects.equals(studentPhNo, that.studentPhNo) &&
                Objects.equals(registrationsByStudentId, that.registrationsByStudentId) &&
                Objects.equals(studentDateOfBirth, that.studentDateOfBirth) &&
                Objects.equals(studentEmail, that.studentEmail) &&
                Objects.equals(studentAddress, that.studentAddress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(studentId, studentFName, studentMName, studentLName, studentPhNo, registrationsByStudentId, studentDateOfBirth, studentEmail, studentAddress);
    }

    @OneToMany(mappedBy = "studentByStudentId")
    public Collection<RegistrationEntity> getRegistrationsByStudentId() {
        return registrationsByStudentId;
    }

    public void setRegistrationsByStudentId(Collection<RegistrationEntity> registrationsByStudentId) {
        this.registrationsByStudentId = registrationsByStudentId;
    }

    @Basic
    @Column(name = "studentDateOfBirth")
    public Date getStudentDateOfBirth() {
        return studentDateOfBirth;
    }

    public void setStudentDateOfBirth(Date studentDateOfBirth) {
        this.studentDateOfBirth = studentDateOfBirth;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "studentId=" + studentId +
                ", studentFName='" + studentFName + '\'' +
                ", studentMName='" + studentMName + '\'' +
                ", studentLName='" + studentLName + '\'' +
                ", studentPhNo='" + studentPhNo + '\'' +
                ", studentDateOfBirth=" + studentDateOfBirth +
                ", studentAddress='" + studentAddress + '\'' +
                ", studentAddress='" + studentEmail + '\'' +
                '}';
    }

    @Basic
    @Column(name = "studentAddress")
    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }
}
