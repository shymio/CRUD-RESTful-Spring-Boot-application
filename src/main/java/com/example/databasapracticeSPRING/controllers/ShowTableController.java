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
    public ResponseEntity<Table> showTable(@RequestParam String tableName, HttpSession session) {
        try {
            DataBase dataBase = (DataBase) session.getAttribute("dataBase");
            if (dataBase == null) {
                throw new RuntimeException("DataBase object not found in session");
            }

            dataBase.findTable(tableName);
            ResponseEntity<Table> response = dataBase.showTable(tableName);

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return response;
            } else {
                return response;
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

//        try {
//            DataBase dataBase = (DataBase) session.getAttribute("dataBase");
//            dataBase.findTable(tableName);
//
//            ResponseEntity<Table> response = dataBase.showTable(tableName);
//
//            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
//                return response;
//            } else {
//                return response;
//            }
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
    }
}