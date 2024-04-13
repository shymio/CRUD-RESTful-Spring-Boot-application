package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.DataBase;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddColumnsToTableController {
//    @GetMapping("/addcolumns_totable")
//    public ResponseEntity<?> addColumnsToTableController(HttpSession session) {
//        // Получаем объект dataBase из сессии
//        DataBase dataBase = (DataBase) session.getAttribute("dataBase");
//
//        // Если объект dataBase уже существует в сессии и список колонок тоже существует
//        if (dataBase != null && session.getAttribute("columns") != null) {
//            // Получаем список колонок из сессии
//            List<Column> columns = (List<Column>) session.getAttribute("columns");
//
//            // Добавляем колонки к объекту dataBase только если они еще не были добавлены
//            if (!dataBase.containsColumns(columns)) {
//                dataBase.addColumnsToTablesFromList(columns);
//                session.setAttribute("dataBase", dataBase);
//                return ResponseEntity.ok().body(dataBase.toString());
//            }
//        }
//
//        // Если объект dataBase еще не существует или список колонок не существует, возвращаем сообщение об ошибке
//        return ResponseEntity.ok().body("Не удалось");
//    }


    @GetMapping("/addcolumns_totable")
    public ResponseEntity<?> addColumnsToTableController(HttpSession session) {
        DataBase dataBase = (DataBase) session.getAttribute("dataBase");
        List<Column> columns = (List<Column>) session.getAttribute("columns");
        if (dataBase != null) {
            dataBase.addColumnsToTablesFromList(columns);
            session.setAttribute("dataBase", dataBase);
            return ResponseEntity.ok().body(dataBase.toString());
        } else return ResponseEntity.ok().body("Не удалось");
    }
}
