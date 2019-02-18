package com.sattar.newsfeed.ViewModels;

import android.arch.lifecycle.ViewModel;

import com.sattar.newsfeed.models.ArticlesItemRealm;
import com.sattar.newsfeed.models.news.ArticlesItem;
import com.sattar.newsfeed.models.news.NewsResponse;
import com.sattar.newsfeed.repositories.NewsRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.realm.RealmList;

import static com.sattar.newsfeed.helpers.Constants.KEY_ASSOCIATED_PRESS;
import static com.sattar.newsfeed.helpers.Constants.KEY_THE_NEXT_WEB;

/**
 * Created By Sattar 2/17/2019
 */
public class MainActivityViewModel extends ViewModel {

    NewsRepository newsRepository;

    public MainActivityViewModel() {

        newsRepository = new NewsRepository();
    }

    public Observable<List<ArticlesItem>> getAllNews() {
        return Observable
                .combineLatest(
                        newsRepository.getNews(KEY_THE_NEXT_WEB),
                        newsRepository.getNews(KEY_ASSOCIATED_PRESS),
                        new BiFunction<NewsResponse, NewsResponse, List<ArticlesItem>>() {
                            @Override
                            public List<ArticlesItem> apply(NewsResponse source1, NewsResponse source2) throws Exception {
                                List<ArticlesItem> allArticles = new ArrayList<>();
                                if (source1 != null && !source1.getArticles().isEmpty())
                                    allArticles.addAll(source1.getArticles());
                                if (source2 != null && !source2.getArticles().isEmpty())
                                    allArticles.addAll(source2.getArticles());

                                newsRepository.addArticlesToRealm(allArticles);

                                return getAllArticles();
                            }
                        });
    }

    public List<ArticlesItem> getAllArticles() {
        RealmList<ArticlesItemRealm> realmList = newsRepository.getAllArticlesOffline();
        List<ArticlesItem> articlesList = new ArrayList<>();
        for (ArticlesItemRealm itemRealm : realmList) {
            ArticlesItem articlesItem = new ArticlesItem();
            articlesItem.copyFromRealm(itemRealm);
            articlesList.add(articlesItem);
        }

        return articlesList;
    }
}
