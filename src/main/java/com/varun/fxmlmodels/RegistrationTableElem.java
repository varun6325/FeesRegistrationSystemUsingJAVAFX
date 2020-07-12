package com.varun.fxmlmodels;

import java.util.Date;

public class RegistrationTableElem {
    private int registrationId;
    private String courseName;
    private Double courseFees;
    private Double registrationDiscount;
    private Double registrationAmount;
    private Double registrationAmountPaid;
    private Date registrationDate;

    public RegistrationTableElem(int registrationId, String courseName, Double courseFees, Double registrationDiscount, Double registrationAmount, Double registrationAmountPaid, Date registrationDate) {
        this.registrationId = registrationId;
        this.courseName = courseName;
        this.courseFees = courseFees;
        this.registrationDiscount = registrationDiscount;
        this.registrationAmount = registrationAmount;
        this.registrationAmountPaid = registrationAmountPaid;
        this.registrationDate = registrationDate;
    }

    public RegistrationTableElem() { }

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
