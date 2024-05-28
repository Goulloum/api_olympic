package com.efrei.olympic_api.dto;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;

public class UpdateEventDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private Date dateTime;

    @NotEmpty
    private Boolean isActive;

    @NotEmpty
    private Integer stadiumId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(Integer stadiumId) {
        this.stadiumId = stadiumId;
    }

}
