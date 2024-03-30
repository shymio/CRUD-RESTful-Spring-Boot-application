package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Table;
import com.example.databasapracticeSPRING.service.TableService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UploadController {

    private Table table;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam String filename, @RequestParam int[] columnWidths, HttpSession session, @RequestParam("txtFile") MultipartFile txtFile) {
        if (!txtFile.isEmpty()) {
            if (txtFile.getOriginalFilename().endsWith(".txt")) {
                try {
                    byte[] bytes = txtFile.getBytes();
                    session.setAttribute("uploadedJsonFile", txtFile);
                    table = new Table();
                    table.readFromFile(filename, columnWidths);
                    session.setAttribute("table", table);
                    return ResponseEntity.ok(table.getColumns());


                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при загрузке файла.");
                }
            } else {
                return ResponseEntity.badRequest().body("Ошибка при загрузке файла.");
            }
        } else {
            return ResponseEntity.badRequest().body("Файл не был выбран.");
        }
    }
}