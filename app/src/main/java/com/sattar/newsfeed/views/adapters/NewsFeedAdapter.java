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

    private List<ArticlesItem> articlesItemList;
    private View.OnClickListener mOnclickListener;
    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int pos);
    }

    public NewsFeedAdapter(List<ArticlesItem> articlesItemList) {
        this.articlesItemList = articlesItemList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);

        return new NewsViewHolder(itemView);
    }

    public void setOnclickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void updateData(List<ArticlesItem> newList) {
        articlesItemList = newList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        ArticlesItem article = articlesItemList.get(position);

        holder.txtDate.setText(Utilities.getDateTime(article.getPublishedAt()));
        holder.txtTitle.setText(article.getTitle());
        holder.txtSource.setText(article.getAuthor());

        try {
            Picasso.get()
                    .load(article.getUrlToImage())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.imgArticle);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Picasso.get()
                    .load(R.drawable.placeholder)
                    .into(holder.imgArticle);
        }
    }

    @Override
    public int getItemCount() {
        return articlesItemList != null ? articlesItemList.size() : 0;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }
}