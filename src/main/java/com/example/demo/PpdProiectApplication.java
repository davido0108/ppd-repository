package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import repository.SalaRepository;
import repository.SpectacolRepository;
import repository.VanzareRepository;

import java.util.Timer;
import java.util.TimerTask;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages={"com.example.demo", "service", "controller", "domain"})
@EnableJpaRepositories(basePackageClasses = {SpectacolRepository.class, VanzareRepository.class,SalaRepository.class})
@EntityScan("domain")
public class PpdProiectApplication {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SpringApplication.run(PpdProiectApplication.class, args);

            }
        },0, 120000);
    }

}
