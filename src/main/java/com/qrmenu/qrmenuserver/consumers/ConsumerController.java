package com.qrmenu.qrmenuserver.consumers;

import com.qrmenu.qrmenuserver.consumers.login.LoginRequest;
import com.qrmenu.qrmenuserver.consumers.login.LoginResponse;
import com.qrmenu.qrmenuserver.utils.Utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/consumers")
public class ConsumerController {
    /*
     * - [x] Register a new Consumer
     * - [ ] Login of Consumer
     * - [x] Update a Consumer
     * - [x] Delete a Consumer
     */

    @Autowired
    private IConsumerRepository consumerRepository;

    private ConsumerService consumerService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody ConsumerModel consumerModel) {
        var consumer = this.consumerRepository.findByName(consumerModel.getName());

        // check if consumer exists or not!
        if (consumer != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Consumer already exists");
        }

        var passwordToHash = BCrypt.withDefaults().hashToString(12, consumerModel.getPassword().toCharArray());
        consumerModel.setPassword(passwordToHash);

        var consumerCreated = this.consumerRepository.save(consumerModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(consumerCreated);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        ConsumerModel consumer = new ConsumerModel();
        consumer.setName(loginRequest.getUsername());
        consumer.setPassword(loginRequest.getPassword());

        boolean loginSuccess = consumerService.loginConsumer(consumer);

        LoginResponse loginResponse = new LoginResponse();

        if (loginSuccess) {
            loginResponse.setSuccess(true);
            loginResponse.setMessage("Login successful");
        } else {
            loginResponse.setSuccess(false);
            loginResponse.setMessage("Invalid username or password");
        }

        return loginResponse;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody ConsumerModel consumerModel,
            @PathVariable UUID id,
            HttpServletRequest request) {
        var consumer = this.consumerRepository.findById(id).orElse(null);

        // check if consumer exists or not!
        if (consumer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Consumer not found");
        }

        Utils.copyNonNullProperties(consumerModel, consumer);

        var consumerUpdated = this.consumerRepository.save(consumer);
        return ResponseEntity.status(HttpStatus.OK).body(consumerUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id, HttpServletRequest request) {
        var consumer = this.consumerRepository.findById(id).orElse(null);

        // check if consumer exists or not!
        if (consumer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Consumer not found");
        }

        this.consumerRepository.delete(consumer);

        return ResponseEntity.status(HttpStatus.OK).body("Consumer deleted");
    }
}
