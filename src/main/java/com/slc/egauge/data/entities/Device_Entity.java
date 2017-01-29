/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.slc.egauge.data.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Steven Kritikos
 * email: stevenrktitikos@outlook.com
 */
@Entity
@Table(name = "device")
@NamedQueries({
  @NamedQuery(name = "Device_Entity.findAll", query = "SELECT d FROM Device_Entity d"),
  @NamedQuery(name = "Device_Entity.findByDeviceId", query = "SELECT d FROM Device_Entity d WHERE d.deviceId = :deviceId"),
  @NamedQuery(name = "Device_Entity.findByDeviceName", query = "SELECT d FROM Device_Entity d WHERE d.deviceName = :deviceName"),
  @NamedQuery(name = "Device_Entity.findByIsOnline", query = "SELECT d FROM Device_Entity d WHERE d.isOnline = :isOnline")})
public class Device_Entity implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 64)
  @Column(name = "deviceId")
  private String deviceId;
  @Size(max = 60)
  @Column(name = "deviceName")
  private String deviceName;
  @Column(name = "isOnline")
  private Boolean isOnline;
  @OneToMany(mappedBy = "deviceId")
  private List<Data_Entity> dataEntityList;

  public Device_Entity() {
  }

  public Device_Entity(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public Boolean getIsOnline() {
    return isOnline;
  }

  public void setIsOnline(Boolean isOnline) {
    this.isOnline = isOnline;
  }

  public List<Data_Entity> getDataEntityList() {
    return dataEntityList;
  }

  public void setDataEntityList(List<Data_Entity> dataEntityList) {
    this.dataEntityList = dataEntityList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (deviceId != null ? deviceId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Device_Entity)) {
      return false;
    }
    Device_Entity other = (Device_Entity) object;
    if ((this.deviceId == null && other.deviceId != null) || (this.deviceId != null && !this.deviceId.equals(other.deviceId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.slc.egauge.data.entities.Device_Entity[ deviceId=" + deviceId + " ]";
  }

}
