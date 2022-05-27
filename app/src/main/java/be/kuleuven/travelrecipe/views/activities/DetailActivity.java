package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import be.kuleuven.travelrecipe.adapters.DetailNotifier;
import be.kuleuven.travelrecipe.adapters.DetailsAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.ExpandListView;
import be.kuleuven.travelrecipe.adapters.IngredientAdapter;
import be.kuleuven.travelrecipe.controller.DatabaseConnect;
import be.kuleuven.travelrecipe.models.RecipeInfo;
import be.kuleuven.travelrecipe.models.DetailedRecipe;
import be.kuleuven.travelrecipe.models.RecipeIngredient;
import be.kuleuven.travelrecipe.models.RecipeStep;

public class DetailActivity extends AppCompatActivity implements DetailNotifier {

    ImageView imgBack, imgRecipeDemo;
    TextView txtRecipeName, txtRecipeDesc;
    ToggleButton tbtnStar;
    DetailsAdapter detailsAdapter;
    IngredientAdapter ingredientAdapter;
    ExpandListView detailsListView, ingredientsListView;
    DetailedRecipe detailedRecipe;
    DatabaseConnect databaseConnect;
    List<RecipeStep> recipeList = new ArrayList<>();
    LinkedHashMap<String,String> ingredients = new LinkedHashMap<>();
    int userID = 1;

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
        tbtnStar = findViewById(R.id.tbtn_star);

//        progressDialog = new ProgressDialog(DetailActivity.this);
//        progressDialog.setMessage("Uploading, please wait...");
//        progressDialog.show();
        detailedRecipe = new DetailedRecipe();
        RecipeInfo recipe = (RecipeInfo) getIntent().getExtras().getParcelable("Recipe");
        System.out.println(recipe.getIngredients());
        detailedRecipe.setRecipeInfo(recipe);
        imgRecipeDemo.setImageBitmap(recipe.getDemo());
        txtRecipeName.setText(recipe.getName());
        txtRecipeDesc.setText(recipe.getDescription());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        databaseConnect = new DatabaseConnect(requestQueue);

        setDetailsListView();
        setIngredientsListView(recipe.getIngredients());

        initModel(databaseConnect);


        //setRecipeRecyclerView(recipeList);
        //setListView(recipeList);
    }

    private void initModel(DatabaseConnect databaseConnect) {
        detailedRecipe.setDetailNotifier(this);
        databaseConnect.requestRecipeDemo(detailedRecipe);
        databaseConnect.requestLikeState(userID, detailedRecipe);
        //databaseConnect.requestIngredients(detailedRecipe);
        databaseConnect.requestRecipeDetails(detailedRecipe);
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

    private void setIngredientsListView(List<RecipeIngredient> ingredients){
        ingredientsListView = findViewById(R.id.ingredients_adapter);
        ingredientAdapter = new IngredientAdapter(ingredients,getApplicationContext());
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

    public void onLike_Clicked(View caller){
        System.out.println(tbtnStar.isChecked());
        databaseConnect.uploadMealPlan(userID, detailedRecipe,tbtnStar.isChecked());
    }

    @Override
    public void notifyDetailsRetrieved(List<RecipeStep> steps) {
        detailsAdapter.setList(steps);
    }

//    @Override
//    public void notifyIngredientsRetrieved(List<RecipeIngredient> ingredients) {
//        ingredientAdapter.setList(ingredients);
//    }

    @Override
    public void notifyLikeStateChanged(boolean newState) {
        tbtnStar.setChecked(newState);
    }

    @Override
    public void notifyRecipeDemoRetrieved(Bitmap bitmap) {
        imgRecipeDemo.setImageBitmap(bitmap);
    }
}