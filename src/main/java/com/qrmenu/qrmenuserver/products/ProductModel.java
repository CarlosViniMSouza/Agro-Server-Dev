package com.qrmenu.qrmenuserver.products;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "table_product")
public class ProductModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID idRestaurant;

    @Column(unique = true)
    private String name;

    private String description;
    private String category;
    private String urlPhoto;

    private Boolean availability;

    private Float price;
}
