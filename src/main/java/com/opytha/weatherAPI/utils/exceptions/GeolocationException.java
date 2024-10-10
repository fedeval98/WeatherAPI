package com.opytha.weatherAPI.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GeolocationException extends RuntimeException {
    public GeolocationException(String message) {
        super(message);
    }
}
