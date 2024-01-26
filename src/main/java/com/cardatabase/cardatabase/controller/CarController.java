package com.cardatabase.cardatabase.controller;

import com.cardatabase.cardatabase.domain.Car;
import com.cardatabase.cardatabase.domain.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {
    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    @GetMapping("/cars")
    public List<Car> getCars(){
        return carRepository.findAll();
    }
}
