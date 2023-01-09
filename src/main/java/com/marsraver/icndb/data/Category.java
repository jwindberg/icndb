package com.marsraver.icndb.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Category {
    @Id
    private Integer id;
    private String data;
}
