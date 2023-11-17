package com.qrmenu.qrmenuserver.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<ProductModel, UUID> {
    ProductModel findByName(String name);

    List<ProductModel> findByIdRestaurant(UUID idRestaurant);
}
