package com.sattar.newsfeed.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created By Sattar 2/18/2019
 */
public class ArticlesItemRealm extends RealmObject {

    public interface Fields{
        String TITLE = "title";
    }
    @PrimaryKey
    private String title;
    private String publishedAt;
    private String author;
    private String urlToImage;
    private String description;
    private String url;

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
