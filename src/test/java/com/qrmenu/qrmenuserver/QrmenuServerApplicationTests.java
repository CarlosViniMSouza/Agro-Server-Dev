package com.qrmenu.qrmenuserver;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.qrmenu.qrmenuserver.consumers.ConsumerController;
import com.qrmenu.qrmenuserver.consumers.ConsumerModel;
import com.qrmenu.qrmenuserver.products.ProductController;
import com.qrmenu.qrmenuserver.products.ProductModel;
import com.qrmenu.qrmenuserver.restaurants.RestaurantController;
import com.qrmenu.qrmenuserver.restaurants.RestaurantModel;

@SpringBootTest
class QrmenuServerApplicationTests {

	@Test
	void createConsumer() throws Exception {
		try {
			ConsumerModel consumerModel = new ConsumerModel();
			ConsumerController consumerController = new ConsumerController();

			consumerModel.setName("John Doe");
			consumerModel.setEmail("email@example.com");
			consumerModel.setPassword("password");

			ResponseEntity consumerCreated = consumerController.register(consumerModel);

			assertNotNull(consumerCreated);
			assertNotNull(consumerCreated.getBody());
		} catch (Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}
	}

	@Test
	void createRestaurant() throws Exception {
		try {
			RestaurantModel restaurantModel = new RestaurantModel();
			RestaurantController restaurantController = new RestaurantController();

			restaurantModel.setName("Restaurant Num 01");
			restaurantModel.setEmail("email@example.com");
			restaurantModel.setPassword("password");

			ResponseEntity restaurantCreated = restaurantController.create(restaurantModel);

			assertNotNull(restaurantCreated);
			assertNotNull(restaurantCreated.getBody());
		} catch (Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}
	}

	@Test
	void createProduct() throws Exception {
		try {
			ProductModel productModel = new ProductModel();
			ProductController productController = new ProductController();

			productModel.setName("Product Num 01");
			productModel.setPrice(19.99f);
			productModel.setCategory("category");
			productModel.setAvailability(true);
			productModel.setDescription("description with details ...");
			productModel.setUrlPhoto("http://link-image");

			ResponseEntity productCreated = productController.create(productModel, null);

			assertNotNull(productCreated);
			assertNotNull(productCreated.getBody());
		} catch (Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}
	}
}
