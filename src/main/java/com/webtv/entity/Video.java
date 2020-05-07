package com.webtv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String filename;

    @Enumerated(EnumType.ORDINAL)
    private VideoStatus status = VideoStatus.CREATED;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User author;

    public Long getId() {
        return id;
    }

    public Video setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public User getAuthor() {
        return author;
    }

    public Video setAuthor(User author) {
        this.author = author;
        return this;
    }

    public VideoStatus getStatus() {
        return this.status;
    }

    public void setStatus(VideoStatus status) {
        this.status = status;
    }
}