package com.sattar.newsfeed.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sattar.newsfeed.R;
import com.sattar.newsfeed.helpers.Utilities;
import com.sattar.newsfeed.models.news.ArticlesItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created By Sattar 2/17/2019
 */
public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder> {

    private List<ArticlesItem> newsList;
    private View.OnClickListener mOnclickListener;

    public NewsFeedAdapter(List<ArticlesItem> newsList) {
        this.newsList = newsList;
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

    public void addData(List<ArticlesItem> newList) {
        newsList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        ArticlesItem article = newsList.get(position);

        holder.txtDate.setText(Utilities.getDateTime(article.getPublishedAt()));
        holder.txtTitle.setText(article.getTitle());
        holder.txtSource.setText(article.getAuthor());

        Picasso.get()
                .load(article.getUrlToImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgArticle);

        holder.itemView.setOnClickListener(mOnclickListener);
    }

    @Override
    public int getItemCount() {
        return newsList != null ? newsList.size() : 0;
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