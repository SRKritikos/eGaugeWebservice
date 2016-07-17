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
public class Devices implements Serializable {
    List<Device> devices;
    String message;
    
    
    
    public Devices(List<Device_Entity> devices) { 
        this(devices, false);
    }
    
    public Devices(List<Device_Entity> devices, boolean withData) { 
        if (devices != null && !devices.isEmpty()) {
            this.devices = new ArrayList<>();
        //TODO: Make sure to return message if data is empty
            if (withData) {
                for (Device_Entity device_entity : devices) {
                    this.devices.add(new Device(device_entity, true));
                }
            } else {
                for (Device_Entity device_entity : devices) {
                    this.devices.add(new Device(device_entity));
                }
            }

        } else {
            this.message = "Error retrieving device data";
        }
    }
    
    public Devices(Map<Device_Entity, List<Data_Entity>> devices) {
        if (devices != null && !devices.isEmpty()) {
            this.devices = new  ArrayList<>();

            devices.forEach((device_entity, data_entities) ->
                   this.devices.add(new Device(device_entity, data_entities))
            );
        } else {
            this.message = "Error retrieving device data";
        }
    }
    
    
}
