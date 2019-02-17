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
public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder> {

    private List<Movie> moviesList;
    private View.OnClickListener mOnclickListener;

    public NewsFeedAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);

        return new NewsViewHolder(itemView);
    }

   public void setOnclickListener(View.OnClickListener onclickListener) {
        mOnclickListener = onclickListener;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Movie movie = moviesList.get(position);

        holder.itemView.setOnClickListener(mOnclickListener);
    }

    @Override
    public int getItemCount() {
        return moviesList != null ? moviesList.size() : 0;
    }


    class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgArticle;
        public TextView txtTitle, txtSource, txtDate;

        NewsViewHolder(View view) {
            super(view);
            imgArticle = view.findViewById(R.id.imgArticle);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtSource = view.findViewById(R.id.txtSource);
            txtDate = view.findViewById(R.id.txtDate);
        }
    }
}