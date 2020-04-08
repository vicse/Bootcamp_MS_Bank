package com.vos.bootcamp.msbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsBankApplication {

  public static void main(String[] args) {
    SpringApplication.run(MsBankApplication.class, args);
  }

}
