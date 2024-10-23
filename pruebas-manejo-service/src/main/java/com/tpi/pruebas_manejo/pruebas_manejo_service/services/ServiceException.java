package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

public class ServiceException extends Throwable {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

