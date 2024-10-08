package com.opytha.weatherAPI.dtos;

import com.opytha.weatherAPI.dtos.airPollution.Components;
import com.opytha.weatherAPI.dtos.airPollution.PollutionList;
import com.opytha.weatherAPI.dtos.weather.Coord;
import lombok.Data;

import java.util.List;

@Data
public class AirPollutioNData {
    private Coord coord;
    private List<PollutionList> list;
}
