package com.sattar.newsfeed.ViewModels;

import android.arch.lifecycle.ViewModel;

import com.sattar.newsfeed.models.ArticlesItemRealm;
import com.sattar.newsfeed.repositories.NewsRepository;

/**
 * Created By Sattar 2/17/2019
 */
public class NewsDetailsViewModel extends ViewModel {

    NewsRepository newsRepository;

    public NewsDetailsViewModel() {

        newsRepository = new NewsRepository();
    }

    public ArticlesItemRealm getArticle(String title) {
        return newsRepository.getArticle(title);
    }
}
