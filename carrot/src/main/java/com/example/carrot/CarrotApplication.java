package com.example.carrot;

import com.example.carrot.common.TryBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement

public class CarrotApplication {

    public static void main(String[] args) {
//        SpringApplication.run(CarrotApplication.class, args);
        SpringApplication springApplication = new SpringApplication(CarrotApplication.class);
        springApplication.setBanner(new TryBanner());
        springApplication.run(args);
    }

}
