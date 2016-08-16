/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.slc.egauge.service;

import com.slc.egauge.data.dao.DataDAO;
import com.slc.egauge.data.dao.DeviceDAO;
import com.slc.egauge.data.entities.Data_Entity;
import com.slc.egauge.data.entities.Device_Entity;
import com.slc.egauge.model.Device;
import com.slc.egauge.model.Devices;
import com.slc.egauge.model.InstantaneousDeviceReadings;
import com.slc.egauge.utils.DatabaseUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 * Service for retrieving device and device data
 * 
 * @author Steven Kritikos
 */
public class DeviceService {
    private EntityManagerFactory emf;
    
    private DataDAO datadao;
    private DeviceDAO devicedao;
    
    public DeviceService() {
        emf = DatabaseUtils.getEntityManager();
        this.datadao = new DataDAO(emf);
        this.devicedao = new DeviceDAO(emf);
    }
    
    public Device getDeviceByNameAndDate(String deviceName, Date startDate, Date endDate) {
        Device_Entity device = devicedao.getDeviceByName(deviceName);
        Device rtVl = new Device(device, datadao.getDataForDeviceByDate(device, startDate, endDate));
        return rtVl;
    }
    
    public Devices getDevicesByNameAndDate(List<String> deviceNames, Date startDate, Date endDate) {
        // For each device name map a list of device entities using dao for query 
        // Collect the returned Stream of device entities into a map of <Device_Entity, List<Data_Entity>>
        Map<Device_Entity, List<Data_Entity>> mapDevices = deviceNames.stream().map(deviceName -> 
            devicedao.getDeviceByName(deviceName)).collect(Collectors.toMap(device -> device,
                device -> datadao.getDataForDeviceByDate(device, startDate, endDate)) 
        );
        
        return new Devices(mapDevices);
    }
    
    public InstantaneousDeviceReadings getInstReadings(List<String> deviceNames) {
        Map<Device_Entity, Data_Entity> mapDevices = deviceNames.stream().map(deviceName ->
            devicedao.getDeviceByName(deviceName)).collect(Collectors.toMap(device -> device, 
                device -> datadao.getMostRecentEntryForDevice(device))
        );
        
        return new InstantaneousDeviceReadings(mapDevices);
    }

    public Device getDeviceByName(String campus) {
        Device rtVl = new Device(devicedao.getDeviceByName(campus)); 
        return rtVl;
    }
   
    
}
