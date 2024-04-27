package com.example.databasapracticeSPRING.service;

import java.io.FileWriter;
import java.util.UUID;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.Table;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkWithFileService {

//     запись таблиц в файл
    public void writeTablesToFile(String filename, List<Table> tables, int[] columnWidths) {
        try (FileWriter writer = new FileWriter(filename)) {

            for (Table table : tables) {
                StringBuilder rowData = new StringBuilder();
                rowData.append(fixWidthString(table.getPhisical_name(), columnWidths[0]));
                rowData.append(fixWidthString(table.getDescription(), columnWidths[1]));
                rowData.append(fixWidthString(table.getCreationDate(), columnWidths[2]));
                writer.write(rowData.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//     Считывание таблиц из файла
    public ArrayList<Table> readFromFileTable(String filename, int[] columnWidths) {
        ArrayList<Table> tables = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = parseFixedWidthString(line, columnWidths);
                Table table = new Table(parts[0].trim(), parts[1].trim(), parts[2].trim());
                tables.add(table);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tables;
    }

//     запись колонок в файл
    public void writeColumnsToFile(String filename, List<Column> columns, int[] columnWidths) {
        try (FileWriter writer = new FileWriter(filename)) {

            for (Column column : columns) {
                StringBuilder rowData = new StringBuilder();
                rowData.append(fixWidthString(String.valueOf(column.getUuid()), columnWidths[0]));
                rowData.append(fixWidthString(String.valueOf(column.getBelongsToTable()), columnWidths[1]));
                rowData.append(fixWidthString(column.getName(), columnWidths[2]));
                rowData.append(fixWidthString(column.getData_type(), columnWidths[3]));
                rowData.append(fixWidthString(String.valueOf(column.getField_length()), columnWidths[4]));
                rowData.append(fixWidthString(column.getKey_attribute(), columnWidths[5]));
                rowData.append(fixWidthString(column.getComment(), columnWidths[6]));
                writer.write(rowData.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Считывание колонок из файла
    public ArrayList<Column> readFromFileColumn(String filename, int[] columnWidths) {
        ArrayList<Column> columns = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = parseFixedWidthString(line, columnWidths);
                Column column = new Column(UUID.fromString(parts[0].trim()), parts[1].trim(),
                        parts[2].trim(), parts[3].trim(), Integer.parseInt(parts[4].trim()),
                        parts[5].trim(), parts[6].trim());
                columns.add(column);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return columns;
    }

//     Вспомогательный метод для выравнивания строки по заданной ширине колонки
    public String fixWidthString(String input, int width) {
        if (input.length() >= width) {
            return input.substring(0, width);
        } else {
            StringBuilder paddedString = new StringBuilder(input);
            while (paddedString.length() < width) {
                paddedString.append(" ");
            }
            return paddedString.toString();
        }
    }

    // разбор строки для записи в файл
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
