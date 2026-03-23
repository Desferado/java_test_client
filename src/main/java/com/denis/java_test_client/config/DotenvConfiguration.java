package com.denis.java_test_client.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DotenvConfiguration {

    @PostConstruct
    public void loadDotenv(){
        Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();

        //Устанавливаем переменные окружения из .env
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
    }
}
