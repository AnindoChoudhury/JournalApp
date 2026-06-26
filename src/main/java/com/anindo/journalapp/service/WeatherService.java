package com.anindo.journalapp.service;

import com.anindo.journalapp.response.TemperatureResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private static final String apikey = "a04b82bf0d7bdada8e7fc2d37a1da91c";

    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public TemperatureResponse getWeather(String city){
        String finalApi = API.replace("API_KEY",apikey).replace("CITY",city);
        ResponseEntity<TemperatureResponse> tempResponse = restTemplate.exchange(finalApi, HttpMethod.GET, null, TemperatureResponse.class);

        return tempResponse.getBody();
    }
}
