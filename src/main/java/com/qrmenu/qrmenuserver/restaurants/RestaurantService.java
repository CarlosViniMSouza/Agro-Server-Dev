package com.qrmenu.qrmenuserver.restaurants;

import com.qrmenu.qrmenuserver.products.ProductModel;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class RestaurantService {

    @Autowired
    private IRestaurantRepository restaurantRepository;

    public RestaurantModel addProductToRestaurant(@PathVariable UUID id, ProductModel productModel) {
        RestaurantModel restaurantModel = this.restaurantRepository.findById((UUID) id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found!"));
        restaurantModel.getProducts().add(productModel);

        return restaurantRepository.save(restaurantModel);
    }

    public String deleteProductToRestaurant(@PathVariable UUID id, ProductModel productModel) {
        RestaurantModel restaurantModel = this.restaurantRepository.findById((UUID) id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found!"));
        restaurantModel.getProducts().remove(productModel);

        return "Product removed from List";
    }
}
