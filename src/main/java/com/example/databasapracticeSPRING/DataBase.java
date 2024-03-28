package com.example.databasapracticeSPRING;
import org.apache.commons.lang3.time.DateUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private String databaseName;
    private List<Table> tables = new ArrayList<>();
    public DataBase(String databaseName) {
        this.databaseName = databaseName;
        this.tables = new ArrayList<>();
    }

    public DataBase() {
        this.tables = new ArrayList<>();
    }

    public void setDatabaseName(String databaseName) {this.databaseName = databaseName;}

    public String getDatabaseName() {return databaseName;}

    public int getTableIndexByName(String tableName) {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getPhisical_name().equals(tableName)) {
                return i;
            }
        }
        return -1; // Возвращаем -1, если таблица с таким именем не найдена
    }



    public List<Table> getTable() {
        return tables;
    }

    public Table getTable (int index) {
        if (index >= 0 && index < tables.size()) {
            return tables.get(index);
        } else {
            System.out.println("Индекс за пределами диапазона");
            return null;
        }
    }

    public String toString() {
        return "База данных {" + "Имя БД = " + databaseName + ", "
                + "Таблицы: " + tables + "}";
    }

    public void addTable(Table table) {
        this.tables.add(table);
    }
    public void addTable(String phisical_name, String description, String date_time) {
        Table one = new Table(phisical_name, description, date_time);
        tables.add(one);
    }

    public void removeTable(String tableName) {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getPhisical_name().equals(tableName)) {
                tables.remove(i);
                break;
            }
        }
    }

    // запись в файл информации о таблице
    public static void writeTablesToFile(String filename, List<Table> tables, int[] columnWidths) {
        try (FileWriter writer = new FileWriter(filename)) {

            // Записываем данные о каждой колонке
            for (Table table : tables) {
                StringBuilder rowData = new StringBuilder();
                rowData.append(Table.fixWidthString(table.getPhisical_name(), columnWidths[0]));
                rowData.append(Table.fixWidthString(table.getDescription(), columnWidths[1]));
                rowData.append(Table.fixWidthString(table.getCreationDate(), columnWidths[2]));
                writer.write(rowData.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Считывание из файла
    public static ArrayList<Table> readFromFile(String filename, int[] columnWidths) {
        ArrayList<Table> tables = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = Table.parseFixedWidthString(line, columnWidths);
                Table table = new Table(parts[0].trim(), parts[1].trim(), parts[2].trim());
                tables.add(table);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tables;
    }

}
