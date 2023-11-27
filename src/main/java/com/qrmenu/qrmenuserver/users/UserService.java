package com.qrmenu.qrmenuserver.users;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.qrmenu.qrmenuserver.news.NewsModel;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    public UserModel addNewsToUser(@PathVariable UUID id, NewsModel newsModel) {
        UserModel userModel = this.userRepository.findById((UUID) id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        userModel.getNews().add(newsModel);

        return userRepository.save(userModel);
    }

    public String deleteNewsToUser(@PathVariable UUID id, NewsModel newsModel) {
        UserModel userModel = this.userRepository.findById((UUID) id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        userModel.getNews().remove(newsModel);

        return "News removed from User!";
    }
}
