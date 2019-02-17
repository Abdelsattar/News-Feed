package com.sattar.newsfeed.services;

import com.sattar.newsfeed.models.news.NewsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created By Sattar 2/17/2019
 */
public interface NewsAPIInterface {

    @GET("articles")
    Observable<NewsResponse> getNews(
            @Query("source") String source);
}
