/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.egauge.data.xml.instantaneous;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author srostantkritikos06
 */
@XmlRootElement(name = "r")
@XmlType(propOrder= {"value", "rate"})
public class Register {
    private String type;
    private String name;
    private Value value;
    private RateOfChange rate;

    
    // Constructor
    public Register() {
    }

    @XmlAttribute(name = "t", required = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute(name = "n", required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "v")
    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @XmlElement(name = "i")
    public RateOfChange getRate() {
        return rate;
    }

    public void setRate(RateOfChange rate) {
        this.rate = rate;
    }  
}
