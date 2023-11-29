package com.qrmenu.qrmenuserver.news;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "table_news")
public class NewsModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID idUser;

    @Column(length = 32, unique = true)
    private String title;

    @Column(length = 64)
    private String caption;

    @Column(length = 256)
    private String paragraphInit;

    @Column(length = 256)
    private String paragraphMiddle;

    @Column(length = 256)
    private String paragraphEnd;
}
