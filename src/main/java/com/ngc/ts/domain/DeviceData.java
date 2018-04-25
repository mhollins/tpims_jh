package com.ngc.ts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DeviceData.
 */
@Entity
@Table(name = "device_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "devicedata")
public class DeviceData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "time_stamp")
    private ZonedDateTime timeStamp;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "jhi_zone")
    private Integer zone;

    @Column(name = "status")
    private String status;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "occupancy")
    private Integer occupancy;

    @ManyToOne
    private Device device;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public DeviceData timeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public DeviceData deviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getZone() {
        return zone;
    }

    public DeviceData zone(Integer zone) {
        this.zone = zone;
        return this;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public String getStatus() {
        return status;
    }

    public DeviceData status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSpeed() {
        return speed;
    }

    public DeviceData speed(Integer speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getVolume() {
        return volume;
    }

    public DeviceData volume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getOccupancy() {
        return occupancy;
    }

    public DeviceData occupancy(Integer occupancy) {
        this.occupancy = occupancy;
        return this;
    }

    public void setOccupancy(Integer occupancy) {
        this.occupancy = occupancy;
    }

    public Device getDevice() {
        return device;
    }

    public DeviceData device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceData deviceData = (DeviceData) o;
        if (deviceData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceData{" +
            "id=" + getId() +
            ", timeStamp='" + getTimeStamp() + "'" +
            ", deviceName='" + getDeviceName() + "'" +
            ", zone=" + getZone() +
            ", status='" + getStatus() + "'" +
            ", speed=" + getSpeed() +
            ", volume=" + getVolume() +
            ", occupancy=" + getOccupancy() +
            "}";
    }
}
