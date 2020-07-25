package com.varun.db;

import com.varun.Utils;
import com.varun.db.managers.CourseManager;
import com.varun.db.managers.RegistrationManager;
import com.varun.db.models.CourseEntity;
import com.varun.db.models.InstallmentEntity;
import com.varun.db.models.RegistrationEntity;
import com.varun.db.models.StudentEntity;
import com.varun.fxmlmodels.CourseTableElem;
import com.varun.fxmlmodels.InstallmentTableElem;
import com.varun.fxmlmodels.RegistrationTableElem;
import com.varun.fxmlmodels.StudentTableElem;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.YEAR;

public class DBUtils {
    public static CourseTableElem getCourseTableElemFromCourseEntity(CourseEntity courseEntity){
        return new CourseTableElem(courseEntity.getCourseId(), courseEntity.getCourseName(), courseEntity.getCourseDesc(), courseEntity.getCourseFees().doubleValue());
    }
//    public static CourseEntity getCourseTableElemFromCourseEntity(CourseTableElem courseTableElem){
//        CourseEntity courseEntity = new CourseEntity();
//        courseEntity.setCourseName(courseTableElem.getCourseName());
//        courseEntity.setCourseDesc(courseTableElem.getCourseDesc());
//        courseEntity.setCourseFees(new BigDecimal(courseTableElem.getCourseFees()));
//        if(courseTableElem.getCourseId() != -1)
//            courseEntity.setCourseId(courseTableElem.getCourseId());
//        return courseEntity;
//
//    }

    public static StudentTableElem getStudentTableElemFromStudentEntity(StudentEntity studentEntity){
        String studentName = studentEntity.getStudentFName() + " " + studentEntity.getStudentMName() + " " + studentEntity.getStudentLName();
        Date currentDate = new Date();
        int studentAge= -1;
        if(studentEntity.getStudentDateOfBirth() != null)
            studentAge = Utils.getCalendar(currentDate).get(YEAR) - Utils.getCalendar(studentEntity.getStudentDateOfBirth()).get(YEAR);
        return new StudentTableElem(studentEntity.getStudentId(),  studentName, studentAge, studentEntity.getStudentPhNo(), studentEntity.getStudentEmail());
    }

    public static RegistrationTableElem getRegistrationTableElemFromRegistrationEntity(RegistrationEntity registrationEntity){
        CourseEntity courseEntity = CourseManager.getCourseById(registrationEntity.getCourseId());
        List<InstallmentEntity> installmentEntities = RegistrationManager.getInstallmentsForRegistration(registrationEntity);
        Double amountPaid = 0.0;
        if(installmentEntities != null){
            for(InstallmentEntity installmentEntity : installmentEntities){
                if(installmentEntity.isInstalmentIsDone())
                    amountPaid += installmentEntity.getIntallmentAmount().doubleValue();
            }
        }
        Double registrationAmount = courseEntity.getCourseFees().doubleValue() * (100.0-registrationEntity.getDiscount().doubleValue()) / 100.0;
        RegistrationTableElem registrationTableElem = new RegistrationTableElem();
        registrationTableElem.setRegistrationId(registrationEntity.getRegistrationId());
        registrationTableElem.setCourseName(courseEntity.getCourseName());
        registrationTableElem.setCourseFees(courseEntity.getCourseFees().doubleValue());
        registrationTableElem.setRegistrationAmount(registrationAmount);
        registrationTableElem.setRegistrationDiscount(registrationEntity.getDiscount().doubleValue());
        registrationTableElem.setRegistrationAmountPaid(amountPaid);
        registrationTableElem.setRegistrationDate(registrationEntity.getRegistrationDate());
        return registrationTableElem;
    }

    public static InstallmentTableElem getInstallmentTableElemFromInstallmentEntity(InstallmentEntity installmentEntity){
        return new InstallmentTableElem(installmentEntity.getInstallmentId(), installmentEntity.getInstallmentNo(), installmentEntity.getIntallmentAmount().doubleValue(), installmentEntity.isInstalmentIsDone(), installmentEntity.getInstallmentDueDate());
    }
}
