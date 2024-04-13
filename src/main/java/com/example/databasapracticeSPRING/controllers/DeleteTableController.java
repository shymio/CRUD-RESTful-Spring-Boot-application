package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.DataBase;
import com.example.databasapracticeSPRING.model.Table;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteTableController {
    @DeleteMapping("/delete_table")
    public ResponseEntity<String> deleteTable(@RequestParam String tableName, HttpSession session) {
        try {
            DataBase dataBase = (DataBase) session.getAttribute("dataBase");
            if (dataBase == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Объект базы данных не найден в сессии");
            }

            Table tableToRemove = dataBase.findTable(tableName);
            if (tableToRemove == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Таблица с именем '" + tableName + "' не найдена");
            }

            // Удаляем таблицу из базы данных
            dataBase.removeTableByName(tableName);
            session.setAttribute("dataBase", dataBase);

            return ResponseEntity.ok().body("Таблица '" + tableName + "' успешно удалена");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Внутренняя ошибка сервера: " + e.getMessage());
        }
    }
}
