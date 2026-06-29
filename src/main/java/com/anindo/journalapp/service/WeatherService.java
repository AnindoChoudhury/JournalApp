package com.anindo.journalapp.service;

import com.anindo.journalapp.cache.AppCache;
import com.anindo.journalapp.response.TemperatureResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class WeatherService {
    @Value("${weather.service.api}")
    private String apikey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public TemperatureResponse getWeather(String city){
        String API = appCache.getAPP_CACHE().get("weather_api");
        String finalApi = API.replace("<API_KEY>",apikey).replace("<CITY>",city);
        System.out.println(finalApi);
        ResponseEntity<TemperatureResponse> tempResponse = restTemplate.exchange(finalApi, HttpMethod.GET, null, TemperatureResponse.class);

        return tempResponse.getBody();
    }
}
