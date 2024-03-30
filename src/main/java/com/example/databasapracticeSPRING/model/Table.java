package com.example.databasapracticeSPRING.model;
import java.io.FileWriter;
import java.util.ArrayList;

import com.example.databasapracticeSPRING.model.Column;
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

    public Table() {this.columns = new ArrayList<>();}

    public void setPhisical_name(String phisical_name) {this.phisical_name = phisical_name;}
    public String getPhisical_name() {return phisical_name;}

    public void setDescription(String description) {this.description = description;}
    public String getDescription() {return description;}

    public void setCreationDate() {this.creationDate = formatDate(DateUtils.truncate(new Date(), Calendar.MINUTE));}
    public String getCreationDate() {return creationDate;}

    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public int size() {
        return columns.size();
    }

    public void setColumn(List<Column> columns) {this.columns = columns;}
    public List<Column> getColumn() {return columns;}

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public List<Column> getColumns() {
        return columns;
    }

//    public void addColumn(int belongsToTable, String phisical_name, String data_type, int field_length, String key_attribute, String comment) {
//        Column list = new Column(belongsToTable, phisical_name, data_type, field_length, key_attribute, comment);
//        this.columns.add(list);
//    }

    //запись в txt файл
    public static void writeColumnsToFile(String filename, List<Column> columns, int[] columnWidths) {
        try (FileWriter writer = new FileWriter(filename)) {

            // Записываем данные о каждой колонке
            for (Column column : columns) {
                StringBuilder rowData = new StringBuilder();
                rowData.append(fixWidthString(String.valueOf(column.getBelongsToTable()), columnWidths[0]));
                rowData.append(fixWidthString(column.getName(), columnWidths[1]));
                rowData.append(fixWidthString(column.getData_type(), columnWidths[2]));
                rowData.append(fixWidthString(String.valueOf(column.getField_length()), columnWidths[3]));
                rowData.append(fixWidthString(column.getKey_attribute(), columnWidths[4]));
                rowData.append(fixWidthString(column.getComment(), columnWidths[5]));
                writer.write(rowData.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Вспомогательный метод для выравнивания строки по заданной ширине колонки
    public static String fixWidthString(String input, int width) {
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

//     Считывание из txt файла
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

//     Вспомогательный метод для считывания из файла
    public static String[] parseFixedWidthString(String line, int[] columnWidths) {
        String[] columns = new String[columnWidths.length];
        int startPos = 0;
        for (int i = 0; i < columnWidths.length; i++) {
            int endPos = startPos + columnWidths[i];
            columns[i] = line.substring(startPos, Math.min(endPos, line.length()));
            startPos = endPos;
        }
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
}
