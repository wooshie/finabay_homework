package com.bambr.finabay_homework;

import com.maxmind.geoip2.WebServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    @Value("${geoip.user_id}")
    Integer geoIpUser;

    @Value("${geoip.license_key}")
    String geoIpKey;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebServiceClient webServiceClient() {
        return new WebServiceClient.Builder(geoIpUser, geoIpKey).build();
    }
}
