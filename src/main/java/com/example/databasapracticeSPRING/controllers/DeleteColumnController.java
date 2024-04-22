package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.DataBase;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteColumnController {

    @DeleteMapping("/delete_column")
    public ResponseEntity<String> deleteColumn(@RequestParam String tableName, @RequestParam String columnName, HttpSession session) {
        DataBase dataBase = (DataBase) session.getAttribute("dataBase");
        if (dataBase == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка: база данных не найдена в сессии.");
        }

        try {
            dataBase.removeColumnFromTable(tableName, columnName);
            return ResponseEntity.ok("Колонка '" + columnName + "' успешно удалена из таблицы '" + tableName + "'.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ошибка: " + e.getMessage());
        }
    }
}
