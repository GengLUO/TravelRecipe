package be.kuleuven.travelrecipe.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.Adapter.DashboardAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.DashboardModel;

public class Settings extends AppCompatActivity {

    RecyclerView dashboardRecyclerView;
    DashboardAdapter dashboardAdapter;
    List<DashboardModel> dashboardModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dashboardModelList = new ArrayList<>();
        dashboardModelList.add(new DashboardModel(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new DashboardModel(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new DashboardModel(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new DashboardModel(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new DashboardModel(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new DashboardModel(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new DashboardModel(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new DashboardModel(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));

        setDashboardRecyclerView(dashboardModelList);
    }

    private void setDashboardRecyclerView(List<DashboardModel> dashboardModelList){
        dashboardRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        dashboardRecyclerView.setLayoutManager(layoutManager);
        dashboardAdapter = new DashboardAdapter(dashboardModelList,this);
        dashboardRecyclerView.setAdapter(dashboardAdapter);
    }
}