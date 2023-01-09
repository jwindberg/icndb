package com.marsraver.icndb.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GetChuck {


    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Joke> jokes = new HashMap<>();
        ChuckClient chuckClient = new ChuckClient();
        Joke joke = null;
        boolean running = true;
        int count = 0;
        while (running) {
            joke = chuckClient.getRandomJoke();
            count++;
            if (!jokes.containsKey(joke.getGuid())) {
                jokes.put(joke.getGuid(), joke);
                count = 0;
            }
            running = count < 10;

            if (jokes.size() % 100 == 0) {
                System.out.println("Jokes found: " + jokes.size());
            }

        }

        Collection<Joke> values = jokes.values();
        objectMapper.writeValue(new File("jokes.json"), values);
        System.out.println("Jokes found: " + jokes.size());
    }

    public static class ChuckClient {
        private RestTemplate restTemplate = new RestTemplate();

        public Joke getRandomJoke() {
            // https://api.chucknorris.io/jokes/random
            ResponseEntity<Joke> responseEntity = restTemplate.getForEntity("https://api.chucknorris.io/jokes/random", Joke.class);
            return responseEntity.getBody();
        }
    }

    @Data
    public static class Joke {
        private Set<String> categories;
        private String created_at;
        private String icon_url;
        private Integer id;
        private String guid;
        private Long newId;
        private String updated_at;
        private String url;
        private String value;
    }
}
