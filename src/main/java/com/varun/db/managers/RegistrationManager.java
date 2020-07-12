package com.varun.db.managers;

import com.varun.db.models.InstallmentEntity;
import com.varun.db.models.RegistrationEntity;
import com.varun.db.models.StudentEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
    public static List<InstallmentEntity> getInstallmentsForRegistration(RegistrationEntity registrationEntity){
        List<InstallmentEntity> installmentEntities = null;
        EntityManager entityManager =null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            entityManager.merge(registrationEntity);
            installmentEntities = (List)registrationEntity.getInstallmentsByRegistrationId();
            for(int i = 0; i < installmentEntities.size(); i++)
                installmentEntities.get(i);
        }catch(Exception ex){
            System.out.println(ex);
            installmentEntities = null;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return installmentEntities;
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
}
