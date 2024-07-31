package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("")
public class WebController {


    @GetMapping("/dashboard")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/dashboard"); // Görünüm adını ayarla
        return modelAndView;
    }
    @GetMapping("/")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/login"); // Görünüm adını ayarla
        return modelAndView;
    }
    @GetMapping("/register")
    public ModelAndView registerPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/register"); // Görünüm adını ayarla
        return modelAndView;
    }
}
