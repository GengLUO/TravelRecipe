package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.notifier.CountryActivityNotifier;
import be.kuleuven.travelrecipe.models.country.Countries;
import be.kuleuven.travelrecipe.models.country.Country;


import java.util.List;

import be.kuleuven.travelrecipe.adapters.CountryAdapter;
import be.kuleuven.travelrecipe.controller.DatabaseConnect;

public class CountrysActivity extends AppCompatActivity implements CountryActivityNotifier {

    RecyclerView countriesRecyclerView;
    CountryAdapter countryAdapter;
    int continentNumber;
    Countries countries;
    RequestQueue requestQueue;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue( this);
        setContentView(R.layout.activity_countries);
        TextView textViewContinent = findViewById(R.id.textViewContinent);
        Intent intent = getIntent();
        continentNumber = intent.getIntExtra("continentnumber",1);
        userid = intent.getIntExtra("userid",1);
        countries = new Countries(userid);
        countries.setCountryActivityNotifier(this);
        if (continentNumber == 1)
        {
            textViewContinent.setText("Asia");
        }
        else if(continentNumber == 2)
        {
            textViewContinent.setText("Europe");
        }
        else if(continentNumber ==3)
        {
            textViewContinent.setText("America");
        }
        else
        {
            textViewContinent.setText("other places");
        }
        DatabaseConnect databaseConnect = new DatabaseConnect(requestQueue);
        databaseConnect.retrieveCountryByContinent(countries,continentNumber);
    }

    public void setCountriesRecyclerView(List<Country> countries){
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