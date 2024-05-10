package com.djo.school_pfe.error;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {

    private Integer status = 404;
    private String errorMessage;

    public NotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
