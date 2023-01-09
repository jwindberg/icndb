package com.marsraver.icndb.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Joke {
    @Id
    private Long id;
    private String guid;
    @ManyToMany
    private Set<Category> categories;
    private String created_at;
    private String icon_url;
    private String updated_at;
    private String url;
    @Column(length = 1000)
    private String data;

}
