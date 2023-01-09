package com.marsraver.icndb.tools;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class DataProcessor {

    private static Map<String, Integer> catMap() {
        Map<String, Integer> catMap = new HashMap<>();
        catMap.put("animal", 1);
        catMap.put("career", 2);
        catMap.put("celebrity", 3);
        catMap.put("dev", 4);
        catMap.put("explicit", 5);
        catMap.put("food", 6);
        catMap.put("history", 7);
        catMap.put("money", 8);
        catMap.put("movie", 9);
        catMap.put("music", 10);
        catMap.put("political", 11);
        catMap.put("religion", 12);
        catMap.put("science", 13);
        catMap.put("sport", 14);
        catMap.put("travel", 15);
        return catMap;
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<GetChuck.Joke>> typeReference = new TypeReference<List<GetChuck.Joke>>() {
        };
        List<GetChuck.Joke> jokes = objectMapper.readValue(new File("jokes.json"), typeReference);

//        writeCategories(jokes);
        writeJokes(jokes);
        writeJoins(jokes);

    }

    private static void writeJoins(List<GetChuck.Joke> jokes) throws IOException {
        Map<String, Integer> catMap = catMap();
        try (FileWriter fileWriter = new FileWriter("joke_categories.sql")) {
            jokes.stream().filter(j -> j.getCategories().size() > 0).forEach(joke ->
                    joke.getCategories().stream().forEach(category -> {
                        Integer categoryId = catMap.get(category);
                        try {
                            String sql = "insert into joke_categories(joke_id, categories_id) " +
                                    "values (" + joke.getId() + ", " + categoryId + ");";
                            fileWriter.write(sql + System.lineSeparator());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    })
            );
        }
    }

    private static void writeCategories(List<GetChuck.Joke> jokes) throws IOException {
        try (FileWriter fileWriter = new FileWriter("categories.sql")) {
            List<String> categories = jokes.stream().filter(j -> j.getCategories().size() > 0)
                    .map(GetChuck.Joke::getCategories)
                    .flatMap(cat -> cat.stream())
                    .distinct()
                    .collect(Collectors.toList());

            Collections.sort(categories);
            AtomicLong id = new AtomicLong();
            categories.stream().forEach(category -> {

                try {
                    String sql = "insert into category values (" + id.incrementAndGet() + ", '" + category + "');";
                    fileWriter.write(sql + System.lineSeparator());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }


    private static void writeJokes(List<GetChuck.Joke> jokes) throws IOException {
        try (FileWriter fileWriter = new FileWriter("jokes.sql")) {
            jokes.stream().forEach(joke -> {
                String sql = "insert into joke(id, guid, created_at, icon_url, updated_at, url, data) values " +
                        "(" +
                        "" + joke.getId() + "," +
                        "'" + joke.getGuid() + "'," +
                        "'" + joke.getCreated_at() + "'," +
                        "'" + joke.getIcon_url() + "'," +
                        "'" + joke.getUpdated_at() + "'," +
                        "'" + joke.getUrl() + "'," +
                        "'" + joke.getValue().replace("'", "''") + "'" +
                        ");";
                try {
                    fileWriter.write(sql + System.lineSeparator());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        }
    }
}

