package com.anindo.journalapp.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class TemperatureResponse {

    private Current current;

    @Data
    public static class Current {
        private Integer temperature;
    }
}
