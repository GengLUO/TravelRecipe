package be.kuleuven.travelrecipe.views.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.DashboardAdapter;
import be.kuleuven.travelrecipe.adapters.RecipeNotifier;
import be.kuleuven.travelrecipe.controller.DatabaseConnect;
import be.kuleuven.travelrecipe.controller.MySingleton;
import be.kuleuven.travelrecipe.models.RecipeInfo;
import be.kuleuven.travelrecipe.models.RecipesDashboard;


public class SearchFragment extends Fragment implements RecipeNotifier {

    private RecyclerView dashboardRecyclerView;
    private DashboardAdapter dashboardAdapter;
    private SearchView searchView;

    private static final String GET_IMAGE_URL = "https://studev.groept.be/api/a21pt210/getRecipe";
    private ProgressDialog progressDialog;
    private RecipesDashboard recipesDashboard;

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
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Uploading, please wait...");
//        progressDialog.show();
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.searchView);
        dashboardRecyclerView = view.findViewById(R.id.recycler_view);
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
                return false;
            }
        });
    }

    private void initModel() {
        recipesDashboard = new RecipesDashboard();
        recipesDashboard.setRecipeNotifier(this);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        DatabaseConnect databaseConnect = new DatabaseConnect(requestQueue);
        databaseConnect.retrieveRecipes(recipesDashboard);
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
        return recipesDashboard.getAllRecipes()
                .stream()
                .filter(r -> r.getName().contains(text))
                .collect(Collectors.toList());
    }


    private void requestRecipes() {
        //Standard Volley request. We don't need any parameters for this one
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, GET_IMAGE_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            //Check if the DB actually contains an image
                            Toast.makeText(getContext(), "begin", Toast.LENGTH_SHORT).show();
                            if( response.length() > 0 ) {
                                for(int i=0; i<response.length();i++){
                                    JSONObject o = response.getJSONObject(i);

                                    //converting base64 string to image
                                    int id = o.getInt("recipe_id");
                                    int country = o.getInt("country");
                                    String name = o.getString("name");
                                    String desc = o.getString("recipe_desc");
                                    String b64String = o.getString("recipe_image");
                                    byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
                                    Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

                                    //Link the bitmap to the ImageView, so it's visible on screen
                                    //imageRetrieved.setImageBitmap( bitmap2 );
                                    recipesDashboard.addRecipe(new RecipeInfo(name,desc,country,id,bitmap));

                                    //Just a double-check to tell us the request has completed
                                }
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "IIImage retrieved from DB", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch( JSONException e )
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Unable to communicate with server", Toast.LENGTH_LONG).show();
                    }
                }
        );
        MySingleton.getInstance(getContext()).addToRequestQueue(retrieveImageRequest);
    }

    @Override
    public void notifyRecipesListChanged(List<RecipeInfo> recipes) {
        dashboardAdapter.setList(recipes);
    }
}