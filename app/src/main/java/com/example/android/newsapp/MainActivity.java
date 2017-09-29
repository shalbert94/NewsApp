package com.example.android.newsapp;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Articles>> {
    private ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public Loader<ArrayList<Articles>> onCreateLoader(int id, Bundle args) {
        return new ArticlesLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Articles>> loader, ArrayList<Articles> data) {
        ListView listView = (ListView) findViewById(R.id.article_list);
        adapter = new ArticleAdapter(getApplicationContext(), data);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Articles>> loader) {
        adapter.clear();
    }
}
