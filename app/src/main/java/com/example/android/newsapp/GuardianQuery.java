package com.example.android.newsapp;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by shalom on 2017-04-20.
 */

public class GuardianQuery {
    public static final String LOG_TAG = GuardianQuery.class.getName();
    private static URL url;
    private static String jsonResponse;
    private static ArrayList<Articles> fetchedArticles;

    private GuardianQuery() {}

    public static ArrayList<Articles> fetchArticles(String urlAsString) {
        url = createUrl(urlAsString);
        jsonResponse = makeHttpRequest(url);
        fetchedArticles = parseJsonResponse(jsonResponse);
        return fetchedArticles;
    }

    private static URL createUrl(String urlAsString) {
        URL url = null;
        try {
            url = new URL(urlAsString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Exception caught while creating URL is: " + e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection;
        InputStream inputStream;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "URL error code " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException caught");
        }
        return jsonResponse;
    }

    private static String readInputStream(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            try {
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem reading InputStream, Exception " + e);
            }
        }

        return sb.toString();
    }

    private static ArrayList<Articles> parseJsonResponse(String jsonResponse) {
        ArrayList<Articles> articles = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        try {
            JSONObject wholeResponse = new JSONObject(jsonResponse);
            JSONObject response = wholeResponse.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject article = results.getJSONObject(i);
                String articleTitle = article.getString("webTitle");
                String articleTimeStamp = article.getString("webPublicationDate");

                JSONObject fields = article.getJSONObject("fields");
                String articleThumbnail = fields.getString("thumbnail");

                articles.add(new Articles(articleTitle, articleTimeStamp, articleThumbnail, LoadImageFromWebOperations(articleThumbnail)));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON response, Exception " + e);
        }

        return articles;
    }

    private static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Problem loading thumbnail, Exception " + e);
            return null;
        }
    }
}
