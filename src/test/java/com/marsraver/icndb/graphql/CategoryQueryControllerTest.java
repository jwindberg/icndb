package com.marsraver.icndb.graphql;

import com.marsraver.icndb.api.JokeService;
import com.marsraver.icndb.data.Category;
import com.marsraver.icndb.data.CategoryRepository;
import com.marsraver.icndb.data.JokeRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@GraphQlTest(CategoryQueryController.class)
@Import(JokeService.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryQueryControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private JokeRepository jokeRepository;

    private Category category(Integer id, String data) {
        Category category = new Category();
        category.setId(id);
        category.setData(data);
        return category;
    }

    @Test
    @Order(1)
    void testCategories() {

        when(categoryRepository.findAll()).thenReturn(List.of(category(1, "red"), category(2, "green"), category(3, "blue")));

        // language=GraphQL
        String document = """
                query {
                categories {
                    id
                    data              
                   }
                }
                """;

        graphQlTester.document(document).execute().path("categories").entityList(Category.class).hasSize(3);
    }

    @Test
    @Order(2)
    void testCategory() {

        Category category = category(454, "red");
        when(categoryRepository.findById(454)).thenReturn(Optional.of(category));

        // language=GraphQL
        String document = """
                query {
                category(id: 454) {
                    id
                    data              
                   }
                }
                """;

        graphQlTester.document(document).execute().path("category").entity(Category.class).isEqualTo(category);

    }

    @Test
    @Order(3)
    void testCategoryNotFound() {

        when(categoryRepository.findById(454)).thenReturn(Optional.empty());

        // language=GraphQL
        String document = """
                query {
                category(id: 454) {
                    id
                    data              
                   }
                }
                """;

        graphQlTester.document(document).execute().path("category").valueIsNull();

    }

    @Test
    @Order(4)
    void testCreateCategory() {

        Category category = category(454, "green");
        when(categoryRepository.save(isA(Category.class))).thenReturn(category);

        // language=GraphQL
        String document = """
                    mutation create($data: String) {
                        createCategory(data: $data) {
                            id
                            data
                        }
                    }
                """;

        graphQlTester.document(document).variable("data", "green").execute().path("createCategory").entity(Category.class).isEqualTo(category);

        verify(categoryRepository).save(isA(Category.class));

    }

    @Test
    @Order(5)
    void testUpdateCategory() {

        Category category = category(454, "green");
        when(categoryRepository.findById(454)).thenReturn(Optional.of(category));
        when(categoryRepository.save(isA(Category.class))).thenReturn(category);

        // language=GraphQL
        String document = """
                    mutation update($id: ID, $data: String) {
                        updateCategory(id: $id, data: $data) {
                            id
                            data
                        }
                    }
                """;

        graphQlTester.document(document)
                .variable("id", 454)
                .variable("data", "green")
                .execute().path("updateCategory").entity(Category.class).isEqualTo(category);

        verify(categoryRepository).findById(454);
        verify(categoryRepository).save(isA(Category.class));

    }

    @Test
    @Order(6)
    void testUpdateCategoryNotFound() {

        when(categoryRepository.findById(454)).thenReturn(Optional.empty());

        // language=GraphQL
        String document = """
                    mutation update($id: ID, $data: String) {
                        updateCategory(id: $id, data: $data) {
                            id
                            data
                        }
                    }
                """;

        graphQlTester.document(document)
                .variable("id", 454)
                .variable("data", "green")
                .execute().path("updateCategory").valueIsNull();

        verify(categoryRepository).findById(454);
        verifyNoMoreInteractions(categoryRepository);
    }

}