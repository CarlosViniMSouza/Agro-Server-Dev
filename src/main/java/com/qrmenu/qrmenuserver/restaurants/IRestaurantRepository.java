package com.qrmenu.qrmenuserver.restaurants;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRestaurantRepository extends JpaRepository<RestaurantModel, UUID> {
    RestaurantModel findByName(String name);
}
