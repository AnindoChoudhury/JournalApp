package com.anindo.journalapp.cache;

import com.anindo.journalapp.entity.ConfigJournalApp;
import com.anindo.journalapp.repository.ConfigRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@Data
public class AppCache {

    @Autowired
    private ConfigRepository configRepository;
    private HashMap<String,String> APP_CACHE;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalApp> configurations = configRepository.findAll();
        for(ConfigJournalApp config : configurations){
            APP_CACHE.put(config.getKey(), config.getValue());
        }
    }
}
