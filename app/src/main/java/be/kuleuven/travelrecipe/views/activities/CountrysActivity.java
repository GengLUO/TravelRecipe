package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.adapters.CountryAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.Country;
import be.kuleuven.travelrecipe.models.User;

public class CountrysActivity extends AppCompatActivity {
    RecyclerView countriesRecyclerView;
    CountryAdapter countryAdapter;
    int continentNumber;
    List<Country> countries;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        Intent intent = getIntent();
        continentNumber = intent.getIntExtra("continentnumber",1);
        //continent setting
        TextView tvContinent = findViewById(R.id.textViewContinent);
        setCountriesRecyclerView(user.getCountriesByContinentID(continentNumber));
    }
    private void setCountriesRecyclerView(List<Country> countries){
        countriesRecyclerView = findViewById(R.id.countriesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        countriesRecyclerView.setLayoutManager(layoutManager);
        countryAdapter = new CountryAdapter(countries,continentNumber,this);
        countriesRecyclerView.setAdapter(countryAdapter);
    }

    public void onImgBack_Clicked(View caller){
        onBackPressed();
    }
}