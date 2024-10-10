package com.opytha.weatherAPI.dtos.openweather;

import com.opytha.weatherAPI.dtos.openweather.airPollution.PollutionList;
import com.opytha.weatherAPI.dtos.openweather.weather.Coord;
import lombok.Data;

import java.util.List;

@Data
public class AirPollutioNData {
    private Coord coord;
    private List<PollutionList> list;
}
