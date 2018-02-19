package com.ngc.ts.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Logos entity.
 */
public class LogosDTO implements Serializable {

    private Long id;

    private String logoUrl;

    private Long siteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LogosDTO logosDTO = (LogosDTO) o;
        if(logosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogosDTO{" +
            "id=" + getId() +
            ", logoUrl='" + getLogoUrl() + "'" +
            "}";
    }
}
