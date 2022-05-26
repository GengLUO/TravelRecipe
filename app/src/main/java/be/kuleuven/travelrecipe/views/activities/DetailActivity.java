package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import be.kuleuven.travelrecipe.adapters.DetailNotifier;
import be.kuleuven.travelrecipe.adapters.DetailsAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.ExpandListView;
import be.kuleuven.travelrecipe.adapters.IngredientAdapter;
import be.kuleuven.travelrecipe.controller.DatabaseConnect;
import be.kuleuven.travelrecipe.models.Recipe;
import be.kuleuven.travelrecipe.models.RecipeDetails;
import be.kuleuven.travelrecipe.models.RecipeStep;

public class DetailActivity extends AppCompatActivity implements DetailNotifier {

    ImageView imgBack, imgRecipeDemo;
    TextView txtRecipeName, txtRecipeDesc;
    DetailsAdapter detailsAdapter;
    IngredientAdapter ingredientAdapter;
    ExpandListView detailsListView, ingredientsListView;
    RecipeDetails recipeDetails;
    List<RecipeStep> recipeList = new ArrayList<>();
    LinkedHashMap<String,String> ingredients = new LinkedHashMap<>();

    private static final String GET_IMAGE_URL_this = "https://studev.groept.be/api/a21pt210/getStep/";
    private static final String GET_INGREDIENT_URL = "https://studev.groept.be/api/a21pt210/getIngredients/";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgBack = findViewById(R.id.img_back);
        imgRecipeDemo = findViewById(R.id.img_recipe_demo);
        txtRecipeName = findViewById(R.id.txt_recipe_name);
        txtRecipeDesc = findViewById(R.id.txt_recipe_desc);

//        progressDialog = new ProgressDialog(DetailActivity.this);
//        progressDialog.setMessage("Uploading, please wait...");
//        progressDialog.show();

        recipeDetails = new RecipeDetails();
        Recipe recipe = (Recipe) getIntent().getExtras().getParcelable("Recipe");
        recipeDetails.setRecipe(recipe);
        imgRecipeDemo.setImageBitmap(recipe.getDemo());
        txtRecipeName.setText(recipe.getName());
        txtRecipeDesc.setText(recipe.getDescription());

        setDetailsListView();
        setIngredientsListView();

        initModel();


        //setRecipeRecyclerView(recipeList);
        //setListView(recipeList);
    }

    private void initModel() {
        recipeDetails.setDetailNotifier(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        DatabaseConnect databaseConnect = new DatabaseConnect(requestQueue);
        databaseConnect.requestIngredients(recipeDetails);
        databaseConnect.requestRecipeDetails(recipeDetails);
    }


//    private void requestRecipeDetails(int recipeId) {
//        //Standard Volley request. We don't need any parameters for this one
//        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, GET_IMAGE_URL_this+ recipeId, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try
//                        {
//                            Toast.makeText(DetailActivity.this, "start ingredients", Toast.LENGTH_SHORT).show();
//                            //Check if the DB actually contains an image
//                            if( response.length() > 0 ) {
//                                for(int i=0; i<response.length();i++){
//                                    JSONObject o = response.getJSONObject(i);
//
//                                    //converting base64 string to image
//                                    String desc = o.getString("description");
//                                    String b64String = o.getString("image");
//                                    byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
//                                    Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
//
//                                    //Link the bitmap to the ImageView, so it's visible on screen
//                                    //imageRetrieved.setImageBitmap( bitmap2 );
//                                    recipeList.add(new RecipeStep(String.valueOf(i+1),desc,bitmap));
//                                    //Just a double-check to tell us the request has completed
//
//                                }
//                                recipeDetails.setSteps(recipeList);
//                                progressDialog.dismiss();
//                                Toast.makeText(DetailActivity.this, "Image retrieved from DB", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        catch( JSONException e )
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(DetailActivity.this, "Unable to communicate with server", Toast.LENGTH_LONG).show();
//                    }
//                }
//        );
//        requestQueue.add(retrieveImageRequest);
//    }

//    private void requestIngredients(int recipeId) {
//        //Standard Volley request. We don't need any parameters for this one
//        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, GET_INGREDIENT_URL+ recipeId, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try
//                        {
//                            //Check if the DB actually contains an image
//                            if( response.length() > 0 ) {
//                                for(int i=0; i<response.length();i++){
//                                    JSONObject o = response.getJSONObject(i);
//
//                                    //converting base64 string to image
//                                    String name = o.getString("name");
//                                    String amount = o.getString("amount");
//                                    ingredients.put(name,amount);
//                                    //Just a double-check to tell us the request has completed
//                                }
//                            }
//                            setIngredientsListView(ingredients);
//                            //progressDialog.dismiss();
//                            Toast.makeText(DetailActivity.this, "Ingredients retrieved from DB", Toast.LENGTH_SHORT).show();
//
//                        }
//                        catch( JSONException e )
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(DetailActivity.this, "Unable to communicate with server", Toast.LENGTH_LONG).show();
//                    }
//                }
//        );
//        requestQueue.add(retrieveImageRequest);
//    }

    private void setDetailsListView() {
        detailsListView = findViewById(R.id.step_adapter);
        detailsAdapter = new DetailsAdapter(getApplicationContext());
        detailsListView.setAdapter(detailsAdapter);
    }

    private void setIngredientsListView(){
        ingredientsListView = findViewById(R.id.ingredients_adapter);
        ingredientAdapter = new IngredientAdapter(getApplicationContext());
        ingredientsListView.setAdapter(ingredientAdapter);
    }

//    private void setRecipeRecyclerView(List<RecipeStep> recipeList){
//        //recipeDetailsRV = findViewById(R.id.details_recycler);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        recipeDetailsRV.setLayoutManager(layoutManager);
//        recipeDetailsAdapter = new RecipeDetailsAdapter(recipeList,this);
//        recipeDetailsRV.setAdapter(recipeDetailsAdapter);
//    }

    public void onImgBack_Clicked(View caller){
        onBackPressed();
        //startActivity(new Intent(this, ImageActivity.class));
    }

    @Override
    public void notifyDetailsRetrieved(List<RecipeStep> steps) {
        detailsAdapter.setList(steps);
    }

    @Override
    public void notifyIngredientsRetrieved(LinkedHashMap<String,String> ingredients) {
        ingredientAdapter.setList(ingredients);
    }
}