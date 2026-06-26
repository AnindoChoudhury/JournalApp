package com.anindo.journalapp.response;

import lombok.Data;


@Data
public class QuoteResponse {

    private String quote;
    private Author author;
    @Data
    public static class Author {
        private String name;
    }
}

