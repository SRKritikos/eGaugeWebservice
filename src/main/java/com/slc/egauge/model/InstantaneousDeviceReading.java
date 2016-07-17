/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.slc.egauge.model;

import com.slc.egauge.data.entities.Data_Entity;
import com.slc.egauge.data.entities.Device_Entity;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Steven Kritikos
 */
public class InstantaneousDeviceReading implements Serializable {
    String deviceName;
    BigDecimal instPower;
    String message;

    public InstantaneousDeviceReading(Device_Entity device_entity, Data_Entity data) {
        if (device_entity != null && data != null ){
            this.deviceName = device_entity.getDeviceName();
            this.instPower = data.getInstPower();
        } else {
            this.message = "Error retrieving instantaneous data";
        }
        
    }
    
    
}
