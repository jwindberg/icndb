package com.marsraver.icndb.api;

import com.marsraver.icndb.data.Category;
import com.marsraver.icndb.data.CategoryRepository;
import com.marsraver.icndb.data.Joke;
import com.marsraver.icndb.data.JokeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class JokeService {

    private static final Random RANDOM = new Random();
    private JokeRepository jokeRepository;
    private CategoryRepository categoryRepository;

    public Collection<Category> findAllCategories() {
        return toList(categoryRepository.findAll());
    }

    public Optional<Category> findCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    public List<Joke> findAllJokes() {
        return toList(jokeRepository.findAll());
    }

    public Optional<Joke> findJokeById(Long id) {
        return jokeRepository.findById(id);
    }

    public Joke randomJoke() {
        List<Joke> allJokes = findAllJokes();
        return allJokes.get(RANDOM.nextInt(allJokes.size() - 1));
    }

    public List<Joke> findJokesByCategories(Collection<Integer> categories) {
        return toList(jokeRepository.findAllByCategory(categories));
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Joke> deleteJoke(Long id) {
        Optional<Joke> jokeOption = findJokeById(id);
        jokeOption.ifPresent(joke -> jokeRepository.delete(joke));
        return jokeOption;
    }

    public Joke createJoke(String guid, Set<Integer> categories, String url, String icon_url, String data) {
        Joke joke = new Joke();
        joke.setGuid(guid);
        if (categories != null) {
            joke.setCategories(new HashSet<>(categoryRepository.findManyById(categories)));
        }
        joke.setUrl(url);
        joke.setIcon_url(icon_url);
        joke.setData(data);
        joke.setCreated_at(currentDateTime());
        return jokeRepository.save(joke);
    }

    public Optional<Joke> updateJoke(Long id, String guid, Set<Integer> categories, String url, String icon_url, String data) {
        Optional<Joke> jokeOption = findJokeById(id);
        jokeOption.ifPresent(joke -> {
            joke.setGuid(guid);
            if (categories != null) {
                joke.setCategories(new HashSet<>(categoryRepository.findManyById(categories)));
            }
            joke.setUrl(url);
            joke.setIcon_url(icon_url);
            joke.setData(data);
            joke.setUpdated_at(currentDateTime());
            jokeRepository.save(joke);
        });
        return jokeOption;
    }

    private String currentDateTime() {
        return ZonedDateTime.now(ZoneOffset.UTC).toString();
    }

    public Category createCategory(String data) {
        Category category = new Category();
        category.setData(data);
        return categoryRepository.save(category);
    }

    public Optional<Category> updateCategory(Integer id, String data) {
        Optional<Category> categoryOption = findCategoryById(id);
        categoryOption.ifPresent(category -> {
            category.setData(data);
            categoryRepository.save(category);
        });
        return categoryOption;
    }

    public Optional<Category> deleteCategory(Integer id) {
        Optional<Category> categoryOption = findCategoryById(id);
        categoryOption.ifPresent(category ->
                categoryRepository.delete(category)
        );
        return categoryOption;
    }
}
