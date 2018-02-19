package com.ngc.ts.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.ngc.ts.domain.enumeration.OwnerShipOptions;

/**
 * Static Site configuration
 */
@ApiModel(description = "Static Site configuration")
@Entity
@Table(name = "site")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "site")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "site_id", nullable = false)
    private String siteId;

    @Column(name = "site_name")
    private String siteName;

    @Column(name = "total_capacity")
    private Integer totalCapacity;

    @Column(name = "low_threshold")
    private Integer lowThreshold;

    @Column(name = "static_data_updated")
    private ZonedDateTime staticDataUpdated;

    @Enumerated(EnumType.STRING)
    @Column(name = "ownership")
    private OwnerShipOptions ownership;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @OneToMany(mappedBy = "site")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Device> devices = new HashSet<>();

    @OneToMany(mappedBy = "site")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Amenities> amenities = new HashSet<>();

    @OneToMany(mappedBy = "site")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Images> images = new HashSet<>();

    @OneToMany(mappedBy = "site")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Logos> logos = new HashSet<>();

    @OneToOne(mappedBy = "site")
    @JsonIgnore
    private SiteStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public Site siteId(String siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public Site siteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public Site totalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
        return this;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Integer getLowThreshold() {
        return lowThreshold;
    }

    public Site lowThreshold(Integer lowThreshold) {
        this.lowThreshold = lowThreshold;
        return this;
    }

    public void setLowThreshold(Integer lowThreshold) {
        this.lowThreshold = lowThreshold;
    }

    public ZonedDateTime getStaticDataUpdated() {
        return staticDataUpdated;
    }

    public Site staticDataUpdated(ZonedDateTime staticDataUpdated) {
        this.staticDataUpdated = staticDataUpdated;
        return this;
    }

    public void setStaticDataUpdated(ZonedDateTime staticDataUpdated) {
        this.staticDataUpdated = staticDataUpdated;
    }

    public OwnerShipOptions getOwnership() {
        return ownership;
    }

    public Site ownership(OwnerShipOptions ownership) {
        this.ownership = ownership;
        return this;
    }

    public void setOwnership(OwnerShipOptions ownership) {
        this.ownership = ownership;
    }

    public Location getLocation() {
        return location;
    }

    public Site location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public Site devices(Set<Device> devices) {
        this.devices = devices;
        return this;
    }

    public Site addDevices(Device device) {
        this.devices.add(device);
        device.setSite(this);
        return this;
    }

    public Site removeDevices(Device device) {
        this.devices.remove(device);
        device.setSite(null);
        return this;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public Set<Amenities> getAmenities() {
        return amenities;
    }

    public Site amenities(Set<Amenities> amenities) {
        this.amenities = amenities;
        return this;
    }

    public Site addAmenities(Amenities amenities) {
        this.amenities.add(amenities);
        amenities.setSite(this);
        return this;
    }

    public Site removeAmenities(Amenities amenities) {
        this.amenities.remove(amenities);
        amenities.setSite(null);
        return this;
    }

    public void setAmenities(Set<Amenities> amenities) {
        this.amenities = amenities;
    }

    public Set<Images> getImages() {
        return images;
    }

    public Site images(Set<Images> images) {
        this.images = images;
        return this;
    }

    public Site addImages(Images images) {
        this.images.add(images);
        images.setSite(this);
        return this;
    }

    public Site removeImages(Images images) {
        this.images.remove(images);
        images.setSite(null);
        return this;
    }

    public void setImages(Set<Images> images) {
        this.images = images;
    }

    public Set<Logos> getLogos() {
        return logos;
    }

    public Site logos(Set<Logos> logos) {
        this.logos = logos;
        return this;
    }

    public Site addLogos(Logos logos) {
        this.logos.add(logos);
        logos.setSite(this);
        return this;
    }

    public Site removeLogos(Logos logos) {
        this.logos.remove(logos);
        logos.setSite(null);
        return this;
    }

    public void setLogos(Set<Logos> logos) {
        this.logos = logos;
    }

    public SiteStatus getStatus() {
        return status;
    }

    public Site status(SiteStatus siteStatus) {
        this.status = siteStatus;
        return this;
    }

    public void setStatus(SiteStatus siteStatus) {
        this.status = siteStatus;
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
        Site site = (Site) o;
        if (site.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), site.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Site{" +
            "id=" + getId() +
            ", siteId='" + getSiteId() + "'" +
            ", siteName='" + getSiteName() + "'" +
            ", totalCapacity=" + getTotalCapacity() +
            ", lowThreshold=" + getLowThreshold() +
            ", staticDataUpdated='" + getStaticDataUpdated() + "'" +
            ", ownership='" + getOwnership() + "'" +
            "}";
    }
}
