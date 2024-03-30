package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.Table;
import com.example.databasapracticeSPRING.model.DataBase;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class ShowController {

    @PostMapping("/show")
    public ResponseEntity<Table> showService(@RequestParam String tableName, HttpSession session) {
        try {
            DataBase dataBase = (DataBase) session.getAttribute("dataBase");
            ResponseEntity<Table> response = dataBase.showService(tableName);

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return response;
            } else {
                return response;
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
