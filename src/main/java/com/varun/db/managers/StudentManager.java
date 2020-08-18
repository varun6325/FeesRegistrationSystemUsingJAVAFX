package com.varun.db.managers;

import com.varun.db.DBUtils;
import com.varun.db.models.RegistrationEntity;
import com.varun.db.models.StudentEntity;
import com.varun.fxmlmodels.StudentTableElem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.LazyInitializationException;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import java.util.List;

public class StudentManager {
    public static List<StudentEntity> getStudentDBList(){
        List<StudentEntity> studentEntities;
        EntityManager entityManager = null;
        try {
            entityManager  = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            studentEntities = entityManager.createQuery("Select a from StudentEntity a", StudentEntity.class).getResultList();
        }catch(Exception ex){
            System.out.println(ex);
            studentEntities = null;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        System.out.println(studentEntities.size());
        return studentEntities;
    }


    public static ObservableList<StudentTableElem> getStudentTableElemList() {
        ObservableList<StudentTableElem> students = null;
        List<StudentEntity> studentEntities = StudentManager.getStudentDBList();
        if(studentEntities != null){
            students = FXCollections.observableArrayList();
            for(StudentEntity studentEntity : studentEntities){
                //System.out.println(studentEntities.toString());
                StudentTableElem studentTableElem = DBUtils.getStudentTableElemFromStudentEntity(studentEntity);
                System.out.println(studentTableElem.toString());
                students.add(studentTableElem);
            }
        }
        return students;
    }
    public static boolean addStudent(StudentEntity studentEntity){
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(studentEntity);
            entityManager.getTransaction().commit();
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
    public static StudentEntity updateStudent(StudentEntity studentEntity){
        StudentEntity res = null;
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            res = entityManager.merge(studentEntity);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            System.out.println(ex);
            if(entityManager != null && entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return res;
    }
    public static StudentEntity getStudentWithoutRegistrationsFromId(int id){
        StudentEntity res = null;
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            res = entityManager.find(StudentEntity.class, id);
        }catch(Exception ex){
            System.out.println(ex);
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return res;
    }
    //get registration details for student. If we have already captured the details before then return those only.
    public static StudentEntity getStudentWithRegistrationsFromEntity(StudentEntity studentEntity){
        if(studentEntity.getRegistrationsByStudentId() != null) {
            try {
                studentEntity.getRegistrationsByStudentId().size();
                return studentEntity;// we already have the registrations with respect to this student, so no need to do it again
            } catch (LazyInitializationException ex) {
                System.out.println(ex);
                // registrations need to be captured from the db
            }
        }
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            PersistenceUnitUtil unitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
            studentEntity = entityManager.merge(studentEntity);
            List<RegistrationEntity> registrationEntities = (List)studentEntity.getRegistrationsByStudentId();
            for(int i = 0; i < registrationEntities.size(); i++)
                registrationEntities.get(i);
        }catch(Exception ex){
            System.out.println(ex);
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return studentEntity;
    }

    public static StudentEntity getStudentWithRegistrationsFromId(int id){
        StudentEntity res = StudentManager.getStudentWithoutRegistrationsFromId(id);
        if(res == null)
            return res;
        res = StudentManager.getStudentWithRegistrationsFromEntity(res);
        return res;
    }
}

