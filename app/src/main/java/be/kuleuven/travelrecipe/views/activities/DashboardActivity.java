package be.kuleuven.travelrecipe.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.controller.DashboardAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.Dashboard;

public class DashboardActivity extends AppCompatActivity {

    RecyclerView dashboardRecyclerView;
    DashboardAdapter dashboardAdapter;
    List<Dashboard> dashboardModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.search);

        //perforn ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        return true;

                    case R.id.me:
                        startActivity(new Intent(getApplicationContext(),MeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.homePage:
                        startActivity(new Intent(getApplicationContext(), Homepage.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            };

        });


        dashboardModelList = new ArrayList<>();
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));

        setDashboardRecyclerView(dashboardModelList);

    }

    private void setDashboardRecyclerView(List<Dashboard> dashboardModelList){
        dashboardRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        dashboardRecyclerView.setLayoutManager(layoutManager);
        dashboardAdapter = new DashboardAdapter(dashboardModelList,this);
        dashboardRecyclerView.setAdapter(dashboardAdapter);
    }

    public void onImgSearch_Clicked(View caller){
        startActivity(new Intent(this, DashboardActivity.class));
    }

    public void onImgHome_Clicked(View caller){
        startActivity(new Intent(this, Homepage.class));
    }

    public void onImgMe_Clicked(View caller){
        startActivity(new Intent(this, MainActivity.class));
    }
}