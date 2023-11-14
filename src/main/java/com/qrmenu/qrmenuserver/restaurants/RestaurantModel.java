package com.qrmenu.qrmenuserver.restaurants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.UUID;

import com.qrmenu.qrmenuserver.products.ProductModel;

import lombok.Data;

@Data
@Entity(name = "table_restaurant")
public class RestaurantModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String name;

    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductModel> products;
    // = new ArrayList<>();
}
