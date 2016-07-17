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
import java.util.Map;

/**
 *
 * @author Steven Kritikos
 */ 
public class InstantaneousDeviceReadings implements Serializable {
    List<InstantaneousDeviceReading> devices;
    String message;

    public InstantaneousDeviceReadings(Map<Device_Entity, Data_Entity> devices) {
       
        if (devices != null && !devices.isEmpty()) {
            this.devices = new ArrayList<>();

            devices.forEach((device_entity, data_entities) ->
                   this.devices.add(new InstantaneousDeviceReading(device_entity, data_entities))
            );
        } else {
            this.message = "Error retrieving instantaneous data";
        }
    } 
}
