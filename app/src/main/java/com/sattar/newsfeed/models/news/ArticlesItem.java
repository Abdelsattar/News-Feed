package com.sattar.newsfeed.models.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sattar.newsfeed.models.ArticlesItemRealm;


public class ArticlesItem implements Parcelable {

    @SerializedName("publishedAt")
    private String publishedAt;

    @SerializedName("author")
    private String author;

    @SerializedName("urlToImage")
    private String urlToImage;

    @SerializedName("description")
    private String description;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;


    public ArticlesItem() {
    }

    protected ArticlesItem(Parcel in) {
        publishedAt = in.readString();
        author = in.readString();
        urlToImage = in.readString();
        description = in.readString();
        title = in.readString();
        url = in.readString();
    }

    public static final Creator<ArticlesItem> CREATOR = new Creator<ArticlesItem>() {
        @Override
        public ArticlesItem createFromParcel(Parcel in) {
            return new ArticlesItem(in);
        }

        @Override
        public ArticlesItem[] newArray(int size) {
            return new ArticlesItem[size];
        }
    };

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

    @Override
    public String toString() {
        return
                "ArticlesItem{" +
                        "publishedAt = '" + publishedAt + '\'' +
                        ",author = '" + author + '\'' +
                        ",urlToImage = '" + urlToImage + '\'' +
                        ",description = '" + description + '\'' +
                        ",title = '" + title + '\'' +
                        ",url = '" + url + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(publishedAt);
        parcel.writeString(author);
        parcel.writeString(urlToImage);
        parcel.writeString(description);
        parcel.writeString(title);
        parcel.writeString(url);
    }

    public void copyToRealm(ArticlesItemRealm itemRealm) {
        itemRealm.setAuthor(getAuthor());
        itemRealm.setDescription(getDescription());
        itemRealm.setPublishedAt(getPublishedAt());
        itemRealm.setUrl(getUrl());
        itemRealm.setUrlToImage(getUrlToImage());
    }

    public void copyFromRealm(ArticlesItemRealm itemRealm) {
        setTitle(itemRealm.getTitle());
        setAuthor(itemRealm.getAuthor());
        setDescription(itemRealm.getDescription());
        setPublishedAt(itemRealm.getPublishedAt());
        setUrl(itemRealm.getUrl());
        setUrlToImage(itemRealm.getUrlToImage());
    }
}