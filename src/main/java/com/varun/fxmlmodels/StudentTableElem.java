package com.varun.fxmlmodels;

public class StudentTableElem {
    int studentId;
    String studentName;
    int studentAge;
    String studentEmail;
    String studentPhNo;

    public String getStudentPhNo() {
        return studentPhNo;
    }

    public void setStudentPhNo(String studentPhNo) {
        this.studentPhNo = studentPhNo;
    }

    public int getStudentId() {
        return studentId;
    }

    public StudentTableElem(int studentId, String studentName, int studentAge, String studentPhNo, String studentEmail) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentAge = studentAge;
        this.studentPhNo = studentPhNo;
        this.studentEmail = studentEmail;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    @Override
    public String toString() {
        return "StudentTableElem{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentAge=" + studentAge +
                ", studentAddress='" + studentEmail + '\'' +
                ", studentPhNo='" + studentPhNo + '\'' +
                '}';
    }
}
