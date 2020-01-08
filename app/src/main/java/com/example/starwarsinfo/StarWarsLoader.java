package com.example.starwarsinfo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.starwarsinfo.NetworkUtils;

import java.io.IOException;

public class StarWarsLoader extends AsyncTaskLoader<String>{
    private static final String TAG = StarWarsLoader.class.getSimpleName();

    private String mStarWarsJSON;
    private String mURL;

    public StarWarsLoader(Context context, String url)
    {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading()
    {
        if(mURL != null)
        {
            if(mStarWarsJSON != null)
            {
                Log.d(TAG, "Delivering cached results");
            }
            else
            {
                Log.d(TAG, "reloading");
                forceLoad();
            }
        }
    }
    @Nullable
    @Override
    public String loadInBackground() {
        if (mURL != null)
        {
            String results = null;
            try {
                Log.d(TAG, "loading results from starwars with url: " + mURL);
                results = NetworkUtils.doHTTPGet(mURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }
        else {
            return null;
        }
    }

    @Override
    public void deliverResult(@Nullable String data) {
        mStarWarsJSON = data;
        super.deliverResult(data);
    }

}

