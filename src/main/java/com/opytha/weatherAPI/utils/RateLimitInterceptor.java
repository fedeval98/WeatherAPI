package com.opytha.weatherAPI.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final long LIMIT_TIME_FRAME = 60 * 1000; // en milisegundos (1 minuto)
    private static final int LIMIT_REQUESTS = 100; // número de solicitudes permitidas
    private final ConcurrentHashMap<String, RateLimit> requestCounts = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        requestCounts.putIfAbsent(ip, new RateLimit(currentTime));

        RateLimit rateLimit = requestCounts.get(ip);

        // Comprueba si ha expirado el tiempo
        if (rateLimit.isExpired(currentTime)) {
            rateLimit.reset(currentTime);
        }

        // Incrementa el contador y verifica si se excede el límite
        if (rateLimit.getRequestCount() < LIMIT_REQUESTS) {
            rateLimit.incrementRequestCount();
            return true; // Permite la solicitud
        } else {
            response.setStatus(429); // Demasiadas solicitudes
            return false; // Bloquea la solicitud
        }
    }

    private static class RateLimit {
        private int requestCount;
        private long startTime;

        public RateLimit(long startTime) {
            this.requestCount = 1; // Contador inicial
            this.startTime = startTime;
        }

        public void incrementRequestCount() {
            requestCount++;
        }

        public int getRequestCount() {
            return requestCount;
        }

        public boolean isExpired(long currentTime) {
            return currentTime - startTime >= LIMIT_TIME_FRAME; // Compara el tiempo actual con el tiempo de inicio
        }

        public void reset(long currentTime) {
            requestCount = 1; // Reinicia el contador
            startTime = currentTime; // Reinicia el tiempo de inicio
        }
    }
}
