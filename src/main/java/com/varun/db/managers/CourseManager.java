package com.varun.db.managers;

import com.varun.db.models.CourseEntity;
import com.varun.fxmlmodels.CourseTableElem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CourseManager {
    public static List<CourseEntity> getCourseDBList(){
        List<CourseEntity> courseEntities = null;
        EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            courseEntities = entityManager.createQuery("Select a from CourseEntity a", CourseEntity.class).getResultList();
        }catch(Exception ex){
            courseEntities = null;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        System.out.println(courseEntities.size() + " " + courseEntities.get(0));
        return courseEntities;
    }
    public static ObservableList<CourseTableElem> getCourseTableElemList() {
        ObservableList<CourseTableElem> courses = null;
        List<CourseEntity> courseEntities = CourseManager.getCourseDBList();
        if(courseEntities != null){
            courses = FXCollections.observableArrayList();
            for(CourseEntity courseEntity : courseEntities){
                CourseTableElem courseTableElem = new CourseTableElem(courseEntity.getCourseId(), courseEntity.getCourseName(), courseEntity.getCourseDesc(), courseEntity.getCourseFees().doubleValue());
                courses.add(courseTableElem);
            }
        }
        return courses;
    }
}
