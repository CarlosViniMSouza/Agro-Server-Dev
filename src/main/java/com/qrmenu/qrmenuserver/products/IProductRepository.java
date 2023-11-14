package com.qrmenu.qrmenuserver.products;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IProductRepository extends JpaRepository<ProductModel, UUID> {
    ProductModel findByName(String name);

    List<ProductModel> findByIdRestaurant(UUID idRestaurant);
}
