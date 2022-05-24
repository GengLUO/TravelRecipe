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
    RecyclerView countriesRecyclerView;
    CountryAdapter countryAdapter;
    int continentNumber;
    List<Country> countries;
    private User user;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue( this);
        setContentView(R.layout.activity_countries);
        Intent intent = getIntent();
        continentNumber = intent.getIntExtra("continentnumber",1);
        user = new User(1);

        DatabaseConnect databaseConnect = new DatabaseConnect(requestQueue);

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