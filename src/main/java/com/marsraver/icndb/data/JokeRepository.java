package com.marsraver.icndb.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

;

public interface JokeRepository extends CrudRepository<Joke, String> {

    @Query("select j from Joke j join j.categories category where category.id in (:categories)")
    Iterable<Joke> findAllByCategory(Collection<Integer> categories);
}
