package com.sattar.newsfeed.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sattar.newsfeed.R;
import com.sattar.newsfeed.ViewModels.MainActivityViewModel;
import com.sattar.newsfeed.models.news.ArticlesItem;
import com.sattar.newsfeed.views.adapters.NewsFeedAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private RecyclerView rvNews;
    private NewsFeedAdapter mAdapter;
    private List<ArticlesItem> articlesItemList;

    MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initScreen();
    }

    void initScreen() {

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        rvNews = findViewById(R.id.rvNews);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupNewsRecyclerView();

//        mViewModel.getNews("the-next-web")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
//                .subscribe(new Observer<NewsResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                        Log.e("OnSub", "Subcribed");
//                    }
//
//                    @Override
//                    public void onNext(NewsResponse newsResponse) {
//                        Log.e("onNext the next", new Gson().toJson(newsResponse));
//                        Log.e("onNext the next", newsResponse.getArticles().size() + "");
//                        if (mAdapter != null) {
//                            mAdapter.addData(newsResponse.getArticles());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("onError", e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//        mViewModel.getNews("associated-press")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
//                .subscribe(new Observer<NewsResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                        Log.e("OnSub", "Subcribed");
//                    }
//
//                    @Override
//                    public void onNext(NewsResponse newsResponse) {
//                        Log.e("onNext the next", new Gson().toJson(newsResponse));
//                        Log.e("onNext the next", newsResponse.getArticles().size() + "");
//
//                        if (mAdapter != null) {
//                            mAdapter.addData(newsResponse.getArticles());
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("onError", e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        getAllNews();
    }

    void getAllNews() {
        mViewModel.getAllNews().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .subscribe(new Observer<List<ArticlesItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("On Next", "on subscribe");
                    }

                    @Override
                    public void onNext(List<ArticlesItem> articlesItems) {
                        Log.e("On Next", articlesItems.size() + "");
                        mAdapter.addData(articlesItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getLocalizedMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void setupNewsRecyclerView() {
        articlesItemList = new ArrayList<>();
        mAdapter = new NewsFeedAdapter(articlesItemList);
        mAdapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewsDetailsActivity.class));
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvNews.setLayoutManager(mLayoutManager);
        rvNews.setItemAnimator(new DefaultItemAnimator());
        rvNews.setAdapter(mAdapter);
    }

}
