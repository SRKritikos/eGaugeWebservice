/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.egauge.data.xml.instantaneous;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author srostantkritikos06
 */
@XmlRootElement(name = "ts")
public class TimeStamp {
    private long time;

    //Constructor
    public TimeStamp() {
    }
    
    @XmlValue
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    
    
}
