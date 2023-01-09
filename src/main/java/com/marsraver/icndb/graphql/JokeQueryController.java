package com.marsraver.icndb.graphql;

import com.marsraver.icndb.api.JokeService;
import com.marsraver.icndb.data.Joke;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.Optional;


@Controller
@AllArgsConstructor
public class JokeQueryController {

    private final JokeService jokeService;

    @QueryMapping
    public Iterable<Joke> jokes() {
        return jokeService.findAllJokes();
    }

    @QueryMapping
    public Optional<Joke> jokeById(@Argument String id) {
        return jokeService.findJokeById(id);
    }

    @QueryMapping
    public Iterable<Joke> jokesByCategories(@Argument Collection<Integer> categories) {
        return jokeService.findJokesByCategories(categories);
    }
}
