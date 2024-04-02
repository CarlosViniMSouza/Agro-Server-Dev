package com.qrmenu.qrmenuserver.news;

import com.qrmenu.qrmenuserver.users.UserService;
import com.qrmenu.qrmenuserver.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
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

@SuppressWarnings("null")
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private INewsRepository newsRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody NewsModel newsModel, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        newsModel.setIdUser((UUID) idUser);

        var news = newsRepository.save(newsModel);

        // calling service to save news in user
        userService.addNewsToUser((UUID) idUser, newsModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(news);
    }

    @GetMapping("/")
    public List<NewsModel> getNews(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var news = this.newsRepository.findByIdUser((UUID) idUser);

        return news;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNews(@RequestBody NewsModel newsModel, @PathVariable UUID id,
            HttpServletRequest request) {
        var news = this.newsRepository.findById(id).orElse(null);

        // check if news exists or not!
        if (news == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("News not found.");
        }

        var idUser = request.getAttribute("idUser");

        if (!news.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not allowed to update the News!");
        }

        Utils.copyNonNullProperties(newsModel, news);

        var newsUpdated = this.newsRepository.save(news);
        return ResponseEntity.status(HttpStatus.OK).body(newsUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNews(@PathVariable UUID id, HttpServletRequest request) {
        var news = this.newsRepository.findById(id).orElse(null);

        // check if news exists or not!
        if (news == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("News not found.");
        }

        var idUser = request.getAttribute("idUser");

        if (!news.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not allowed to delete the News.");
        }

        // calling service to remove news in User
        userService.deleteNewsToUser((UUID) idUser, news);

        // remove news from news Table
        this.newsRepository.delete(news);

        return ResponseEntity.status(HttpStatus.OK).body("News deleted.");
    }
}
