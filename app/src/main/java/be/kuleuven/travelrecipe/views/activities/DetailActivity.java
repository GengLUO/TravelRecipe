package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import be.kuleuven.travelrecipe.ImageActivity;
import be.kuleuven.travelrecipe.adapters.DetailsAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.ExpandListView;
import be.kuleuven.travelrecipe.models.RecipeStep;

public class DetailActivity extends AppCompatActivity {

    ImageView imgBack;
    //RecyclerView recipeDetailsRV;
    //RecipeDetailsAdapter recipeDetailsAdapter;
    DetailsAdapter detailsAdapter;
    ExpandListView listView;
    List<RecipeStep> recipeList = new ArrayList<>();
    private RequestQueue requestQueue;
    private static final String GET_IMAGE_URL_this = "https://studev.groept.be/api/a21pt210/getStep/";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgBack = findViewById(R.id.img_back);

        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

        int recipeId =getIntent().getExtras().getInt("id");
        System.out.println(recipeId);
        requestQueue = Volley.newRequestQueue(this);
        {
            //Standard Volley request. We don't need any parameters for this one
            JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, GET_IMAGE_URL_this+recipeId, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try
                            {
                                //Check if the DB actually contains an image
                                if( response.length() > 0 ) {
                                    for(int i=0; i<response.length();i++){
                                        JSONObject o = response.getJSONObject(i);

                                        //converting base64 string to image
                                        String desc = o.getString("description");
                                        String b64String = o.getString("image");
                                        byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
                                        Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

                                        //Link the bitmap to the ImageView, so it's visible on screen
                                        //imageRetrieved.setImageBitmap( bitmap2 );
                                        recipeList.add(new RecipeStep(String.valueOf(i+1),desc,bitmap));
                                        System.out.println(desc);
                                        System.out.println(bitmap);
                                        //Just a double-check to tell us the request has completed
                                    }
                                    setListView(recipeList);
                                    progressDialog.dismiss();
                                    Toast.makeText(DetailActivity.this, "Image retrieved from DB", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(DetailActivity.this, "Unable to communicate with server", Toast.LENGTH_LONG).show();
                        }
                    }
            );

            requestQueue.add(retrieveImageRequest);
        }

        //setRecipeRecyclerView(recipeList);
        //setListView(recipeList);
        System.out.println("222222");
    }

    private void setListView(List<RecipeStep> recipeList) {
        listView = findViewById(R.id.simple_adapter);
        detailsAdapter = new DetailsAdapter(recipeList,getApplicationContext());
        listView.setAdapter(detailsAdapter);
    }

//    private void setRecipeRecyclerView(List<RecipeStep> recipeList){
//        //recipeDetailsRV = findViewById(R.id.details_recycler);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        recipeDetailsRV.setLayoutManager(layoutManager);
//        recipeDetailsAdapter = new RecipeDetailsAdapter(recipeList,this);
//        recipeDetailsRV.setAdapter(recipeDetailsAdapter);
//    }

    public void onImgBack_Clicked(View caller){
        //onBackPressed();
        startActivity(new Intent(this, ImageActivity.class));
    }

}