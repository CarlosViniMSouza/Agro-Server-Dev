package com.qrmenu.qrmenuserver.consumers;

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
    public ResponseEntity login(@RequestBody ConsumerModel consumerModel) {
        var consumer = this.consumerRepository.findByName(consumerModel.getName());

        if (consumer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Consumer does not exist");
        }

        var passwordVerify = BCrypt.verifyer().verify(consumerModel.getPassword().toCharArray(),
                consumer.getPassword());

        if (!passwordVerify.verified) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid name or password");
        }

        return ResponseEntity.status(HttpStatus.OK).body(consumer);
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
