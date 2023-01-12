package com.marsraver.icndb.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("select c from Category c  where c.id in (:categories)")
    List<Category> findManyById(Collection<Integer> categories);
}
