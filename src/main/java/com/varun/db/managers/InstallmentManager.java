package com.varun.db.managers;

import com.varun.db.models.InstallmentEntity;
import com.varun.db.models.StudentEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class InstallmentManager {
    public static boolean addInstallment(InstallmentEntity installmentEntity){
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(installmentEntity);
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
    public static boolean removeInstallment(InstallmentEntity installmentEntity){
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(installmentEntity);
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
    public static InstallmentEntity getInstallmentById(int installmentId){
        InstallmentEntity res = null;
        EntityManager entityManager = null;
        try {
            entityManager =  PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            res = entityManager.find(InstallmentEntity.class, installmentId);
        }catch(Exception ex){
            System.out.println(ex);
            res = null;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return res;
    }

    public static int getMaxInstallmentNoForRegistration(int registrationId){
        EntityManager entityManager = null;
        int maxInstallmentNo;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            maxInstallmentNo = (int)entityManager.createQuery("Select max(i.installmentNo) from InstallmentEntity i where i.registrationByRegistrationId.registrationId = :param").setParameter("param", registrationId).getSingleResult();
        }catch(Exception ex){
            System.out.println(ex);
            maxInstallmentNo = -1;
        }finally {
            if(entityManager != null)
                entityManager.close();
        }
        return maxInstallmentNo;
    }
}
