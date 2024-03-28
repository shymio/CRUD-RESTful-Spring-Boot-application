package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.Column;
import com.example.databasapracticeSPRING.Table;
import com.example.databasapracticeSPRING.DataBase;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ColumnController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

}
