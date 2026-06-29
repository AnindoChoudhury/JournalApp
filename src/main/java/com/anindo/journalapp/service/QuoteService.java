package com.anindo.journalapp.service;

import com.anindo.journalapp.cache.AppCache;
import com.anindo.journalapp.response.QuoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public QuoteResponse callQuoteAPI(){
        String api = appCache.getAPP_CACHE().get("quote_api");
        ResponseEntity<QuoteResponse> quoteResponse = restTemplate.exchange(api, HttpMethod.GET,null,QuoteResponse.class);
        return quoteResponse.getBody();
    }
}
