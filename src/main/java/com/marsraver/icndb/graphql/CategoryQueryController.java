package com.marsraver.icndb.graphql;

import com.marsraver.icndb.api.JokeService;
import com.marsraver.icndb.data.Category;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class CategoryQueryController {

    private final JokeService jokeService;

    @QueryMapping
    public Iterable<Category> categories() {
        return jokeService.findAllCategories();
    }

    @QueryMapping
    public Optional<Category> category(@Argument Integer id) {
        return jokeService.findCategoryById(id);
    }

    @MutationMapping
    public Category createCategory(@Argument String data) {
        return jokeService.createCategory(data);
    }

    @MutationMapping
    public Optional<Category> updateCategory(@Argument Integer id, @Argument String data) {
        return jokeService.updateCategory(id, data);
    }

    @MutationMapping
    public Optional<Category> deleteCategory(@Argument Integer id) {
        return jokeService.deleteCategory(id);
    }
}
