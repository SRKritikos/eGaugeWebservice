/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.slc.egauge.data.dao;

import com.slc.egauge.data.dao.exceptions.NonexistentEntityException;
import com.slc.egauge.data.dao.exceptions.PreexistingEntityException;
import com.slc.egauge.data.dao.exceptions.RollbackFailureException;
import com.slc.egauge.data.entities.Data_Entity;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.slc.egauge.data.entities.Device_Entity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.UserTransaction;

/**
 *
 * @author Steven Kritikos

 */
public class DataDAO implements Serializable {

    public DataDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Data_Entity data_Entity) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityTransaction utx = this.getEntityManager().getTransaction();
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Device_Entity deviceId = data_Entity.getDeviceId();
            if (deviceId != null) {
                deviceId = em.getReference(deviceId.getClass(), deviceId.getDeviceId());
                data_Entity.setDeviceId(deviceId);
            }
            em.persist(data_Entity);
            if (deviceId != null) {
                deviceId.getDataEntityList().add(data_Entity);
                deviceId = em.merge(deviceId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findData_Entity(data_Entity.getDataId()) != null) {
                throw new PreexistingEntityException("Data_Entity " + data_Entity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Data_Entity data_Entity) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityTransaction utx = this.getEntityManager().getTransaction();
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Data_Entity persistentData_Entity = em.find(Data_Entity.class, data_Entity.getDataId());
            Device_Entity deviceIdOld = persistentData_Entity.getDeviceId();
            Device_Entity deviceIdNew = data_Entity.getDeviceId();
            if (deviceIdNew != null) {
                deviceIdNew = em.getReference(deviceIdNew.getClass(), deviceIdNew.getDeviceId());
                data_Entity.setDeviceId(deviceIdNew);
            }
            data_Entity = em.merge(data_Entity);
            if (deviceIdOld != null && !deviceIdOld.equals(deviceIdNew)) {
                deviceIdOld.getDataEntityList().remove(data_Entity);
                deviceIdOld = em.merge(deviceIdOld);
            }
            if (deviceIdNew != null && !deviceIdNew.equals(deviceIdOld)) {
                deviceIdNew.getDataEntityList().add(data_Entity);
                deviceIdNew = em.merge(deviceIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = data_Entity.getDataId();
                if (findData_Entity(id) == null) {
                    throw new NonexistentEntityException("The data_Entity with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction utx = this.getEntityManager().getTransaction();
        try {
            utx.begin();
            em = getEntityManager();
            Data_Entity data_Entity;
            try {
                data_Entity = em.getReference(Data_Entity.class, id);
                data_Entity.getDataId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The data_Entity with id " + id + " no longer exists.", enfe);
            }
            Device_Entity deviceId = data_Entity.getDeviceId();
            if (deviceId != null) {
                deviceId.getDataEntityList().remove(data_Entity);
                deviceId = em.merge(deviceId);
            }
            em.remove(data_Entity);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Data_Entity> findData_EntityEntities() {
        return findData_EntityEntities(true, -1, -1);
    }

    public List<Data_Entity> findData_EntityEntities(int maxResults, int firstResult) {
        return findData_EntityEntities(false, maxResults, firstResult);
    }

    private List<Data_Entity> findData_EntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Data_Entity.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Data_Entity findData_Entity(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Data_Entity.class, id);
        } finally {
            em.close();
        }
    }

    public int getData_EntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Data_Entity> rt = cq.from(Data_Entity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Data_Entity> getDataForDeviceByDate(Device_Entity device, Date startDate, Date endDate ) {
        List<Data_Entity> rtVl = null;
        EntityManager em = getEntityManager();
        try {
            rtVl = em.createQuery("SELECT d FROM Data_Entity d WHERE d.deviceId = :device AND d.timeRecorded BETWEEN :startDate AND :endDate ORDER BY d.timeRecorded DESC")
                    .setParameter("device", device)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } catch (Exception e) {
          e.printStackTrace();
            System.out.println(e.toString());
        }
        return rtVl;
    }
     
    public Data_Entity getMostRecentEntryForDevice(Device_Entity device) {
        Data_Entity rtVl = null;
        
        try {
            EntityManager em = getEntityManager();
            rtVl = em.createQuery("SELECT d FROM Data_Entity d WHERE d.deviceId = :device ORDER BY d.timeRecorded DESC", Data_Entity.class)
                    .setParameter("device", device)
                    .setMaxResults(1)
                    .getSingleResult();
            
        } catch (Exception e ) {
            System.out.println(e.toString());
        }
        return rtVl;
    }
    
    
    public boolean insertDataIntoTable(Device_Entity device, BigDecimal power, Date date, BigDecimal instPower) {
        boolean result = false;
        EntityManager em = this.getEntityManager();;
        try {            
            em.getTransaction().begin();
            
            Data_Entity entity = new Data_Entity();
            entity.setDataId(UUID.randomUUID().toString());
            entity.setDeviceId(device);
            entity.setPower(power);
            entity.setTimeRecorded(date);
            entity.setInstPower(instPower);
            em.persist(entity);
            em.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } finally {
            
            em.close();
        }
        return result;
    }

}
