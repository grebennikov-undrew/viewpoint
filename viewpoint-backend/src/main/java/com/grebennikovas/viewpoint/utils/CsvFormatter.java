package com.grebennikovas.viewpoint.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvFormatter {

    static final String delimeter = ",";
    static final Charset charSet = StandardCharsets.UTF_8;

    public static byte[] export(List<String> columns, Map<String, Map<String, Object>> data) throws IOException {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Записать заголовки
            String header = "key," + String.join(delimeter,columns) + "\n";
            outputStream.write(header.getBytes(charSet));

            // Записать данные
            for (Map.Entry<String, Map<String, Object>> row: data.entrySet()) {
                // Получить ключ строки и строку
                String rowKey = row.getKey();
                Map<String, Object> rowData = row.getValue();

                // Получить данные строки
                String rowDataString = columns.stream()
                        .map(column -> rowData.get(column).toString())
                        .collect(Collectors.joining(delimeter));

                String fullRow = rowKey + "," + rowDataString + "\n";

                // Записать строку
                outputStream.write(fullRow.getBytes(charSet));
            }

            return outputStream.toByteArray();

        } catch (IOException e){
            e.printStackTrace();
            throw e;
        }
    }
}
