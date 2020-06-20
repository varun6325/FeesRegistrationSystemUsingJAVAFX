package com.varun.db;

import com.varun.db.models.CourseEntity;
import com.varun.db.models.StudentEntity;
import com.varun.fxmlmodels.CourseTableElem;
import com.varun.fxmlmodels.StudentTableElem;

import java.math.BigDecimal;

public class DBUtils {
    public static CourseTableElem getCourseTableElemFromCourseEntity(CourseEntity courseEntity){
        return new CourseTableElem(courseEntity.getCourseId(), courseEntity.getCourseName(), courseEntity.getCourseDesc(), courseEntity.getCourseFees().doubleValue());
    }
    public static CourseEntity getCourseTableElemFromCourseEntity(CourseTableElem courseTableElem){
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseName(courseTableElem.getCourseName());
        courseEntity.setCourseDesc(courseTableElem.getCourseDesc());
        courseEntity.setCourseFees(new BigDecimal(courseTableElem.getCourseFees()));
        if(courseTableElem.getCourseId() != -1)
            courseEntity.setCourseId(courseTableElem.getCourseId());
        return courseEntity;

    }
}
