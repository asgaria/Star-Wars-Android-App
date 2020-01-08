package com.example.starwarsinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.starwarsinfo.People.PeopleActivity;
import com.example.starwarsinfo.Planet.PlanetActivity;
import com.example.starwarsinfo.Settings.SettingsActivity;
import com.example.starwarsinfo.Species.SpeciesActivity;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private String mRelocationURLTextString;
    private ProgressBar mLoadingPB;
    private TextView mLoadingErrorTV;

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        //mSearchResultsRV = findViewById(R.id.rv_starwars_list);

        //mPeopleAdapter = new PeopleAdapter();

        //mSearchResultsRV.setAdapter(mPeopleAdapter);
        //mSearchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        //mSearchResultsRV.setHasFixedSize(true);

        NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_hamburger_stack);

        getSupportActionBar().setElevation(0);

        Button peopleButton = findViewById(R.id.btn_people);
        peopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent peopleIntent = new Intent(getApplicationContext(), PeopleActivity.class);
                startActivity(peopleIntent);
            }
        });

        Button planetButton = findViewById(R.id.btn_planet);
        planetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent planetIntent = new Intent(getApplicationContext(), PlanetActivity.class);
                startActivity(planetIntent);
            }
        });

        Button speciesButton = findViewById(R.id.btn_species);
        speciesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speciesIntent = new Intent(getApplicationContext(), SpeciesActivity.class);
                startActivity(speciesIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                return true;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.nav_people:
                Intent peopleActivityIntent = new Intent(this, PeopleActivity.class);
                startActivity(peopleActivityIntent);
                return true;
            case R.id.nav_planets:
                Intent planetActivityIntent = new Intent(this, PlanetActivity.class);
                startActivity(planetActivityIntent);
                return true;
            case R.id.nav_species:
                Intent speciesActivityIntent = new Intent(this, SpeciesActivity.class);
                startActivity(speciesActivityIntent);
                return true;
            default:
                return false;
        }
    }

}
