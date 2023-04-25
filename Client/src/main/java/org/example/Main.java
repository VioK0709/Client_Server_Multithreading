package org.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.lang.reflect.Type;

public class Main {

    public static void main(String[] args) {
        String[] columnMapping = {"port", "host"};
        String fileName = "settings.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        String jsonFilename = "settings.json";
        writeString(json, jsonFilename);
        List<Employee> listThree = jsonToList(json);
        listThree.forEach(System.out::println);

        Runnable con = new Client(8080, "localhost");
        con.run();
    }


    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy =
                    new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return gson.toJson(list, listType);
    }

    private static void writeString(String json, String jsonFilename) {
        try (FileWriter file = new FileWriter(jsonFilename)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Employee> jsonToList(String json) {
        List<Employee> list = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            Object jsonString = new JSONParser().parse(json);
            JSONArray array = (JSONArray) jsonString;
            for (Object obj : array) {
                list.add(gson.fromJson(obj.toString(), Employee.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}