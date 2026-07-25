package com.nexfiscal_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nexfiscal_api.config.DotenvLoader;

@SpringBootApplication
public class NexfiscalApiApplication {

    public static void main(String[] args) {
        DotenvLoader.init();
        SpringApplication.run(NexfiscalApiApplication.class, args);
    }
}
