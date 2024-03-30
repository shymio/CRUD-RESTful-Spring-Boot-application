package com.example.databasapracticeSPRING.service;

import com.example.databasapracticeSPRING.model.Column;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {

    public List<Column> readFromFile(String filename, int[] columnWidths) {
        ArrayList<Column> columns = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = parseFixedWidthString(line, columnWidths);
                Column column = new Column(Integer.parseInt(parts[0].trim()), parts[1].trim(), parts[2].trim(), Integer.parseInt(parts[3].trim()), parts[4].trim(), parts[5].trim());
                columns.add(column);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return columns;
    }

    // Вспомогательный метод для считывания из файла
    public String[] parseFixedWidthString(String line, int[] columnWidths) {
        String[] columns = new String[columnWidths.length];
        int startPos = 0;
        for (int i = 0; i < columnWidths.length; i++) {
            int endPos = startPos + columnWidths[i];
            columns[i] = line.substring(startPos, Math.min(endPos, line.length()));
            startPos = endPos;
        }
        return columns;
    }
}


//public class ColumnService {
//
//}
