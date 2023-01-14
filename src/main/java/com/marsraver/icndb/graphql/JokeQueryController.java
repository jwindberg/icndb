package com.marsraver.icndb.graphql;

import com.marsraver.icndb.api.JokeService;
import com.marsraver.icndb.data.Joke;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;


@Controller
@AllArgsConstructor
public class JokeQueryController {

    private final JokeService jokeService;

    @QueryMapping
    public Iterable<Joke> jokes() {
        return jokeService.findAllJokes();
    }

    @QueryMapping
    public Optional<Joke> joke(@Argument Long id) {
        return jokeService.findJokeById(id);
    }

    @QueryMapping
    public Iterable<Joke> jokesByCategories(@Argument Collection<Integer> categories) {
        return jokeService.findJokesByCategories(categories);
    }

    @MutationMapping
    public Joke createJoke(@Argument String guid, @Argument Set<Integer> categories, @Argument String url, @Argument String icon_url, @Argument String data) {
        return jokeService.createJoke(guid, categories, url, icon_url, data);
    }

    @MutationMapping
    public Optional<Joke> updateJoke(@Argument Long id, @Argument String guid, @Argument Set<Integer> categories,
                                     @Argument String url, @Argument String icon_url, @Argument String data) {
        return jokeService.updateJoke(id, guid, categories, url, icon_url, data);
    }

    @MutationMapping
    public Optional<Joke> deleteJoke(@Argument Long id) {
        return jokeService.deleteJoke(id);
    }

}
