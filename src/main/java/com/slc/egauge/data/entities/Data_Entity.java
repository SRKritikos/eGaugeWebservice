/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.slc.egauge.data.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Steven Kritikos
 */
@Entity
@Table(name = "data")
@NamedQueries({
    @NamedQuery(name = "Data_Entity.findAll", query = "SELECT d FROM Data_Entity d"),
    @NamedQuery(name = "Data_Entity.findByDataId", query = "SELECT d FROM Data_Entity d WHERE d.dataId = :dataId"),
    @NamedQuery(name = "Data_Entity.findByTimeRecorded", query = "SELECT d FROM Data_Entity d WHERE d.timeRecorded = :timeRecorded"),
    @NamedQuery(name = "Data_Entity.findByPower", query = "SELECT d FROM Data_Entity d WHERE d.power = :power"),
    @NamedQuery(name = "Data_Entity.findByInstPower", query = "SELECT d FROM Data_Entity d WHERE d.instPower = :instPower")})
public class Data_Entity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "dataId")
    private String dataId;
    @Column(name = "timeRecorded")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeRecorded;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "power")
    private BigDecimal power;
    @Column(name = "instPower")
    private BigDecimal instPower;
    @JoinColumn(name = "deviceId", referencedColumnName = "deviceId")
    @ManyToOne
    private Device_Entity deviceId;

    public Data_Entity() {
    }

    public Data_Entity(String dataId) {
        this.dataId = dataId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Date getTimeRecorded() {
        return timeRecorded;
    }

    public void setTimeRecorded(Date timeRecorded) {
        this.timeRecorded = timeRecorded;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public BigDecimal getInstPower() {
        return instPower;
    }

    public void setInstPower(BigDecimal instPower) {
        this.instPower = instPower;
    }

    public Device_Entity getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Device_Entity deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataId != null ? dataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Data_Entity)) {
            return false;
        }
        Data_Entity other = (Data_Entity) object;
        if ((this.dataId == null && other.dataId != null) || (this.dataId != null && !this.dataId.equals(other.dataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.slc.egauge.data.entities.Data_Entity[ dataId=" + dataId + " ]";
    }

}
