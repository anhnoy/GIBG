package com.Auton.gibg.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private Long id;

    @NotNull
    @Column(name = "pro_name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "image_pro")
    private String imagePro;

    @Column(name = "image_preview")
    private String imagePreview;

    @Column(name = "shope_id")
    private Long shopId;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "hashtag_id")
    private Long hashtag_id;

    @Column(name = "createat")
    private LocalDateTime createAt;

    // getters and setters
}