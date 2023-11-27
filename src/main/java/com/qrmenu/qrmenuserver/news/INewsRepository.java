package com.qrmenu.qrmenuserver.news;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface INewsRepository extends JpaRepository<NewsModel, UUID> {
    NewsModel findByTitle(String title);

    List<NewsModel> findByIdUser(UUID idUser);
}
