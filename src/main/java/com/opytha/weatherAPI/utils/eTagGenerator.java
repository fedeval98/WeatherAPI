package com.opytha.weatherAPI.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class eTagGenerator {
    public static <T> String generateETag(T data) {
        try {
            // Convierte el objeto Data a JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(data);

            // Calcula el hash SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(jsonData.getBytes(StandardCharsets.UTF_8));

            // Convierte el hash a un string hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString(); // Devuelve el ETag
        } catch (NoSuchAlgorithmException | JsonProcessingException e) {
            throw new RuntimeException("Error generating ETag", e);
        }
    }
}
