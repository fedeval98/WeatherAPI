package com.opytha.weatherAPI.dtos.airPollution;

import lombok.Data;

import java.util.Map;

@Data
public class PollutionList {
    private long dt;
    private Map<String, Short> main;
    private Components components;
}
