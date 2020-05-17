package ru.keni0k.game.tanks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class TanksApplication {

    public static void main(String[] args) {
        SpringApplication.run(TanksApplication.class, args);
    }

}
