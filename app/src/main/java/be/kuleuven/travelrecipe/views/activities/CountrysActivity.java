package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.adapters.CountryAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.Country;

public class CountrysActivity extends AppCompatActivity {
    RecyclerView countriesRecyclerView;
    CountryAdapter countryAdapter;
    List<Country> countries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        //continent setting
        TextView tvContinent = findViewById(R.id.textViewContinent);

        countries = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            countries.add(new Country("china",20,R.drawable.flag_china));
        }
        setCountriesRecyclerView(countries);

    }
    private void setCountriesRecyclerView(List<Country> countries){
        countriesRecyclerView = findViewById(R.id.countriesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        countriesRecyclerView.setLayoutManager(layoutManager);
        countryAdapter = new CountryAdapter(countries,this);
        countriesRecyclerView.setAdapter(countryAdapter);
    }

    public void onImgBack_Clicked(View caller){
        onBackPressed();
    }
}