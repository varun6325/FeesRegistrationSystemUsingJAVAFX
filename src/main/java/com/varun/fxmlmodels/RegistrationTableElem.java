package com.varun.fxmlmodels;

import java.util.Date;

public class RegistrationTableElem {
    private int registrationId;
    private String studentName;

    private String courseName;
    private Double courseFees;
    private Double registrationDiscount;
    private Double registrationAmount;
    private Double registrationAmountPaid;
    private Date registrationDate;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getRegistrationId() {

        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getCourseFees() {
        return courseFees;
    }

    public void setCourseFees(Double courseFees) {
        this.courseFees = courseFees;
    }

    public Double getRegistrationDiscount() {
        return registrationDiscount;
    }

    public void setRegistrationDiscount(Double registrationDiscount) {
        this.registrationDiscount = registrationDiscount;
    }

    public Double getRegistrationAmount() {
        return registrationAmount;
    }

    public void setRegistrationAmount(Double registrationAmount) {
        this.registrationAmount = registrationAmount;
    }

    public Double getRegistrationAmountPaid() {
        return registrationAmountPaid;
    }

    public void setRegistrationAmountPaid(Double registrationAmountPaid) {
        this.registrationAmountPaid = registrationAmountPaid;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
