package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import repository.SalaRepository;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages={"com.example.demo", "service", "controller", "domain"})
@EnableJpaRepositories(basePackageClasses = {SalaRepository.class})
@EntityScan("domain")
public class PpdProiectApplication {

    public static void main(String[] args) {

        SpringApplication.run(PpdProiectApplication.class, args);
    }

}
