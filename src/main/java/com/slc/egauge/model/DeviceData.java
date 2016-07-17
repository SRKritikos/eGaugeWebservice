/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.slc.egauge.model;

import com.slc.egauge.data.entities.Data_Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;


/**
 *
 * @author Steven Kritikos
 */
public class DeviceData implements Serializable {
    private BigDecimal totalPower;
    private BigDecimal instPower;
    private Date timeRecorded;
    
    public DeviceData(Data_Entity data) {
        if (data != null) {
            this.totalPower = data.getPower().abs().setScale(3, RoundingMode.CEILING) ;
            this.instPower = data.getInstPower().abs().setScale(3, RoundingMode.CEILING);
            this.timeRecorded = data.getTimeRecorded();
        } 
    }    
}
