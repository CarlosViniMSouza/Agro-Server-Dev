package com.qrmenu.qrmenuserver.users;

import com.qrmenu.qrmenuserver.utils.Utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists!");
        }

        var passwordToHash = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordToHash);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authUser(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exists!");
        }

        var passwordVerify = BCrypt.verifyer()
                .verify(userModel.getPassword().toCharArray(), user.getPassword());

        if (!passwordVerify.verified) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid name or password");
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable UUID id) {
        var user = this.userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User dont exists!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/{id}/news")
    public ResponseEntity<Object> showNewsByUser(@PathVariable UUID id) {
        var user = this.userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User dont exists!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(user.getNews());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody UserModel userModel, @PathVariable UUID id) {
        var user = this.userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User dont exists!");
        }

        Utils.copyNonNullProperties(userModel, user);

        var userUpdated = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
        var user = this.userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User dont exists!");
        }

        this.userRepository.delete(user);

        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }
}
