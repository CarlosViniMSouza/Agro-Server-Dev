package com.qrmenu.qrmenuserver.products;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.qrmenu.qrmenuserver.restaurants.RestaurantService;
import com.qrmenu.qrmenuserver.utils.Utils;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    /*
     * - [x] List All Products of a Restaurant
     * - [x] Register a new Product of a Restaurant
     * - [x] Update a Product of a Restaurant
     * - [x] Delete a Product of a Restaurant
     */

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody ProductModel productModel, HttpServletRequest request) {
        var idRestaurant = request.getAttribute("idRestaurant");
        productModel.setIdRestaurant((UUID) idRestaurant);

        var product = productRepository.save(productModel);

        // calling service to save product in restaurant
        restaurantService.addProductToRestaurant((UUID) idRestaurant, productModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/")
    public List<ProductModel> list(HttpServletRequest request) {
        var idRestaurant = request.getAttribute("idRestaurant");
        var products = this.productRepository.findByIdRestaurant((UUID) idRestaurant);

        return products;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody ProductModel productModel, @PathVariable UUID id,
            HttpServletRequest request) {
        var product = this.productRepository.findById(id).orElse(null);

        // check if product exists or not!
        if (product == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found.");
        }

        var idRestaurant = request.getAttribute("idRestaurant");

        if (!product.getIdRestaurant().equals(idRestaurant)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not allowed to Update the Product.");
        }

        Utils.copyNonNullProperties(productModel, product);

        var productUpdated = this.productRepository.save(product);
        return ResponseEntity.status(HttpStatus.OK).body(productUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id, HttpServletRequest request) {
        var product = this.productRepository.findById(id).orElse(null);

        // check if product exists or not!
        if (product == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found.");
        }

        var idRestaurant = request.getAttribute("idRestaurant");

        if (!product.getIdRestaurant().equals(idRestaurant)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not allowed to Delete the Product.");
        }

        // calling service to remove product in restaurant
        restaurantService.deleteProductToRestaurant((UUID) idRestaurant, product);

        // remove product from Product Table
        this.productRepository.delete(product);

        return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
    }
}
