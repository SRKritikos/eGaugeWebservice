/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.slc.egauge.model;

import com.slc.egauge.data.entities.Data_Entity;
import com.slc.egauge.data.entities.Device_Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Steven Kritikos
 */
public class Device implements Serializable {
    private String deviceId;
    private String deviceName;
    private List<DeviceData> deviceData;
    String message;

    public Device(Device_Entity device) {
        this(device, false);
    }
    
    public Device(Device_Entity device, boolean withData) {
        
        if (device != null) {
            this.deviceId = device.getDeviceId();
            this.deviceName = device.getDeviceName();
            

            if (withData) {
                this.deviceData = new ArrayList<>();
                for (Data_Entity data_entity : device.getDataEntityList())
                {
                    DeviceData tempData = new DeviceData(data_entity);
                    deviceData.add(tempData);
                }
            }
        } else {
            this.message = "Error retrieving device data";
        }
    }
    public Device(Device_Entity device, List<Data_Entity> data) {
        
        if (device != null && (data != null || !data.isEmpty())) {
            this.deviceId = device.getDeviceId();
            this.deviceName = device.getDeviceName();
            this.deviceData = new ArrayList<>();

            for(Data_Entity data_entity : data) {
                this.deviceData.add(new DeviceData(data_entity));
            }
        } else {
            this.message = "Error retrieving device data";
        }
    }
    
    
}
