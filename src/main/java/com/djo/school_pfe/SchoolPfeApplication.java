package com.djo.school_pfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
public class SchoolPfeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolPfeApplication.class, args);
        System.out.println("applicationStarted");
//        Instant instant = Instant.now();
//        Instant instant2 = instant.plus(1, ChronoUnit.DAYS);
//        instant.plus(1, ChronoUnit.DAYS);
//        System.out.println(instant2);
//        Date date = new Date();
//        date.setTime(date.getTime()+ 24 *60 *60 *1000);
//        date.setTime(date.getTime()+ 24 *60 *60 *1000);
//        System.out.println(date);
//        Date sharedDate = new Date();
//        Runnable task = () -> {
//            sharedDate.setTime(sharedDate.getTime() + 24*60*60*1000); // Modification de la date en ajoutant 1 day
//            System.out.println(Thread.currentThread().getName() + ": Updated date: " + sharedDate);
//        };
//
//        // Création de plusieurs threads
//        Thread thread1 = new Thread(task);
//        Thread thread2 = new Thread(task);
//        Thread threadx = new Thread(task);
//
//        // Démarrage des threads
//        thread1.start();
//        thread2.start();
//        threadx.start();
//
//        Instant sharedInstant = Instant.now();
//        Runnable task2 = () -> {
//            Instant updated = sharedInstant.plus(1,ChronoUnit.DAYS); // Modification de la date en ajoutant 1 seconde
//            System.out.println(Thread.currentThread().getName() + ": Updated date: " + updated);
//        };
//
//        // Création de plusieurs threads
//        Thread thread3 = new Thread(task2);
//        Thread thread4 = new Thread(task2);
//        Thread thready = new Thread(task2);
//
//        // Démarrage des threads
//        thread3.start();
//        thread4.start();
//        thready.start();
    }
}
