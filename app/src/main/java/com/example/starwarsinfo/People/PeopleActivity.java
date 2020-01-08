package com.example.starwarsinfo.People;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.starwarsinfo.R;
import com.example.starwarsinfo.StarWarsLoader;

import java.util.ArrayList;

public class PeopleActivity extends AppCompatActivity implements PeopleAdapter.OnPersonItemClickListener,
        LoaderManager.LoaderCallbacks<String>{

    private RecyclerView mSearchResultsRV;
    private PeopleAdapter mPeopleAdapter;
    private ProgressBar mLoadingPB;
    private TextView mLoadingErrorMessageTV;
    private static final String REPOS_ARRAY_KEY = "peopleRepos";
    private static final String GET_URL_KEY = "PeopleGetUrl";
    private static final int STARWARS_LOADER_ID = 0;
    private static SharedPreferences.OnSharedPreferenceChangeListener settingsListener;
    private ArrayList<PeopleUtils.StarWarsPerson> mStarWarsPeople;
    private final String TAG = PeopleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        mLoadingPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);
        SharedPreferences prefs = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);

        mSearchResultsRV = findViewById(R.id.rv_starwars_list);

        mPeopleAdapter = new PeopleAdapter(this);

        mSearchResultsRV.setAdapter(mPeopleAdapter);
        mSearchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRV.setHasFixedSize(true);

        if(savedInstanceState != null && savedInstanceState.containsKey(REPOS_ARRAY_KEY))
        {
            mStarWarsPeople = (ArrayList<PeopleUtils.StarWarsPerson>) savedInstanceState.getSerializable((REPOS_ARRAY_KEY));
            mPeopleAdapter.updatePeopleResults(mStarWarsPeople);
        }
        getSupportLoaderManager().initLoader(STARWARS_LOADER_ID, savedInstanceState, this);
        settingsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                loadStarWarsPeople();
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(settingsListener);
        loadStarWarsPeople();
    }

    private void loadStarWarsPeople()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String pageNumber = settings.getString("settings_page_number", "1");
        String url = PeopleUtils.buildStarWarsSearchURL("https://swapi.co/api/", pageNumber);
        Log.d(TAG, "querying starwars people search URL: " + url );
       // new StarWarsSearchTask().execute(url);
        Bundle args = new Bundle();
        args.putString(GET_URL_KEY, url);
        getSupportLoaderManager().restartLoader(STARWARS_LOADER_ID, args, this);
    }

    @Override
    public void onPersonItemClick(PeopleUtils.StarWarsPerson swp) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, swp.name);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if ( mStarWarsPeople!= null) {
            outState.putSerializable(REPOS_ARRAY_KEY, mStarWarsPeople);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        String url = null;
        if (bundle != null) {
            url = bundle.getString(GET_URL_KEY);
        }
        return new StarWarsLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        Log.d(TAG, "Got results from the loader for people");
        if (s != null) {
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mSearchResultsRV.setVisibility(View.VISIBLE);
            ArrayList<PeopleUtils.StarWarsPerson> starWarsPeople = PeopleUtils.parseStarWarsResults(s);
            mPeopleAdapter.updatePeopleResults(starWarsPeople);
        } else {
            mSearchResultsRV.setVisibility(View.INVISIBLE);
            mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
        }
        mLoadingPB.setVisibility(View.INVISIBLE);

    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }
//    class StarWarsSearchTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mLoadingPB.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String url = urls[0];
//            String results = null;
//            try {
//                Log.d(TAG, "loading results URL");
//                results = NetworkUtils.doHTTPGet(url);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return results;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            if (s != null) {
//                //mLoadingErrorTV.setVisibility(View.INVISIBLE);
//                //mSearchResultsRV.setVisibility(View.VISIBLE);
//                Log.d(TAG, s);
//                ArrayList<PeopleUtils.StarWarsPerson> items = PeopleUtils.parseStarWarsResults(s); // SET UP FOR PEOPLE
//                mPeopleAdapter.updatePeopleResults(items);
//            } else {
//                //mLoadingErrorTV.setVisibility(View.VISIBLE);
//                //mSearchResultsRV.setVisibility(View.INVISIBLE);
//            }
//            mLoadingPB.setVisibility(View.INVISIBLE);
//        }
//    }
}
