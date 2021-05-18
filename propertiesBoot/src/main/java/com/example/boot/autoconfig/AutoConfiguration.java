package com.example.boot.autoconfig;

import com.example.boot.properties.AutoProperties;
import com.example.boot.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(AutoProperties.class)
@Configuration
//@ConditionalOnClass()
@Slf4j
public class AutoConfiguration {
    @Autowired
    AutoProperties autoProperties;

    @Bean
    Service service(){
        Service service = new Service();
        service.setMyName(autoProperties.getName());
        service.setMyNumber(autoProperties.getNumber());
        log.info("Auto Config working ! ");
        return service;
    }

}
