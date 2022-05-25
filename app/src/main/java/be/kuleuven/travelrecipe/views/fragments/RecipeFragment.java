package be.kuleuven.travelrecipe.views.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

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

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.DashboardAdapter;
import be.kuleuven.travelrecipe.models.Recipe;
import be.kuleuven.travelrecipe.models.RecipesModel;

public class RecipeFragment extends Fragment {

    RecyclerView dashboardRecyclerView;
    DashboardAdapter dashboardAdapter;
    List<Recipe> dashboardModelList;

    private RequestQueue requestQueue;
    private static final String GET_LIKED_URL = "https://studev.groept.be/api/a21pt210/getLikedRecipe/1";
    private ProgressDialog progressDialog;
    private RecipesModel recipesModel = new RecipesModel();

    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        dashboardRecyclerView = view.findViewById(R.id.like_recycler_view);

        requestLikedRecipes();

        System.out.println("end");

        return view;
    }

    private void bindAdapter() {
        dashboardAdapter = new DashboardAdapter(recipesModel.getAllRecipes(),getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        dashboardRecyclerView.setLayoutManager(layoutManager);
        //dashboardRecyclerView.setNestedScrollingEnabled(false);
        dashboardRecyclerView.setAdapter(dashboardAdapter);
    }

    private void requestLikedRecipes() {
        //Standard Volley request. We don't need any parameters for this one
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, GET_LIKED_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            //Check if the DB actually contains an image
                            Toast.makeText(getContext(), "beginlike", Toast.LENGTH_SHORT).show();
                            if( response.length() > 0 ) {
                                for(int i=0; i<response.length();i++){
                                    JSONObject o = response.getJSONObject(i);

                                    //converting base64 string to image
                                    int id = o.getInt("recipe_id");
                                    System.out.println(id);
                                    String name = o.getString("name");
                                    String desc = o.getString("recipe_desc");
                                    String b64String = o.getString("recipe_image");
                                    byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
                                    Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

                                    //Link the bitmap to the ImageView, so it's visible on screen
                                    //imageRetrieved.setImageBitmap( bitmap2 );
                                    recipesModel.addRecipe(new Recipe(name,desc,id,bitmap));

                                    //Just a double-check to tell us the request has completed



                                }
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "IIImage retrieved from DB", Toast.LENGTH_SHORT).show();
                                bindAdapter();
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

        requestQueue.add(retrieveImageRequest);

    }
}