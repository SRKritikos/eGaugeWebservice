/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.egauge.utils;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author srostantkritikos06
 */
public class DatabaseUtils {
    private static EntityManagerFactory emf;
    public static EntityManagerFactory getEntityManager() {
        
        Map<String, String> persistenceMap = new HashMap<String, String>();
        persistenceMap.put("javax.persistence.jdbc.user", "root");
        persistenceMap.put("javax.persistence.jdbc.password", "mysql");
        
        emf = Persistence.createEntityManagerFactory("com.slc_eGaugeWebService_war_1.2-SNAPSHOTPU", persistenceMap);
        return emf;
    }
}
