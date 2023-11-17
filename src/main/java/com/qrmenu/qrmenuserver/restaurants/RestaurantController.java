package com.qrmenu.qrmenuserver.restaurants;

import com.qrmenu.qrmenuserver.utils.Utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    /*
     * - [x] Show all infos of a Restaurant
     * - [x] Register a new Restaurant
     * - [x] Update a Restaurant
     * - [x] Delete a Restaurant
     */

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody RestaurantModel restaurantModel) {
        var restaurant = this.restaurantRepository.findByName(restaurantModel.getName());

        // check if restaurant exists or not!
        if (restaurant != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Restaurant already exists");
        }

        var passwordToHash = BCrypt.withDefaults().hashToString(12, restaurantModel.getPassword().toCharArray());
        restaurantModel.setPassword(passwordToHash);

        var restaurantCreated = this.restaurantRepository.save(restaurantModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity list(@PathVariable UUID id, HttpServletRequest request) {
        var restaurant = this.restaurantRepository.findById(id).orElse(null);

        // check if restaurant exists or not!
        if (restaurant == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Restaurant not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    @GetMapping("/{id}/products/")
    public ResponseEntity showProducts(@PathVariable UUID id, HttpServletRequest request) {
        var restaurant = this.restaurantRepository.findById(id).orElse(null);

        // check if restaurant exists or not!
        if (restaurant == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Restaurant not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(restaurant.getProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody RestaurantModel restaurantModel, @PathVariable UUID id,
            HttpServletRequest request) {
        var restaurant = this.restaurantRepository.findById(id).orElse(null);

        // check if restaurant exists or not!
        if (restaurant == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Restaurant not found");
        }

        Utils.copyNonNullProperties(restaurantModel, restaurant);

        var restaurantUpdated = this.restaurantRepository.save(restaurant);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id, HttpServletRequest request) {
        var restaurant = this.restaurantRepository.findById(id).orElse(null);

        // check if restaurant exists or not!
        if (restaurant == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Restaurant not found");
        }

        this.restaurantRepository.delete(restaurant);

        return ResponseEntity.status(HttpStatus.OK).body("Restaurant deleted");
    }
}
