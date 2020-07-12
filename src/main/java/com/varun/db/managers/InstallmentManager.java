package com.varun.db.managers;

import com.varun.db.models.InstallmentEntity;
import com.varun.db.models.StudentEntity;

import javax.persistence.EntityManager;

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
}
