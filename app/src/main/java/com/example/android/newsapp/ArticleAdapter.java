package com.example.android.newsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by shalom on 2017-04-21.
 */

public class ArticleAdapter extends ArrayAdapter<Articles> {
    public final static String LOG_TAG = ArticleAdapter.class.getName();
    private Context context;

    public ArticleAdapter(Context context, ArrayList<Articles> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Articles article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_list_item, parent, false);
        }

        ImageView thumbnailImageView = (ImageView) convertView.findViewById(R.id.article_thumbnail);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.article_title);
        TextView timeStampTextView = (TextView) convertView.findViewById(R.id.article_time_stamp);
//        Log.e(LOG_TAG, "Before LoadImageFromWebOperations()");
        thumbnailImageView.setImageDrawable(article.getThumbnailDrawable());
//        Log.e(LOG_TAG, "After LoadImageFromWebOperations()");
        titleTextView.setText(article.getTitle());
        timeStampTextView.setText(formatTimeStamp(article.getTimeStamp()));

        return convertView;
    }


    private String formatTimeStamp(String jsonTimeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            sdf.parse(jsonTimeStamp); //prints-> Mon Sep 30 02:46:19 CST 2013
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Problem parsing timeStamp, Exception " + e);
        }
        return sdf.toString();
    }
}
