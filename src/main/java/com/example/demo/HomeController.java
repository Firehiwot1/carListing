package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    CategoryRegister categoryRepository;
    @Autowired
    CarRepository carRepository;
    @RequestMapping("/")
    public String listCategory(Model model){
        model.addAttribute ( "car", carRepository.findAll () );
        model.addAttribute ( "categories", categoryRepository.findAll () );
        return "categorylist";
    }
    @GetMapping("/addcategory")
    public String categoryForm(Model model){
        model.addAttribute ( "car", carRepository.findAll () );
        model.addAttribute ( "category", new Category () );
        return "categoryform";
    }
    @GetMapping("/addcars")
    public String carForm(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("car", new Car());
        return "carform";
    }

    @PostMapping("/processcategory")
    public String processform(@Valid Category category, BindingResult result, Model model) {
        if (result.hasErrors ()) {

            return "categoryform";
        }
        categoryRepository.save ( category );
        return "redirect:/";
    }
    @PostMapping("/processcar")
    public String processform(@Valid Car car, BindingResult result, Model model) {
        if (result.hasErrors ()) {

            return "carform";
        }
        carRepository.save(car);
        return "redirect:/";
    }
    @RequestMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") long id, Model model) {
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "categoryform";
    }
    @RequestMapping("/detail/car/{id}")
    public String showCar(@PathVariable("id") long id, Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("car", carRepository.findById(id).get());
        return "carlist";
    }
    @RequestMapping("/update/car{id}")
    public String updateCar(@PathVariable("id") long id, Model model){
        model.addAttribute("car", carRepository.findById(id).get());
        return "carform";
    }
    @RequestMapping("/delete/car{id}")
    public String delCar(@PathVariable("id") long id){
        carRepository.deleteById(id);
        return "redirect:/";
    }
}

