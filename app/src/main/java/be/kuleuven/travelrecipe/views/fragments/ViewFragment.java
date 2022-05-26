package be.kuleuven.travelrecipe.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import be.kuleuven.travelrecipe.adapters.DashboardAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.RecipeInfo;

public class ViewFragment extends Fragment {

    private RecyclerView recyclerView;
    //private List<Recipe> recipeList;
    private ArrayList<RecipeInfo> dashboardModels;

    public ViewFragment() {
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
        initView();
        initData();
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        DashboardAdapter dashboardAdapter = new DashboardAdapter(dashboardModels,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(dashboardAdapter);

        return view;
    }

    private void initData() {
        dashboardModels = new ArrayList<RecipeInfo>();
//        dashboardModels.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
//        dashboardModels.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
//        dashboardModels.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
//        dashboardModels.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
//        dashboardModels.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
//        dashboardModels.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
//        dashboardModels.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
//        dashboardModels.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));
//        dashboardModels.add(new Dashboard(R.drawable.dumpling,R.drawable.flag_china,"水晶虾饺","很好吃"));

    }

    private void initView() {

    }
}