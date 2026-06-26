package com.anindo.journalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {
        System.out.println("!!!!!! IF YOU DO NOT SEE THIS, THE CODE IS NOT COMPILING !!!!!!");
        System.setProperty("spring.data.mongodb.uri", "mongodb+srv://anindochoudhury2004_db_user:anindo@cluster0.dwiubed.mongodb.net/journal_db?appName=Cluster0");
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
