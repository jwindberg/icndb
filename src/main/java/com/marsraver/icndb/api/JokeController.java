package com.marsraver.icndb.api;

import com.marsraver.icndb.data.Category;
import com.marsraver.icndb.data.Joke;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
public class JokeController {

    private JokeService jokeService;

    @GetMapping("jokes")
    public ResponseEntity<Collection<Joke>> allJokesByCategory(@RequestParam(value = "category", required = false) Set<Integer> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.ok(jokeService.findAllJokes());
        } else {
            return ResponseEntity.ok(jokeService.findJokesByCategories(categories));
        }
    }

    @GetMapping("joke/{id}")
    public ResponseEntity<Joke> jokeById(@PathVariable Integer id) {
        Optional<Joke> joke = jokeService.findJokeById(id);
        return joke.isPresent() ? ResponseEntity.ok(joke.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("categories")
    public ResponseEntity<Collection<Category>> allCategories() {
        return ResponseEntity.ok(jokeService.findAllCategories());
    }

    @GetMapping("category/{id}")
    public ResponseEntity<Category> categoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(jokeService.findCategoryById(id).orElse(null));
    }

}
