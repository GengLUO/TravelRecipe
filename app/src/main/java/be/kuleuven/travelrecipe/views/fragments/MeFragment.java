package be.kuleuven.travelrecipe.views.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.DashboardAdapter;
import be.kuleuven.travelrecipe.models.dashboard.Dashboard;
import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;
import be.kuleuven.travelrecipe.notifier.RecipeNotifier;
import be.kuleuven.travelrecipe.views.activities.MainActivity;

public class MeFragment extends Fragment implements RecipeNotifier {

    private ImageView imgSetting;
    private RecyclerView listRecyclerView;
    private DashboardAdapter listDashboardAdapter;
    private Dashboard dashboard;

    public MeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        initModel();
        return view;
    }

    private void initView(View view) {
        listRecyclerView = view.findViewById(R.id.rvList);
        imgSetting = view.findViewById(R.id.img_Setting);
        bindAdapter();
    }

    private void initModel() {
        dashboard.setRecipeNotifier(this);
        dashboard = new Dashboard();
        MainActivity.databaseConnect.requestListRecipe(dashboard);
    }

    private void bindAdapter() {
        listDashboardAdapter = new DashboardAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listRecyclerView.setLayoutManager(layoutManager);
        listRecyclerView.setNestedScrollingEnabled(false);
        listRecyclerView.setAdapter(listDashboardAdapter);
    }

    @Override
    public void notifyRecipesListChanged(List<RecipeInfo> recipes) {
        listDashboardAdapter.setList(recipes);
    }
}