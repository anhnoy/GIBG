package com.Auton.gibg.entity.CategoryEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "tb_category")
@Getter
@Setter

public class ProductCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long category_id;

    @Column(name = "category_name")
    private String category_name;

    @Column(name = "description")
    private String description;

    @Column(name = "parentId")
    private Long parentId;


}
