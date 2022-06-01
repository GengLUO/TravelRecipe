package be.kuleuven.travelrecipe.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.base.DatabaseConnect;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavController navController;
    public static int userID;
    public static RequestQueue requestQueue;
    public static DatabaseConnect databaseConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this);
        databaseConnect = new DatabaseConnect(requestQueue);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.btm_nav);
        Intent intent = getIntent();
        userID = intent.getIntExtra("userid",1);
        navController = Navigation.findNavController(this, R.id.nav_controller);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

    }


    public void onButtonCreateRecipeClicked(View caller)
    {
        Intent intent = new Intent(this, UploadRecipeActivity.class);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
    public void onImgSetting_Clicked(View caller){
        Intent intent = new Intent(this,SettingMain.class);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
    public void onImgContinent1_Clicked(View caller){
        Intent intent = new Intent(this,CountrysActivity.class);
        intent.putExtra("continentnumber",1);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
    public void onImgContinent2_Clicked(View caller){
        Intent intent = new Intent(this,CountrysActivity.class);
        intent.putExtra("continentnumber",2);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
    public void onImgContinent3_Clicked(View caller){
        Intent intent = new Intent(this,CountrysActivity.class);
        intent.putExtra("continentnumber",3);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
    public void onImgContinent4_Clicked(View caller){
        Intent intent = new Intent(this,CountrysActivity.class);
        intent.putExtra("continentnumber",4);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
}