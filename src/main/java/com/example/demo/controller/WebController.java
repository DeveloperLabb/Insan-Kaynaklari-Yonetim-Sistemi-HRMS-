package com.example.demo.controller;

import com.example.demo.service.AuthenticationService;
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

    private final AuthenticationService authenticationService;

    @Autowired
    public WebController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

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
}
