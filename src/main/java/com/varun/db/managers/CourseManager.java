package com.varun.db.managers;

import com.varun.db.DBUtils;
import com.varun.db.models.CourseEntity;
import com.varun.fxmlmodels.CourseTableElem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import javax.naming.ContextNotEmptyException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class CourseManager {
    public static List<CourseEntity> getCourseDBList(){
        List<CourseEntity> courseEntities = null;
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            courseEntities = entityManager.createQuery("Select a from CourseEntity a", CourseEntity.class).getResultList();
        }catch(Exception ex){
            System.out.println(ex);
            courseEntities = null;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        System.out.println(courseEntities.size());
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
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(courseEntity);
            entityManager.getTransaction().commit();
            System.out.println("Adding course successful : " + courseEntity.toString());
        }catch(Exception ex){
            if(entityManager != null && entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
                return false;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return true;
    }
    /*
    delete course by its id
    return values :
    0 : unsuccessful with constraint violation exception - because of foreign key constraint - because registration already exists for this course
    1 : successfuly
    -1 : unsuccessful with some other error
     */
    public static int deleteCourseById(int id){
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE FROM CourseEntity c WHERE c.courseId = :id");
            int delCount = query.setParameter("id", id).executeUpdate();
            entityManager.getTransaction().commit();
            System.out.println(delCount + " no of courses deleted with respect to course id : " + id);
            return 1;
        }catch(Exception ex){
            if(entityManager != null && entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            Throwable t = ex.getCause();
            while(t!= null && !(t instanceof SQLIntegrityConstraintViolationException))
                t = t.getCause();
            if(t instanceof  SQLIntegrityConstraintViolationException)
                return 0;
            return -1;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
    }
    public static CourseEntity updateCourse(CourseEntity courseEntity){
        CourseEntity res;
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
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
        EntityManager entityManager = null;
        try {
            entityManager =  PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
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

    public static boolean checkCourseExists(String courseName){
        List<CourseEntity> courseEntities = null;
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            TypedQuery<Long> query = entityManager.createQuery("Select count(*) from CourseEntity a where a.courseName like :courseName", Long.class);
            query.setParameter("courseName", courseName);
            Long out = query.getSingleResult();
            if(out == 0)
                return false;

        }catch(Exception ex){
            System.out.println(ex);
            courseEntities = null;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return true;
    }
}
