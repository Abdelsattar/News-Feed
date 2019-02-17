package com.sattar.newsfeed.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sattar.newsfeed.R;
import com.sattar.newsfeed.models.Movie;

import java.util.List;

/**
 * Created By Sattar 2/17/2019
 */
public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder> {

    private List<Movie> moviesList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgArticle;
        public TextView txtTitle, txtSource, txtDate;

        MyViewHolder(View view) {
            super(view);
            imgArticle = view.findViewById(R.id.imgArticle);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtSource = view.findViewById(R.id.txtSource);
            txtDate = view.findViewById(R.id.txtDate);
        }
    }


    public NewsFeedAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
    }

    @Override
    public int getItemCount() {
//        Log.e("moviesList size", moviesList.size() + "");
        return moviesList != null ? moviesList.size() : 0;
    }
}