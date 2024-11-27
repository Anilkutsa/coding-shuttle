package com.codingshuttle.w1p1.alicebakery.W1P1_AliceBakery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class W1P1AliceBakeryApplication implements CommandLineRunner {

    @Autowired
    CakeBaker cakebaker;

    public static void main(String[] args) {
        SpringApplication.run(W1P1AliceBakeryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(cakebaker.BakeCake());
    }
}
