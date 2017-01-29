/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.slc.egauge.data.dao;

import com.slc.egauge.data.dao.exceptions.NonexistentEntityException;
import com.slc.egauge.data.dao.exceptions.PreexistingEntityException;
import com.slc.egauge.data.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.slc.egauge.data.entities.Data_Entity;
import com.slc.egauge.data.entities.Device_Entity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.UserTransaction;

/**
 *
 * @author Steven Kritikos
 */
public class DeviceDAO implements Serializable {

       public DeviceDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Device_Entity device_Entity) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (device_Entity.getDataEntityList() == null) {
            device_Entity.setDataEntityList(new ArrayList<Data_Entity>());
        }
        EntityTransaction utx = this.getEntityManager().getTransaction();
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Data_Entity> attachedDataEntityList = new ArrayList<Data_Entity>();
            for (Data_Entity dataEntityListData_EntityToAttach : device_Entity.getDataEntityList()) {
                dataEntityListData_EntityToAttach = em.getReference(dataEntityListData_EntityToAttach.getClass(), dataEntityListData_EntityToAttach.getDataId());
                attachedDataEntityList.add(dataEntityListData_EntityToAttach);
            }
            device_Entity.setDataEntityList(attachedDataEntityList);
            em.persist(device_Entity);
            for (Data_Entity dataEntityListData_Entity : device_Entity.getDataEntityList()) {
                Device_Entity oldDeviceIdOfDataEntityListData_Entity = dataEntityListData_Entity.getDeviceId();
                dataEntityListData_Entity.setDeviceId(device_Entity);
                dataEntityListData_Entity = em.merge(dataEntityListData_Entity);
                if (oldDeviceIdOfDataEntityListData_Entity != null) {
                    oldDeviceIdOfDataEntityListData_Entity.getDataEntityList().remove(dataEntityListData_Entity);
                    oldDeviceIdOfDataEntityListData_Entity = em.merge(oldDeviceIdOfDataEntityListData_Entity);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findDevice_Entity(device_Entity.getDeviceId()) != null) {
                throw new PreexistingEntityException("Device_Entity " + device_Entity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Device_Entity device_Entity) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityTransaction utx = this.getEntityManager().getTransaction();
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Device_Entity persistentDevice_Entity = em.find(Device_Entity.class, device_Entity.getDeviceId());
            List<Data_Entity> dataEntityListOld = persistentDevice_Entity.getDataEntityList();
            List<Data_Entity> dataEntityListNew = device_Entity.getDataEntityList();
            List<Data_Entity> attachedDataEntityListNew = new ArrayList<Data_Entity>();
            for (Data_Entity dataEntityListNewData_EntityToAttach : dataEntityListNew) {
                dataEntityListNewData_EntityToAttach = em.getReference(dataEntityListNewData_EntityToAttach.getClass(), dataEntityListNewData_EntityToAttach.getDataId());
                attachedDataEntityListNew.add(dataEntityListNewData_EntityToAttach);
            }
            dataEntityListNew = attachedDataEntityListNew;
            device_Entity.setDataEntityList(dataEntityListNew);
            device_Entity = em.merge(device_Entity);
            for (Data_Entity dataEntityListOldData_Entity : dataEntityListOld) {
                if (!dataEntityListNew.contains(dataEntityListOldData_Entity)) {
                    dataEntityListOldData_Entity.setDeviceId(null);
                    dataEntityListOldData_Entity = em.merge(dataEntityListOldData_Entity);
                }
            }
            for (Data_Entity dataEntityListNewData_Entity : dataEntityListNew) {
                if (!dataEntityListOld.contains(dataEntityListNewData_Entity)) {
                    Device_Entity oldDeviceIdOfDataEntityListNewData_Entity = dataEntityListNewData_Entity.getDeviceId();
                    dataEntityListNewData_Entity.setDeviceId(device_Entity);
                    dataEntityListNewData_Entity = em.merge(dataEntityListNewData_Entity);
                    if (oldDeviceIdOfDataEntityListNewData_Entity != null && !oldDeviceIdOfDataEntityListNewData_Entity.equals(device_Entity)) {
                        oldDeviceIdOfDataEntityListNewData_Entity.getDataEntityList().remove(dataEntityListNewData_Entity);
                        oldDeviceIdOfDataEntityListNewData_Entity = em.merge(oldDeviceIdOfDataEntityListNewData_Entity);
                    }
                }
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
                String id = device_Entity.getDeviceId();
                if (findDevice_Entity(id) == null) {
                    throw new NonexistentEntityException("The device_Entity with id " + id + " no longer exists.");
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
        EntityTransaction utx = this.getEntityManager().getTransaction();
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Device_Entity device_Entity;
            try {
                device_Entity = em.getReference(Device_Entity.class, id);
                device_Entity.getDeviceId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The device_Entity with id " + id + " no longer exists.", enfe);
            }
            List<Data_Entity> dataEntityList = device_Entity.getDataEntityList();
            for (Data_Entity dataEntityListData_Entity : dataEntityList) {
                dataEntityListData_Entity.setDeviceId(null);
                dataEntityListData_Entity = em.merge(dataEntityListData_Entity);
            }
            em.remove(device_Entity);
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

    public List<Device_Entity> findDevice_EntityEntities() {
        return findDevice_EntityEntities(true, -1, -1);
    }

    public List<Device_Entity> findDevice_EntityEntities(int maxResults, int firstResult) {
        return findDevice_EntityEntities(false, maxResults, firstResult);
    }

    private List<Device_Entity> findDevice_EntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Device_Entity.class));
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

    public Device_Entity findDevice_Entity(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Device_Entity.class, id);
        } finally {
            em.close();
        }
    }

    public int getDevice_EntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Device_Entity> rt = cq.from(Device_Entity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Device_Entity getDeviceByName(String name) {
        Device_Entity rtVl = null;
        EntityManager em = getEntityManager();
        try {
            rtVl = em.createNamedQuery("Device_Entity.findByDeviceName", Device_Entity.class)
                    .setParameter("deviceName", name)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }   finally {
            em.close();
        }
        
        return rtVl;
    }
}
