package com.ksh.soundstory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SoundstoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoundstoryApplication.class, args);
    }

}
