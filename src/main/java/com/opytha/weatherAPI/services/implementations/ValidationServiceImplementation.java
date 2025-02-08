package com.opytha.weatherAPI.services.implementations;

import com.opytha.weatherAPI.models.enums.Langs;
import com.opytha.weatherAPI.models.enums.Units;
import com.opytha.weatherAPI.services.ValidationService;
import com.opytha.weatherAPI.utils.exceptions.BadRequestException;
import jakarta.validation.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
public class ValidationServiceImplementation implements ValidationService {
    @Override
    public boolean isValidLang(String lang) {
        if (lang == null || lang.isBlank() ||
                Arrays.stream(Langs.values()).noneMatch(l -> l.name().equalsIgnoreCase(lang))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Language not supported");
        }
        return true;
    }

    @Override
    public boolean isValidUnit(String unit) {
        if (unit == null || unit.isBlank() ||
                Arrays.stream(Units.values()).noneMatch(u -> u.name().equalsIgnoreCase(unit))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit not supported");
        }
        return true;
    }
}
