package com.varun.db.managers;

import com.varun.controller.StudentDetailsSceneController;
import com.varun.db.models.StudentEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class StudentManager {
public static List<StudentEntity> getStudentDBList(){
        List<StudentEntity> studentEntities = null;
        EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            studentEntities = entityManager.createQuery("Select a from StudentEntity a", StudentEntity.class).getResultList();
        }catch(Exception ex){
            System.out.println(ex);
            studentEntities = null;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        System.out.println(studentEntities.size() + " " + studentEntities.get(0));
        return studentEntities;
    }
//    public static ObservableList<CourseTableElem> getCourseTableElemList() {
//        ObservableList<CourseTableElem> courses = null;
//        List<CourseEntity> courseEntities = CourseManager.getCourseDBList();
//        if(courseEntities != null){
//            courses = FXCollections.observableArrayList();
//            for(CourseEntity courseEntity : courseEntities){
//                CourseTableElem courseTableElem = new CourseTableElem(courseEntity.getCourseId(), courseEntity.getCourseName(), courseEntity.getCourseDesc(), courseEntity.getCourseFees().doubleValue());
//                courses.add(courseTableElem);
//            }
//        }
//        return courses;
//    }
    public static boolean addStudent(StudentEntity studentEntity){
        EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            entityManager.persist(studentEntity);
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return true;
    }
    public static StudentEntity updateStudent(StudentEntity studentEntity){
        StudentEntity res = null;
        EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            res = entityManager.merge(studentEntity);
        }catch(Exception ex){
            System.out.println(ex);
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return res;
    }
    public static StudentEntity getCourseById(int id){
        StudentEntity res = null;
        EntityManager entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            res = entityManager.find(StudentEntity.class, id);
        }catch(Exception ex){
            System.out.println(ex);
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return res;
    }
}
