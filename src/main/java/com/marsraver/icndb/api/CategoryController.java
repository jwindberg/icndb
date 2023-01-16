package com.marsraver.icndb.api;

import com.marsraver.icndb.data.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class CategoryController {

    private JokeService jokeService;

    @GetMapping("categories")
    public ResponseEntity<Collection<Category>> allCategories() {
        return ResponseEntity.ok(jokeService.findAllCategories());
    }

    @GetMapping("category/{id}")
    public ResponseEntity<Category> categoryById(@PathVariable Integer id) {
        return responseOrNotFound(jokeService.findCategoryById(id));
    }

    @PostMapping("category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(jokeService.createCategory(categoryRequest.getData()));
    }

    @PutMapping("category/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable Integer id) {
        return responseOrNotFound(jokeService.updateCategory(id, categoryRequest.getData()));
    }

    @DeleteMapping("category/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Integer id) {
        return responseOrNotFound(jokeService.deleteCategory(id));
    }

    private <B> ResponseEntity<B> responseOrNotFound(Optional<B> optional) {
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Data
    public static class CategoryRequest {
        private String data;
    }
}