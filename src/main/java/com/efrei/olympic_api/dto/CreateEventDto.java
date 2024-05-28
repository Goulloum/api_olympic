package com.efrei.olympic_api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateEventDto {

    @NotEmpty
    private String name;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime;

    @NotNull
    @Min(1)
    private Integer durationInMinute;

    @NotNull
    private Boolean isActive;

    @NotNull
    @Min(1)
    private Double ticketUnitPrice;

    @NotNull
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

    public Integer getDurationInMinute() {
        return durationInMinute;
    }

    public void setDurationInMinute(Integer durationInMinute) {
        this.durationInMinute = durationInMinute;
    }

    public Double getTicketUnitPrice() {
        return ticketUnitPrice;
    }

    public void setTicketUnitPrice(Double ticketUnitPrice) {
        this.ticketUnitPrice = ticketUnitPrice;
    }

}
