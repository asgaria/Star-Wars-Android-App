package com.example.starwarsinfo.Planet;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class PlanetActivity extends AppCompatActivity implements PlanetAdapter.OnPlanetItemClickListener,
        LoaderManager.LoaderCallbacks<String>{

    private RecyclerView mSearchResultsRV;
    private PlanetAdapter mPlanetAdapter;
    private ProgressBar mLoadingPB;
    private TextView mLoadingErrorMessageTV;
    private static final String REPOS_ARRAY_KEY = "planetRepos";
    private static final String GET_URL_KEY = "PlanetGetUrl";
    private static final int STARWARS_LOADER_ID = 0;
    private static SharedPreferences.OnSharedPreferenceChangeListener settingsListener;
    private ArrayList<PlanetUtils.StarWarsPlanet> mStarWarsPlanet;
    private final String TAG = PlanetActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);
        mLoadingPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);
        SharedPreferences prefs = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);

        mSearchResultsRV = findViewById(R.id.rv_starwars_list);

        mPlanetAdapter = new PlanetAdapter(this);

        mSearchResultsRV.setAdapter(mPlanetAdapter);
        mSearchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRV.setHasFixedSize(true);

        if(savedInstanceState != null && savedInstanceState.containsKey(REPOS_ARRAY_KEY))
        {
            mStarWarsPlanet = (ArrayList<PlanetUtils.StarWarsPlanet>) savedInstanceState.getSerializable((REPOS_ARRAY_KEY));
            mPlanetAdapter.updatePlanetResults(mStarWarsPlanet);
        }
        getSupportLoaderManager().initLoader(STARWARS_LOADER_ID, savedInstanceState, this);
        settingsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                loadStarWarsPlanet();
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(settingsListener);
        loadStarWarsPlanet();
    }

    private void loadStarWarsPlanet()
    {
        String url = PlanetUtils.buildStarWarsSearchURL("https://swapi.co/api/");
        Log.d(TAG, "querying starwars planet search URL: " + url );
       // new StarWarsSearchTask().execute(url);
        Bundle args = new Bundle();
        args.putString(GET_URL_KEY, url);
        getSupportLoaderManager().restartLoader(STARWARS_LOADER_ID, args, this);
    }

    @Override
    public void onPlanetItemClick(PlanetUtils.StarWarsPlanet swp) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, swp.name);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if ( mStarWarsPlanet!= null) {
            outState.putSerializable(REPOS_ARRAY_KEY, mStarWarsPlanet);
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
        Log.d(TAG, "Got results from the loader for planet");
        if (s != null) {
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mSearchResultsRV.setVisibility(View.VISIBLE);
            ArrayList<PlanetUtils.StarWarsPlanet> starWarsPlanet = PlanetUtils.parseStarWarsResults(s);
            mPlanetAdapter.updatePlanetResults(starWarsPlanet);
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
