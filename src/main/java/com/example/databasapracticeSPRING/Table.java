package com.example.databasapracticeSPRING;
import java.io.FileWriter;
import java.util.ArrayList;
import org.apache.commons.lang3.time.DateUtils;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Table {
    private String phisical_name;
    private String description;
    private String creationDate;
    private List<Column> columns = new ArrayList<>();

    public Table(String phisical_name, String description, String date_time) {
        this.phisical_name = phisical_name;
        this.description = description;
        this.creationDate = formatDate(DateUtils.truncate(new Date(), Calendar.MINUTE));
        this.columns = new ArrayList<>();
    }

    public Table() {
        this.columns = new ArrayList<>();
    }


    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public void addColumn(int belongsToTable, String phisical_name, String data_type, int field_length, String key_attribute, String comment) {
        Column list = new Column(belongsToTable, phisical_name, data_type, field_length, key_attribute, comment);
        this.columns.add(list);
    }


    public void setPhisical_name(String phisical_name) {
        this.phisical_name = phisical_name;
    }

    public String getPhisical_name() {
        return phisical_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    private void setCreationDate() {
        this.creationDate = formatDate(DateUtils.truncate(new Date(), Calendar.MINUTE));
    }
    private String getCreationDate() {
        return creationDate;
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public ArrayList<Column> getColumn() {
        return columns;
    }

    public Column getColumn(int index) {
        if (index >= 0 && index < columns.size()) {
            return columns.get(index);
        } else {
            System.out.println("Индекс за пределами диапазона");
            return null;
        }
    }

    public void removeColumn(int index) {
        if (index >= 0 && index < columns.size()) {
            columns.remove(index);
            System.out.println("Колонка " + (index + 1) + " успешно удалена.");
        } else {
            System.out.println("Указанный индекс выходит за границы списка.");
        }
    }

    public void removeColumn(String columnName) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getName().equals(columnName)) {
                columns.remove(i);
                break;
            }
        }
    }

    public String toString() {
        return "\n" + "Имя таблицы = " + phisical_name + ' '
                + "Описание = " + description + " "
                + "Дата и время создания = " + creationDate + " "
                + "Колонки: " + columns;
    }

    // запись в файл информации о таблице
    public static void writeTablesToFile(String filename, List<Table> tables, int[] columnWidths) {
        try (FileWriter writer = new FileWriter(filename)) {

            // Записываем данные о каждой колонке
            for (Table table : tables) {
                StringBuilder rowData = new StringBuilder();
                rowData.append(Column.fixWidthString(table.getPhisical_name(), columnWidths[0]));
                rowData.append(Column.fixWidthString(table.getDescription(), columnWidths[1]));
                rowData.append(Column.fixWidthString(table.getCreationDate(), columnWidths[2]));
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
                String[] parts = Column.parseFixedWidthString(line, columnWidths);
                Table table = new Table(parts[0].trim(), parts[1].trim(), parts[2].trim());
                tables.add(table);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tables;
    }
}
