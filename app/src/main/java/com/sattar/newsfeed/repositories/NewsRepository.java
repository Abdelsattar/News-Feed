package com.sattar.newsfeed.repositories;

import com.sattar.newsfeed.models.news.NewsResponse;
import com.sattar.newsfeed.services.NewsAPIInterface;
import com.sattar.newsfeed.services.RetorfitClient;

import io.reactivex.Observable;

/**
 * Created By Sattar 2/18/2019
 */
public class NewsRepository {


    public Observable<NewsResponse> getNews(String source) {

        NewsAPIInterface articlesApiInterface = RetorfitClient.getClient().create
                (NewsAPIInterface.class);

        return articlesApiInterface.getNews(source);
    }
}
