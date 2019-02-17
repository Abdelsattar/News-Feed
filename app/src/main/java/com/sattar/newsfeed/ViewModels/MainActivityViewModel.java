package com.sattar.newsfeed.ViewModels;

import android.arch.lifecycle.ViewModel;

import com.sattar.newsfeed.models.news.NewsResponse;
import com.sattar.newsfeed.repositories.NewsRepository;

import io.reactivex.Observable;

/**
 * Created By Sattar 2/17/2019
 */
public class MainActivityViewModel extends ViewModel {

    NewsRepository newsRepository;

    public MainActivityViewModel() {

        newsRepository = new NewsRepository();
    }

    public Observable<NewsResponse> getNews(String source) {
        return newsRepository.getNews(source);
    }
}
