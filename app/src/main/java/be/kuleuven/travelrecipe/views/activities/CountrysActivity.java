package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
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

    private ImageView imgBack;
    private TextView txtContinent;
    private RecyclerView countriesRecyclerView;
    private CountryAdapter countryAdapter;
    private Countries countries;
    private int continentNumber;
    private int userid;
    private static final int ASIA = 1;
    private static final int EUROPE = 2;
    private static final int AMERICA = 3;
    private static final int OTHER = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        txtContinent = findViewById(R.id.textViewContinent);
        imgBack = findViewById(R.id.img_back);
        countriesRecyclerView = findViewById(R.id.countriesRecyclerView);

        handleIntent();
        countries = new Countries(userid);
        countries.setCountryActivityNotifier(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        DatabaseConnect databaseConnect = new DatabaseConnect(requestQueue);
        databaseConnect.retrieveCountryByContinent(countries,continentNumber);
        switch(continentNumber){
            case ASIA:txtContinent.setText(R.string.asia);
                break;
            case EUROPE:txtContinent.setText(R.string.europe);
                break;
            case AMERICA:txtContinent.setText(R.string.america);
                break;
            default:txtContinent.setText(R.string.other);
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();
        continentNumber = intent.getIntExtra("continentnumber",1);
        userid = intent.getIntExtra("userid",1);
    }

    public void setCountriesRecyclerView(List<Country> countries){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        countriesRecyclerView.setLayoutManager(layoutManager);
        countryAdapter = new CountryAdapter(countries,continentNumber,this);
        countriesRecyclerView.setAdapter(countryAdapter);
    }

    public void onImgBack_Clicked(View caller){
        onBackPressed();
    }

}