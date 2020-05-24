package com.varun.dbmodels;
/*
Created by : varun kalra
dbmodels class for course that will be used by database
 */
public class CourseDBModel {
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getcourseDesc() {
        return courseDesc;
    }

    public void setcourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public Double getCourseFees() {
        return courseFees;
    }

    public void setCourseFees(Double courseFees) {
        this.courseFees = courseFees;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    private String courseName;
    private String courseDesc;
    private Double courseFees;
    private int courseId;
}
