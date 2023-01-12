package com.marsraver.icndb.data;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.Set;

@Data
@Entity
public class Joke {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "joke_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "5454"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
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
