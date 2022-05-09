package be.kuleuven.travelrecipe.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.DashboardAdapter;
import be.kuleuven.travelrecipe.models.Dashboard;


public class SearchFragment extends Fragment {

    RecyclerView dashboardRecyclerView;
    DashboardAdapter dashboardAdapter;
    List<Dashboard> dashboardModelList;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        dashboardModelList = new ArrayList<>();
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));

        dashboardRecyclerView = view.findViewById(R.id.recycler_view);
        DashboardAdapter dashboardAdapter = new DashboardAdapter(dashboardModelList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        dashboardRecyclerView.setLayoutManager(layoutManager);
        dashboardRecyclerView.setNestedScrollingEnabled(false);
        dashboardRecyclerView.setAdapter(dashboardAdapter);

        return view;
    }
}