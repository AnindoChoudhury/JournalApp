package com.anindo.journalapp.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "config_journal_app")
public class ConfigJournalApp {
    @Id
    private ObjectId id;
    private String key;
    private String value;
}
