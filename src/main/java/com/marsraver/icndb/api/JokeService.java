package com.marsraver.icndb.api;

import com.marsraver.icndb.data.Category;
import com.marsraver.icndb.data.CategoryRepository;
import com.marsraver.icndb.data.Joke;
import com.marsraver.icndb.data.JokeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class JokeService {
    private JokeRepository jokeRepository;
    private CategoryRepository categoryRepository;

    public Collection<Category> findAllCategories() {
        return toList(categoryRepository.findAll());
    }

    public Optional<Category> findCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    public Collection<Joke> findAllJokes() {
        return toList(jokeRepository.findAll());
    }

    public Optional<Joke> findJokeById(String id) {
        return jokeRepository.findById(id);
    }

    public List<Joke> findJokesByCategories(Collection<Integer> categories) {
        return toList(jokeRepository.findAllByCategory(categories));
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
