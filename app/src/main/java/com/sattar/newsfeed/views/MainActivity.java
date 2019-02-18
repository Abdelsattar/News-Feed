package com.sattar.newsfeed.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ProgressBar;
import android.widget.TextView;
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

import static com.sattar.newsfeed.helpers.Constants.KEY_ARTICLE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private RecyclerView rvNews;
    private NewsFeedAdapter mAdapter;
    private List<ArticlesItem> mArticlesItemList;

    MainActivityViewModel mViewModel;

    TextView txtError;
    ProgressBar progressBar;
    private Disposable disposable;
    private SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initScreen();
    }

    void initScreen() {
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        rvNews = findViewById(R.id.rvNews);
        txtError = findViewById(R.id.txtError);
        progressBar = findViewById(R.id.progressBar);
        pullToRefresh = findViewById(R.id.pullToRefresh);


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
        getAllNews();
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllNews();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    void getAllNews() {
//        progressBar.setVisibility(View.VISIBLE);

        mViewModel.getAllNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .subscribe(new Observer<List<ArticlesItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("On Next", "on subscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<ArticlesItem> articlesItems) {
                        Log.e("On Next", articlesItems.size() + "");
                        progressBar.setVisibility(View.GONE);
                        mArticlesItemList = articlesItems;

                        if (!articlesItems.isEmpty()) {
                            mAdapter.updateData(articlesItems);
                            rvNews.setVisibility(View.VISIBLE);
                            txtError.setVisibility(View.GONE);

                        } else {
                            txtError.setText(getString(R.string.txt_no_articles));
                            txtError.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getLocalizedMessage());
                        progressBar.setVisibility(View.GONE);
                        txtError.setVisibility(View.VISIBLE);

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
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Toast.makeText(this, getString(R.string.action_search), Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        if (item.getTitle() != null)
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void setupNewsRecyclerView() {
        mArticlesItemList = new ArrayList<>();
        mAdapter = new NewsFeedAdapter(mArticlesItemList);
        mAdapter.setOnclickListener(new NewsFeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int pos) {

                startActivity(new Intent(MainActivity.this, NewsDetailsActivity.class)
                        .putExtra(KEY_ARTICLE, mArticlesItemList.get(pos)));
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvNews.setLayoutManager(mLayoutManager);
        rvNews.setItemAnimator(new DefaultItemAnimator());
        rvNews.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
