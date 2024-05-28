package com.efrei.olympic_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UpdateTicketDto {

    @NotEmpty
    private String ownerFullName;

    @Min(0)
    private Double price;

    @NotNull
    private Boolean isInsured;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer eventId;

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getIsInsured() {
        return isInsured;
    }

    public void setIsInsured(Boolean isInsured) {
        this.isInsured = isInsured;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
