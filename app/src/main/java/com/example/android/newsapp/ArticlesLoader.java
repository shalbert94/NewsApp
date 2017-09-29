package com.example.android.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Created by shalom on 2017-04-21.
 */

public class ArticlesLoader extends AsyncTaskLoader<ArrayList<Articles>> {
    private static final String GUARDIAN_URL = "http://content.guardianapis.com/search?show-fields=thumbnail&api-key=65e1885b-21c0-4e23-bbe8-91f3e26c6202";

    public ArticlesLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Articles> loadInBackground() {
         ArrayList<Articles> articles = GuardianQuery.fetchArticles(GUARDIAN_URL);
        return articles;
    }
}
