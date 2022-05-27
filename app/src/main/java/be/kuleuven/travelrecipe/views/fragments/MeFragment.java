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
import android.widget.ImageView;
import android.widget.TextView;
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
import be.kuleuven.travelrecipe.controller.DatabaseConnect;
import be.kuleuven.travelrecipe.models.RecipeInfo;
import be.kuleuven.travelrecipe.models.RecipesDashboard;
import be.kuleuven.travelrecipe.models.User;

public class MeFragment extends Fragment {

    private ImageView imgMore;
    private ImageView imgSetting;
    private TextView usernameTextView;
    private ImageView profileImageView;
    RecyclerView listRecyclerView;
    DashboardAdapter listDashboardAdapter;
    List<RecipeInfo> listRecipeList;
    private int userid = 1;

    private RequestQueue requestQueue;
    private String GET_LIKED_URL = "https://studev.groept.be/api/a21pt210/getLikedRecipe/";
    private ProgressDialog progressDialog;
    private RecipesDashboard recipesDashboard = new RecipesDashboard();

    public MeFragment() {
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
        progressDialog.setMessage("Me Fragment is Uploading");
        progressDialog.show();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_me, container, false);
        profileImageView = view.findViewById(R.id.profileImageView);
        usernameTextView = view.findViewById(R.id.userNameTextView);
        requestQueue = Volley.newRequestQueue(getContext());
        listRecyclerView = view.findViewById(R.id.rvList);
        imgMore = view.findViewById(R.id.img_More);
        imgSetting = view.findViewById(R.id.img_Setting);
        DatabaseConnect databaseConnect = new DatabaseConnect(requestQueue);
        User user = new User(userid);
        databaseConnect.retrieveUserInfo(user);
        profileImageView.setImageBitmap(user.getImage());
        usernameTextView.setText(user.getUserName());

        requestListRecipe();

        return view;
    }

    private void requestListRecipe() {
        //Standard Volley request. We don't need any parameters for this one
        GET_LIKED_URL = GET_LIKED_URL + String.valueOf(userid);
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

    private void bindAdapter() {
        listDashboardAdapter = new DashboardAdapter(recipesDashboard.getAllRecipes(),getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listRecyclerView.setLayoutManager(layoutManager);
        listRecyclerView.setNestedScrollingEnabled(false);
        listRecyclerView.setAdapter(listDashboardAdapter);
    }

}