package be.kuleuven.travelrecipe.views.fragments;

import android.app.ProgressDialog;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.DashboardAdapter;
import be.kuleuven.travelrecipe.notifier.RecipeNotifier;
import be.kuleuven.travelrecipe.controller.DatabaseConnect;
import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;
import be.kuleuven.travelrecipe.models.recipe.RecipeIngredient;
import be.kuleuven.travelrecipe.models.dashboard.Dashboard;


public class SearchFragment extends Fragment implements RecipeNotifier {

    private RecyclerView dashboardRecyclerView;
    private DashboardAdapter dashboardAdapter;
    private SearchView searchView;
    private Spinner dashboardSpinner;

    private ProgressDialog progressDialog;
    private Dashboard dashboard;
    private static final int DATE_ASC = 0;
    private static final int DATE_DESC = 1;
    private static final int A_Z = 2;
    private static final int Z_A = 3;

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
/*
 progressDialog = new ProgressDialog(getContext());
 progressDialog.setMessage("Uploading, please wait...");
 progressDialog.show();
*/
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.searchView);
        dashboardRecyclerView = view.findViewById(R.id.recycler_view);
        dashboardSpinner = view.findViewById(R.id.spSearch);
        dashboardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("click:"+i);
                sortList(i);
                System.out.println("finish");
                System.out.println(i);
                dashboardAdapter.setList(filterList(searchView.getQuery().toString()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        initView();
        initModel();
        return view;
    }

    private void initView() {
        dashboardAdapter = new DashboardAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        dashboardRecyclerView.setLayoutManager(layoutManager);
        dashboardRecyclerView.setNestedScrollingEnabled(false);
        dashboardRecyclerView.setAdapter(dashboardAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(filterList(query).isEmpty())
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                //bindAdapter(filterList(newText));
                dashboardAdapter.setList(filterList(newText));
                System.out.println("size"+ dashboard.getAllRecipes().size());
                return false;
            }
        });
    }

    private void initModel() {
        dashboard = new Dashboard();
        dashboard.setRecipeNotifier(this);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        DatabaseConnect databaseConnect = new DatabaseConnect(requestQueue);
        databaseConnect.retrieveRecipes(dashboard);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<RecipeInfo> filterList(String text) {
//        filteredList = recipes.stream()
//                                .filter(r -> r.getName().contains(text))
//                                .collect(Collectors.toList());
////        recipes.stream().forEach(e -> {
////            if (e.getName().contains(text)){
////                filteredList.add(e);
////            }
////        });
//        return filteredList;
//        if(text.equals("")){
//            return recipesDashboard.getAllRecipes();
//        }
        return dashboard
                .getAllRecipes()
                .stream()
                .filter(r -> r.getName().contains(text) ||
                             r.getIngredients()
                                     .stream()
                                     .map(RecipeIngredient::getName)
                                     .collect(Collectors.toSet())
                                     .toString()
                                     .contains(text))
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortList(int state){
        System.out.println(searchView.getQuery());
        switch (state){
            case DATE_ASC : dashboard.getAllRecipes()
                                            .sort(Comparator.comparingInt(RecipeInfo::getRecipeId));
                //(r1,r2) -> (r2.getRecipeId()-r1.getRecipeId())
                break;
            case DATE_DESC : dashboard.getAllRecipes()
                                            .sort(Comparator.comparingInt(RecipeInfo::getRecipeId).reversed());
                break;
            case A_Z: dashboard.getAllRecipes()
                                        .sort((Comparator.comparing(RecipeInfo::getName)));
                break;
            case Z_A: dashboard.getAllRecipes()
                                        .sort((Comparator.comparing(RecipeInfo::getName).reversed()));
                break;
        }
    }

    @Override
    public void notifyRecipesListChanged(List<RecipeInfo> recipes) {
        dashboardAdapter.setList(recipes);
    }
}