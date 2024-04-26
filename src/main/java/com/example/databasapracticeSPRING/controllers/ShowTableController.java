package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Table;
import com.example.databasapracticeSPRING.model.DataBase;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShowTableController {

    @GetMapping("/show")
    public ResponseEntity<?> showTable(@RequestParam String tableName, HttpSession session) {
        try {
            DataBase dataBase = (DataBase) session.getAttribute("dataBase");
            if (dataBase == null) {
                throw new RuntimeException("Ошибка: объект базы данных не найден в сессии.");
            }

            Table table = dataBase.findTable(tableName);
            if (table == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Таблица с именем " + tableName + " не найдена.");
            } else {
                return ResponseEntity.ok(table);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Внутренняя ошибка сервера: " + e.getMessage());
        }
    }
}