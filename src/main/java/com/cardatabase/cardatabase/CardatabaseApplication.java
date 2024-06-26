package com.cardatabase.cardatabase;

import com.cardatabase.cardatabase.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CardatabaseApplication.class);
    private final OwnerRepository orepository;
    private final CarRepository repository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public CardatabaseApplication(
            OwnerRepository orepository,
            CarRepository repository,
            AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.orepository = orepository;
        this.repository = repository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(CardatabaseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner("John", "Johnson");
        Owner owner2 = new Owner("Mary", "Robinson");
        orepository.saveAll(Arrays.asList(owner1, owner2));

        repository.save(new Car("Ford", "Mustang", "Red", "ADF-1121", 2023, 59000, owner1));
        repository.save(new Car("Nissan", "Leaf", "White", "SSJ-3002", 2020, 29000, owner2));
        repository.save(new Car("Toyota", "Prius", "Silver", "KKO-0212", 2022, 39000, owner2));

        for (Car car : repository.findAll()) {
            logger.info("brand: {}, model: {}", car.getBrand(), car.getModel());
        }
        appUserRepository.save(new AppUser("user",
                passwordEncoder.encode("user"),
                "USER"));
        appUserRepository.save(new AppUser("admin",
                passwordEncoder.encode("admin"),
                "ADMIN"));
    }
}
