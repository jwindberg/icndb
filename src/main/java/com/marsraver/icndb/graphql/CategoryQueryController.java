package com.marsraver.icndb.graphql;

import com.marsraver.icndb.api.JokeService;
import com.marsraver.icndb.data.Category;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class CategoryQueryController {

    private final JokeService jokeService;

    @QueryMapping
    public Iterable<Category> categories() {
        return jokeService.findAllCategories();
    }
}
