package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import be.kuleuven.travelrecipe.R;

public class BottomActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        bottomNavigationView = findViewById(R.id.btm_nav);
        navController = Navigation.findNavController(this, R.id.nav_controller);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                bottomNavigationView.getMenu()).build();
//
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
}