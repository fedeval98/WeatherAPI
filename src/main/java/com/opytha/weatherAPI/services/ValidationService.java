package com.opytha.weatherAPI.services;

public interface ValidationService {
    boolean isValidLang(String lang);
    boolean isValidUnit(String unit);
}
