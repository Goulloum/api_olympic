package com.efrei.olympic_api.exception;

import com.efrei.olympic_api.enums.EntityEnum;

public class RessourceNotFoundException extends RuntimeException {

    // Constructor with special argument that is the entity type that wasn't found
    public RessourceNotFoundException(EntityEnum entity) {
        super("Ressource not found: " + entity + " not found");

    }
}
