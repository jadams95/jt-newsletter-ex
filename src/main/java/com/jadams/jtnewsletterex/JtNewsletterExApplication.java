package com.jadams.jtnewsletterex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JtNewsletterExApplication {

    public static void main(String[] args) {
        SpringApplication.run(JtNewsletterExApplication.class, args);
    }

}
