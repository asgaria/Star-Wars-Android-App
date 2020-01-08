package com.example.starwarsinfo.Planet;

import android.net.Uri;

import com.google.gson.Gson;

import java.util.ArrayList;

public class PlanetUtils {
    final static String STARWARS_SEARCH_BASE_URL = "https://swapi.co/api/";
    final static String STARWARS_SEARCH_QUERY_PARAM = "q";
    final static String STARWARS_SEARCH_TYPE_PARAM = "planets";
    final static String STARWARS_SEARCH_NUMBER_PARAM = "1";
    final static String STARWARS_SEARCH_STUFF = "search";

    public static class StarWarsPlanet {
        public String name;
        public String rotation_period;
        public String population;
        public String terrain;
        public String climate;
        public String gravity;
        public String diameter;
    }
    public static class StarWarsResults {
        public StarWarsPlanet[] results;

    }

    public static String buildStarWarsSearchURL(String query){
        return Uri.parse(STARWARS_SEARCH_BASE_URL).buildUpon()
                .appendPath(STARWARS_SEARCH_TYPE_PARAM)
                .appendQueryParameter(STARWARS_SEARCH_STUFF, "a")
                .build()
                .toString();

    }

    public static ArrayList<StarWarsPlanet> parseStarWarsResults(String json) {
        Gson gson = new Gson();
        StarWarsResults results = gson.fromJson(json, StarWarsResults.class);
        if (results != null && results.results != null) {
            ArrayList<StarWarsPlanet> newList = new ArrayList<>();
            for (StarWarsPlanet swp: results.results) {
                newList.add(swp);
            }
            return newList;
        } else {
            return null;
        }
    }
}
