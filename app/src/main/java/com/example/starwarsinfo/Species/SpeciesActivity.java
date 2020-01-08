package com.example.starwarsinfo.Species;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.starwarsinfo.Species.SpeciesAdapter;
import com.example.starwarsinfo.Species.SpeciesUtils;
import com.example.starwarsinfo.R;
import com.example.starwarsinfo.StarWarsLoader;

import java.util.ArrayList;

public class SpeciesActivity extends AppCompatActivity implements SpeciesAdapter.OnSpeciesItemClickListener,
        LoaderManager.LoaderCallbacks<String>{

    private RecyclerView mSearchResultsRV;
    private SpeciesAdapter mSpeciesAdapter;
    private ProgressBar mLoadingPB;
    private TextView mLoadingErrorMessageTV;
    private static final String REPOS_ARRAY_KEY = "speciesRepos";
    private static final String GET_URL_KEY = "SpeciesGetUrl";
    private static final int STARWARS_LOADER_ID = 0;
    private static SharedPreferences.OnSharedPreferenceChangeListener settingsListener;
    private ArrayList<SpeciesUtils.StarWarsSpecies> mStarWarsSpecies;
    private final String TAG = SpeciesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_species);
        mLoadingPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);
        SharedPreferences prefs = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);

        mSearchResultsRV = findViewById(R.id.rv_starwars_list);

        mSpeciesAdapter = new SpeciesAdapter(this);

        mSearchResultsRV.setAdapter(mSpeciesAdapter);
        mSearchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRV.setHasFixedSize(true);

        if(savedInstanceState != null && savedInstanceState.containsKey(REPOS_ARRAY_KEY))
        {
            mStarWarsSpecies = (ArrayList<SpeciesUtils.StarWarsSpecies>) savedInstanceState.getSerializable((REPOS_ARRAY_KEY));
            mSpeciesAdapter.updateSpeciesResults(mStarWarsSpecies);
        }
        getSupportLoaderManager().initLoader(STARWARS_LOADER_ID, savedInstanceState, this);
        settingsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                loadStarWarsSpecies();
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(settingsListener);
        loadStarWarsSpecies();
    }

    private void loadStarWarsSpecies()
    {
        String url = SpeciesUtils.buildStarWarsSearchURL("https://swapi.co/api/");
        Log.d(TAG, "querying starwars search species URL: " + url );
       // new StarWarsSearchTask().execute(url);
        Bundle args = new Bundle();
        args.putString(GET_URL_KEY, url);
        getSupportLoaderManager().restartLoader(STARWARS_LOADER_ID, args, this);
    }

    @Override
    public void onSpeciesItemClick(SpeciesUtils.StarWarsSpecies swp) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, swp.name);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if ( mStarWarsSpecies!= null) {
            outState.putSerializable(REPOS_ARRAY_KEY, mStarWarsSpecies);
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
        Log.d(TAG, "Got results from the loader");
        if (s != null) {
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mSearchResultsRV.setVisibility(View.VISIBLE);
            ArrayList<SpeciesUtils.StarWarsSpecies> starWarsSpecies = SpeciesUtils.parseStarWarsResults(s);
            mSpeciesAdapter.updateSpeciesResults(starWarsSpecies);
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
//                ArrayList<SpeciesUtils.StarWarsSpecies> items = SpeciesUtils.parseStarWarsResults(s); // SET UP FOR PEOPLE
//                mSpeciesAdapter.updateSpeciesResults(items);
//            } else {
//                //mLoadingErrorTV.setVisibility(View.VISIBLE);
//                //mSearchResultsRV.setVisibility(View.INVISIBLE);
//            }
//            mLoadingPB.setVisibility(View.INVISIBLE);
//        }
//    }
}
