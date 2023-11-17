package com.qrmenu.qrmenuserver.restaurants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRestaurantRepository extends JpaRepository<RestaurantModel, UUID> {
    RestaurantModel findByName(String name);
}
