package com.varun.db.managers;

import com.varun.db.DBUtils;
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
            System.out.println(ex);
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
                courses.add(DBUtils.getCourseTableElemFromCourseEntity(courseEntity));
            }
        }
        return courses;
    }
    public static boolean addCourse(CourseEntity courseEntity){
        EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(courseEntity);
            entityManager.getTransaction().commit();
            System.out.println("Adding course successful : " + courseEntity.toString());
        }catch(Exception ex){
            System.out.println(ex);
            if(entityManager != null && entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
                return false;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return true;
    }
    public static CourseEntity updateCourse(CourseEntity courseEntity){
        CourseEntity res;
        EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            res = entityManager.merge(courseEntity);
            entityManager.getTransaction().commit();
            System.out.println("Updating course successful : " + courseEntity.toString());
        }catch(Exception ex){
            System.out.println(ex);
            res=null;
            if(entityManager != null && entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return res;
    }
    public static CourseEntity getCourseById(int id){
        CourseEntity res = null;
        EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            res = entityManager.find(CourseEntity.class, id);
        }catch(Exception ex){
            System.out.println(ex);
            res = null;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return res;
    }
}
