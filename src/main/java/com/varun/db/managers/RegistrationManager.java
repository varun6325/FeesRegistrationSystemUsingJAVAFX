package com.varun.db.managers;

import com.varun.db.models.InstallmentEntity;
import com.varun.db.models.RegistrationEntity;
import com.varun.db.models.StudentEntity;
import org.hibernate.LazyInitializationException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class RegistrationManager {

    public static boolean addRegistration(RegistrationEntity registrationEntity){
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(registrationEntity);
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
    public static RegistrationEntity updateRegistration(RegistrationEntity registrationEntity){
        RegistrationEntity res = null;
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            res = entityManager.merge(registrationEntity);
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

    public static int deleteRegistrationById(int id){
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            RegistrationEntity res = entityManager.find(RegistrationEntity.class, id);
            entityManager.remove(res);
            entityManager.getTransaction().commit();
            System.out.println("Registration deleted with respect to registration id : " + id);
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
    public static RegistrationEntity getRegistrationWithInstallationsFromEntitiy(RegistrationEntity registrationEntity){
        if(registrationEntity.getInstallmentsByRegistrationId() != null) {
            try {
                registrationEntity.getInstallmentsByRegistrationId().size();
                return registrationEntity;// we already have the installments with respect to this registration, so no need to do it again
            } catch (LazyInitializationException ex) {
                // installments need to be captured from the db
                System.out.println(ex);
            }
        }
        EntityManager entityManager =null;
        try {
            List<InstallmentEntity> installmentEntities;
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            registrationEntity = entityManager.merge(registrationEntity);
            installmentEntities = (List)registrationEntity.getInstallmentsByRegistrationId();
            for(int i = 0; i < installmentEntities.size(); i++)
                installmentEntities.get(i);
        }catch(Exception ex){
            System.out.println(ex);
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return registrationEntity;
    }

    public static RegistrationEntity getRegistrationWithStudentInfoFromEntitiy(RegistrationEntity registrationEntity){
        try{
            registrationEntity.getStudentByStudentId().getStudentFName();
            return registrationEntity;// we already have the installments with respect to this registration, so no need to do it again
        }catch(LazyInitializationException ex){
            // installments need to be captured from the db
            System.out.println(ex);
        }
        EntityManager entityManager =null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            registrationEntity = entityManager.merge(registrationEntity);
            registrationEntity.getStudentByStudentId().getStudentFName();
        }catch(Exception ex){
            System.out.println(ex);
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return registrationEntity;
    }
    public static RegistrationEntity getRegistrationById(int registrationId){
        RegistrationEntity res = null;
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            res = entityManager.find(RegistrationEntity.class, registrationId);
        }catch(Exception ex){
            System.out.println(ex);
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return res;
    }

    public static List<RegistrationEntity> getRegistrationsHavingInstallmentDueDateBetween(Date since, Date upto, boolean alreadyPaid){
        List<RegistrationEntity> registrationEntities = null;
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            TypedQuery<RegistrationEntity> registrationEntityTypedQuery = entityManager.createQuery("Select i.registrationByRegistrationId from InstallmentEntity i " +
                   "where i.installmentDueDate BETWEEN :since and :upto and i.instalmentIsDone = :alreadyPaid", RegistrationEntity.class);
            registrationEntityTypedQuery.setParameter("since", since, TemporalType.DATE).setParameter("upto", upto, TemporalType.DATE).setParameter("alreadyPaid", alreadyPaid);
            registrationEntities = registrationEntityTypedQuery.getResultList();
        }catch(Exception ex){
            System.out.println(ex);
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return registrationEntities;
    }
}
