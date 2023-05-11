package org.example.cars.controller;

import org.example.cars.model.Car;
import org.example.cars.repository.JdbcCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {
    @Autowired
    private JdbcCarRepository repository;

    @GetMapping(value = {"/", "/main"})
    public String mainPage(){
        return "index";
    }
    /**
     * Список всех элементов из таблиц
     */
    @GetMapping("/cars")
    public String getCars(Model model){
        model.addAttribute("cars", repository.findAll());
        return "cars";
    }

    @GetMapping("/cars/{id}")
    public String getCar(Model model, @PathVariable long id){
        Car car = null;
        try {
            car = repository.findCarById(id);
            model.addAttribute("allowDelete", false);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("car", car);
        return "car";
    }

    /**
     * Создание элемента
     */
    @GetMapping(value = {"/cars/new"})
    public String showNewCar(Model model) {
        Car car = new Car();
        model.addAttribute("add", true);
        model.addAttribute("car", car);
        return "car-form";
    }

    @PostMapping(value = "/cars/new")
    public String addCar( Model model, @ModelAttribute("car") Car car) {
        try {
            //car.setReleaseYear(car.getReleaseYear());
            repository.save(car);
            return "redirect:/cars";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", true);
            return "car-form";
        }
    }

    /**
     * Обновление элемента
     */
    @GetMapping(value = {"/cars/{id}/edit"})
    public String showEditCar(Model model, @PathVariable long id) {
        Car car = null;
        try {
            car = repository.findCarById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("car", car);
        return "car-form";
    }

    @PostMapping(value = {"/cars/{id}/edit"})
    public String updateCar(Model model,
                              @PathVariable long id,
                              @ModelAttribute("car") Car car) {
        try {
            car.setId(id);
            repository.update(car);
            return "redirect:/cars/" + car.getId();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "car-form";
        }
    }

    /**
     * Удаление строки из каждой таблицы
     */
    @GetMapping(value = {"/cars/{id}/delete"})
    public String showDeleteCarById(Model model, @PathVariable long id) {
        Car car = null;
        try {
            car = repository.findCarById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("car", car);
        return "car";
    }

    @PostMapping(value = {"/cars/{id}/delete"})
    public String deleteCarById(Model model, @PathVariable long id) {
        try {
            repository.deleteCarById(id);
            return "redirect:/cars";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "car";
        }
    }

    @GetMapping(value = "/create")
    public String createTable(){
        repository.createTable();
        return "redirect:/";
    }

    @GetMapping(value = "/drop")
    public String dropTable(){
        repository.dropTable();
        return "redirect:/";
    }

    @GetMapping(value = "/truncate")
    public String truncateTable(){
        repository.deleteAll();
        return "redirect:/";
    }
}
