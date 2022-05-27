package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.CountryActivityNotifier;
import be.kuleuven.travelrecipe.models.Countries;
import be.kuleuven.travelrecipe.models.Country;
import be.kuleuven.travelrecipe.models.User;


import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.adapters.CountryAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.controller.DatabaseConnect;
import be.kuleuven.travelrecipe.models.Country;
import be.kuleuven.travelrecipe.models.User;

public class CountrysActivity extends AppCompatActivity implements CountryActivityNotifier {
    private TextView textViewContinent;
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
        textViewContinent.findViewById(R.id.textViewContinent);
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
            textViewContinent.setText("other places in the world");
        }
        DatabaseConnect databaseConnect = new DatabaseConnect(requestQueue);
        databaseConnect.retrieveCountryByContinent(countries,continentNumber);
        //continent setting
        TextView tvContinent = findViewById(R.id.textViewContinent);
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