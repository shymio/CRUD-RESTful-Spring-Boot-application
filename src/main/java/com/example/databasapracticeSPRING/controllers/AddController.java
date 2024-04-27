package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.DataBase;
import com.example.databasapracticeSPRING.model.Table;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddController {
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
        return ResponseEntity.status(HttpStatus.CREATED).body(dataBase);
    }


    @PostMapping("/add_column")
    public ResponseEntity<?> addColumn(@ModelAttribute Column column, HttpSession session) {
        DataBase dataBase = (DataBase) session.getAttribute("dataBase");

        if (dataBase != null) {
            if (dataBase.hasTableWithName(column)) {
                dataBase.addOneColumnToTable(column);
                return ResponseEntity.status(HttpStatus.CREATED).body(dataBase);
            } else {
                String errorMessage = "Ошибка: Таблица с именем " + column.getBelongsToTable() + " не найдена.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        } else {
            String errorMessage = "Ошибка: База данных не найдена.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
