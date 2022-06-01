package be.kuleuven.travelrecipe.views.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.DashboardAdapter;
import be.kuleuven.travelrecipe.notifier.RecipeNotifier;
import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;
import be.kuleuven.travelrecipe.models.dashboard.Dashboard;
import be.kuleuven.travelrecipe.views.activities.MainActivity;


public class SearchFragment extends Fragment implements RecipeNotifier {

    private RecyclerView dashboardRecyclerView;
    private DashboardAdapter dashboardAdapter;
    private SearchView searchView;
    private Spinner dashboardSpinner;
    private Dashboard dashboard;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        initModel();
        return view;
    }

    private void initView(View view) {
        searchView = view.findViewById(R.id.searchView);
        dashboardRecyclerView = view.findViewById(R.id.recycler_view);
        dashboardSpinner = view.findViewById(R.id.spSearch);
        bindAdapter();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(dashboard.filterList(query).isEmpty())
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                return false;
            }
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                dashboardAdapter.setList(dashboard.filterList(newText));
                return false;
            }
        });
        dashboardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int sortState, long l) {
                dashboard.sortList(sortState);
                dashboardAdapter.setList(dashboard.filterList(searchView.getQuery().toString()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void bindAdapter() {
        dashboardAdapter = new DashboardAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        dashboardRecyclerView.setLayoutManager(layoutManager);
        dashboardRecyclerView.setNestedScrollingEnabled(false);
        dashboardRecyclerView.setAdapter(dashboardAdapter);
    }

    private void initModel() {
        dashboard = new Dashboard();
        dashboard.setRecipeNotifier(this);
        MainActivity.databaseConnect.retrieveRecipes(dashboard);
    }

    @Override
    public void notifyRecipesListChanged(List<RecipeInfo> recipes) {
        dashboardAdapter.setList(recipes);
    }
}