package com.example.databasapracticeSPRING;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Column {
    private int belongsToTable;
    private String phisical_name;
    private String data_type;
    private int field_length;
    private String key_attribute;
    private String comment;

    public Column(int belongsToTable, String phisical_name, String data_type, int field_length, String key_attribute, String comment) {
        this.belongsToTable = belongsToTable;
        this.phisical_name = phisical_name;
        this.data_type = data_type;
        this.field_length = field_length;
        this.key_attribute = key_attribute;
        this.comment = comment;
    }

    public void setBelongsToTable(int belongsToTable) {
        this.belongsToTable = belongsToTable;
    }

    public int getBelongsToTable() {
        return belongsToTable;
    }

    public void setName(String phisical_name) {
        this.phisical_name = phisical_name;
    }

    public String getName() {
        return phisical_name;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getData_type() {
        return data_type;
    }

    public void setField_length(int field_length) {
        this.field_length = field_length;
    }

    public int getField_length() {
        return field_length;
    }
    public void setKey_attribute(String key_attribute) {
        this.key_attribute = key_attribute;
    }

    public String getKey_attribute() {
        return key_attribute;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String toString() {
        return "\n" + "№ таблицы = " + belongsToTable + ", " +
                "Имя колонки = " + phisical_name + ", " +
                "Тип данных = " + data_type + ", " +
                "Длина поля = " + field_length + ", " +
                "Атрибут ключа = " + key_attribute + ", " +
                "Комментарий = " + comment + "}";
    }

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

    // Считывание из файла
    public static ArrayList<Column> readFromFile(String filename, int[] columnWidths) {
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
}
