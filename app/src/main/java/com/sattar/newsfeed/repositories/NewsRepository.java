package com.sattar.newsfeed.repositories;

import com.sattar.newsfeed.models.ArticlesItemRealm;
import com.sattar.newsfeed.models.news.ArticlesItem;
import com.sattar.newsfeed.models.news.NewsResponse;
import com.sattar.newsfeed.services.NewsAPIInterface;
import com.sattar.newsfeed.services.RetorfitClient;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created By Sattar 2/18/2019
 */
public class NewsRepository {


    public NewsRepository() {
    }

    public Observable<NewsResponse> getNews(String source) {
        NewsAPIInterface articlesApiInterface = RetorfitClient.getClient().create
                (NewsAPIInterface.class);

        return articlesApiInterface.getNews(source);
    }

    public ArticlesItemRealm getArticle(String title) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(ArticlesItemRealm.class)
                .equalTo(ArticlesItemRealm.Fields.TITLE, title).findFirst();

    }

    public void addArticlesToRealm(List<ArticlesItem> itemList) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
//        Log.e("addArticlesToRealm", itemList.size() + "");
        for (ArticlesItem item : itemList) {
            ArticlesItemRealm itemRealm = realm.where(ArticlesItemRealm.class)
                    .equalTo(ArticlesItemRealm.Fields.TITLE, item.getTitle())
                    .findFirst();
            if (itemRealm == null) {
                itemRealm = realm.createObject(ArticlesItemRealm.class, item.getTitle());
            }
            item.copyToRealm(itemRealm);
        }
        realm.commitTransaction();

    }

    public RealmList<ArticlesItemRealm> getAllArticlesOffline() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<ArticlesItemRealm> realmResults = realm.where(ArticlesItemRealm.class).findAll();

        RealmList<ArticlesItemRealm> list = new RealmList<>();
        if (realmResults != null) {
            list.addAll(realmResults.subList(0, realmResults.size()));
//            Log.e("getAllArticlesOffline", realmResults.size() + "");

        }
        return list;
    }


}
