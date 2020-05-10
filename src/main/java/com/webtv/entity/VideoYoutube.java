package com.webtv.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "video_youtube")
public class VideoYoutube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Lob
    private String description;

    @Column(name = "tags")
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @JoinTable(name = "tags", joinColumns = @JoinColumn(name = "video_id"))
    private Set<String> tags;

    @ApiModelProperty(hidden = true)
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false)
    private Video video;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Video getVideo() {
        return video;
    }

    @ApiModelProperty(hidden = true)
    public void setVideo(Video video) {
        this.video = video;
    }

    public void addTag(String tag) {
        if(null == this.tags) {
            tags = new HashSet<>();
        }
        this.tags.add(tag);
    }
}