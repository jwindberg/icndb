package com.marsraver.icndb.api;

import com.marsraver.icndb.data.Joke;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Joke> jokeById(@PathVariable Long id) {
        return responseOrNotFound(jokeService.findJokeById(id));
    }

    @GetMapping("joke/random")
    public ResponseEntity<Joke> randomJoke() {
        return ResponseEntity.ok(jokeService.randomJoke());
    }

    @PostMapping("joke")
    public ResponseEntity<Joke> createJoke(@RequestBody JokeRequest jokeRequest) {
        return ResponseEntity.ok(jokeService.createJoke(
                jokeRequest.getGuid(),
                jokeRequest.getCategories(),
                jokeRequest.getUrl(),
                jokeRequest.getIcon_url(),
                jokeRequest.getData()));
    }

    @PutMapping("joke/{id}")
    public ResponseEntity<Joke> updateJoke(@RequestBody JokeRequest jokeRequest, @PathVariable Long id) {
        return responseOrNotFound(jokeService.updateJoke(id, jokeRequest.getGuid(),
                jokeRequest.getCategories(),
                jokeRequest.getUrl(),
                jokeRequest.getIcon_url(),
                jokeRequest.getData()));
    }

    @DeleteMapping("joke/{id}")
    public ResponseEntity<Joke> deleteJoke(@PathVariable Long id) {
        return responseOrNotFound(jokeService.deleteJoke(id));
    }

    private <B> ResponseEntity<B> responseOrNotFound(Optional<B> optional) {
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Data
    public static class JokeRequest {
        private String guid;
        private Set<Integer> categories;
        private String url;
        String icon_url;
        private String data;
    }

}
