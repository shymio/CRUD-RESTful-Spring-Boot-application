package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.DataBase;
import com.example.databasapracticeSPRING.model.Table;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AddTableController {
    @PostMapping("/add_table")
    public ResponseEntity<?> addTable(@ModelAttribute Table table, HttpSession session) {
        table.setCreationDate();
        DataBase dataBase = (DataBase) session.getAttribute("dataBase");

        if (dataBase != null) {
            dataBase.addTable(table);
        } else {
            dataBase = new DataBase();
            dataBase.addTable(table);
            session.setAttribute("dataBase", dataBase);
        }
        return ResponseEntity.ok(dataBase);
    }
}
