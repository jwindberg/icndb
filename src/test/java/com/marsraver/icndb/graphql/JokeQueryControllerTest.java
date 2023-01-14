package com.marsraver.icndb.graphql;

import com.marsraver.icndb.api.JokeService;
import com.marsraver.icndb.data.Category;
import com.marsraver.icndb.data.CategoryRepository;
import com.marsraver.icndb.data.Joke;
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

@GraphQlTest(JokeQueryController.class)
@Import(JokeService.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JokeQueryControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private JokeRepository jokeRepository;

    private Joke joke(Long id, String guid, String data) {
        Joke joke = new Joke();
        joke.setId(id);
        joke.setGuid(guid);
        joke.setData(data);
        return joke;
    }

    @Test
    @Order(1)
    void testJokes() {

        when(jokeRepository.findAll()).thenReturn(List.of(joke(1L, "abc", "red"), joke(2L, "xyz", "green")));

        // language=GraphQL
        String document = """
                query {
                jokes {
                    id
                    guid
                    data              
                   }
                }
                """;

        graphQlTester.document(document).execute().path("jokes").entityList(Category.class).hasSize(2);

    }

    @Test
    @Order(2)
    void testJoke() {

        Joke joke = joke(4L, "abc", "red");
        when(jokeRepository.findById(4L)).thenReturn(Optional.of(joke));

        // language=GraphQL
        String document = """
                query {
                joke(id: 4) {
                    id
                    guid
                    data              
                   }
                }
                """;

        graphQlTester.document(document).execute().path("joke").entity(Joke.class).isEqualTo(joke);

    }

    @Test
    @Order(3)
    void testJokeNotFound() {

        when(jokeRepository.findById(4L)).thenReturn(Optional.empty());

        // language=GraphQL
        String document = """
                query {
                joke(id: 4) {
                    id
                    guid
                    data              
                   }
                }
                """;

        graphQlTester.document(document).execute().path("joke").valueIsNull();
    }

    @Test
    @Order(4)
    void testCreateJoke() {

        Joke joke = joke(4L, "abc", "red");
        when(jokeRepository.save(isA(Joke.class))).thenReturn(joke);

        // language=GraphQL
        String document = """
                    mutation create($guid: String, $data: String) {
                        createJoke(guid: $guid, data: $data) {
                            id
                            guid
                            data
                        }
                    }
                """;

        graphQlTester.document(document).variable("guid", "abc").variable("data", "red").execute().path("createJoke").entity(Joke.class).isEqualTo(joke);

        verify(jokeRepository).save(isA(Joke.class));
    }

    @Test
    @Order(5)
    void testUpdateJoke() {

        Joke joke = joke(4L, "abc", "red");
        when(jokeRepository.findById(4L)).thenReturn(Optional.of(joke));
        when(jokeRepository.save(isA(Joke.class))).thenReturn(joke);

        // language=GraphQL
        String document = """
                    mutation update($id: ID, $guid: String, $data: String) {
                        updateJoke(id: $id, guid: $guid, data: $data) {
                            id
                            guid
                            data
                        }
                    }
                """;

        graphQlTester.document(document).variable("id", 4L).variable("guid", "abc").variable("data", "red").execute().path("updateJoke").entity(Joke.class).matches(j -> j.getId().equals(4L) && j.getGuid().equals("abc") && j.getData().equals("red"));

        verify(jokeRepository).findById(4L);
        verify(jokeRepository).save(isA(Joke.class));
    }

    @Test
    @Order(6)
    void testUpdateJokeNotFound() {

        when(jokeRepository.findById(4L)).thenReturn(Optional.empty());

        // language=GraphQL
        String document = """
                    mutation update($id: ID, $guid: String, $data: String) {
                        updateJoke(id: $id, guid: $guid, data: $data) {
                            id
                            guid
                            data
                        }
                    }
                """;

        graphQlTester.document(document)
                .variable("id", 4L)
                .variable("guid", "abc")
                .variable("data", "red")
                .execute().path("updateJoke").valueIsNull();

        verify(jokeRepository).findById(4L);
        verifyNoMoreInteractions(jokeRepository);
    }

}

