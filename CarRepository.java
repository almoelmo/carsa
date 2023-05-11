package org.example.cars.repository;

import org.example.cars.model.Car;

import java.util.List;

public interface CarRepository {
    List<Car> findAll();
    Car findCarById(long id);
    int save(Car car);
    int update(Car car);
    void deleteCarById(long id);
    void createTable();
    void dropTable();
    void deleteAll();
}
