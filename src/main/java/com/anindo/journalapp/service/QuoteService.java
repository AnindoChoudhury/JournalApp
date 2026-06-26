package com.anindo.journalapp.service;

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

    private static final String api = "https://indian-quotes-api.vercel.app/api/quotes/random";

    public QuoteResponse callQuoteAPI(){
        ResponseEntity<QuoteResponse> quoteResponse = restTemplate.exchange(api, HttpMethod.GET,null,QuoteResponse.class);
        return quoteResponse.getBody();
    }
}
