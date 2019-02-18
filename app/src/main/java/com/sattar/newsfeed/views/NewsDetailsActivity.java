package com.sattar.newsfeed.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sattar.newsfeed.R;
import com.sattar.newsfeed.ViewModels.NewsDetailsViewModel;
import com.sattar.newsfeed.helpers.Utilities;
import com.sattar.newsfeed.models.ArticlesItemRealm;
import com.squareup.picasso.Picasso;

import static com.sattar.newsfeed.helpers.Constants.KEY_ARTICLE_TITLE;

public class NewsDetailsActivity extends AppCompatActivity {

    ArticlesItemRealm article;
    ImageView imgArticle;
    public TextView txtTitle;
    TextView txtSource;
    TextView txtDate;
    TextView txtDescription;
    Button btnOpenWebsite;
    String articleTitle;

    NewsDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        initScreen();
    }

    void initScreen() {
        imgArticle = findViewById(R.id.imgArticle);
        txtTitle = findViewById(R.id.txtTitle);
        txtSource = findViewById(R.id.txtSource);
        txtDate = findViewById(R.id.txtDate);
        txtDescription = findViewById(R.id.txtDescription);
        btnOpenWebsite = findViewById(R.id.btnOpenWebsite);
        viewModel = ViewModelProviders.of(this).get(NewsDetailsViewModel.class);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        articleTitle = getIntent().getStringExtra(KEY_ARTICLE_TITLE);
        if (articleTitle != null)
            article = viewModel.getArticle(articleTitle);

        if (article != null) {
            bindDataToScreen(article);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void bindDataToScreen(ArticlesItemRealm article) {
        txtDate.setText(Utilities.getDateTime(article.getPublishedAt()));
        txtTitle.setText(article.getTitle());
        txtSource.setText(article.getAuthor());
        txtDescription.setText(article.getDescription());

        try {
            Picasso.get()
                    .load(article.getUrlToImage())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imgArticle);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Picasso.get()
                    .load(R.drawable.placeholder)
                    .into(imgArticle);
        }

        final String websiteUrl = article.getUrl();
        btnOpenWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebsite(websiteUrl);
            }
        });
    }


    void openWebsite(String websiteUrl) {
        if (websiteUrl != null) {
            try {
                Uri webpage = Uri.parse(websiteUrl);
                Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(myIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No application can handle this request." +
                        " Please install a web browser or check your URL.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
