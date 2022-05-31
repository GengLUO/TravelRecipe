package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import be.kuleuven.travelrecipe.notifier.DetailNotifier;
import be.kuleuven.travelrecipe.adapters.DetailsAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.ExpandListView;
import be.kuleuven.travelrecipe.adapters.IngredientAdapter;
import be.kuleuven.travelrecipe.controller.DatabaseConnect;
import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;
import be.kuleuven.travelrecipe.models.recipe.DetailedRecipe;
import be.kuleuven.travelrecipe.models.recipe.RecipeIngredient;
import be.kuleuven.travelrecipe.models.recipe.RecipeStep;

public class DetailActivity extends AppCompatActivity implements DetailNotifier {

    ImageView imgBack, imgRecipeDemo, imgTimer;
    TextView txtRecipeName, txtRecipeDesc, txtTime;
    ToggleButton tbtnStar;
    DetailsAdapter detailsAdapter;
    IngredientAdapter ingredientAdapter;
    ExpandListView detailsListView, ingredientsListView;
    DetailedRecipe detailedRecipe;
    DatabaseConnect databaseConnect;
    Vibrator vibrator;
    List<RecipeStep> recipeList = new ArrayList<>();
    LinkedHashMap<String,String> ingredients = new LinkedHashMap<>();
    int userID = 1;
    private int PICK_IMAGE_REQUEST = 111;
    private CountDownTimer countDownTimer;
    int hour;
    int minute;

    private static final String GET_IMAGE_URL_this = "https://studev.groept.be/api/a21pt210/getStep/";
    private static final String GET_INGREDIENT_URL = "https://studev.groept.be/api/a21pt210/getIngredients/";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgBack = findViewById(R.id.img_back);
        imgTimer = findViewById(R.id.img_timer);
        imgRecipeDemo = findViewById(R.id.img_recipe_demo);
        txtRecipeName = findViewById(R.id.txt_recipe_name);
        txtRecipeDesc = findViewById(R.id.txt_recipe_desc);
        txtTime = findViewById(R.id.txt_time);
        tbtnStar = findViewById(R.id.tbtn_star);

        vibrator = (Vibrator)this.getSystemService(VIBRATOR_SERVICE);

//        progressDialog = new ProgressDialog(DetailActivity.this);
//        progressDialog.setMessage("Uploading, please wait...");
//        progressDialog.show();
        detailedRecipe = new DetailedRecipe();
        RecipeInfo recipe = getIntent().getExtras().getParcelable("Recipe");
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


    public void onImgBack_Clicked(View caller){
        onBackPressed();
//        String[] isoCountries = Locale.getISOCountries();
//        HashMap<String,String> map = new HashMap<>();
//        for (String country : isoCountries) {
//            Locale locale = new Locale("en", country);
//            String iso = locale.getISO3Country();
//            String code = locale.getCountry();
//            String name = locale.getDisplayCountry();
//            map.put(code,name);
//            System.out.println(iso + " " + code + " " + name);
//        }
//        System.out.println(map);
    }

    public void onLike_Clicked(View caller){
        System.out.println(tbtnStar.isChecked());
        databaseConnect.uploadMealPlan(userID, detailedRecipe,tbtnStar.isChecked());
    }

    public void onUploadWork_Clicked(View caller){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);

        //this line will start the new activity and will automatically run the callback method below when the user has picked an image
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    public void onTimer_Clicked(View caller){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                hour = hr;
                minute = min;
                long timeDuration = TimeUnit.HOURS.toMillis(hr) + TimeUnit.MINUTES.toMillis(min);
                long interval = 1000;
                if(countDownTimer!=null)
                    countDownTimer.cancel();
                countDownTimer = new CountDownTimer(timeDuration,interval) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long hour = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                        long minute = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)-TimeUnit.HOURS.toMinutes(hour);
                        long second = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-TimeUnit.HOURS.toSeconds(hour)-TimeUnit.MINUTES.toSeconds(minute);
                        String timeText = String.format(Locale.getDefault(),"%02d:%02d:%02d", hour,minute,second);
                        txtTime.setText(timeText);
                    }

                    @Override
                    public void onFinish() {
//                        long[] vibrationPattern = new long[]{0, 180, 80, 120};//delay, duration, delay, duration
//                        vibrator.vibrate(vibrationPattern, -1);
                        vibrator.vibrate(2000);
                        System.out.println("finish");
                    }
                }.start();
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Rescale the bitmap to 400px wide (avoid storing large images!)
                databaseConnect.uploadWork(detailedRecipe.getRecipeInfo().getRecipeId(),userID,bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void playVibrate(Context context) {
        long[] vibrationPattern = new long[]{0, 500, 80, 120};//delay, duration, delay, duration
        vibrator.vibrate(vibrationPattern, -1);
    }

    @Override
    public void notifyDetailsRetrieved(List<RecipeStep> steps) {
        detailsAdapter.setList(steps);
    }

    @Override
    public void notifyLikeStateChanged(boolean newState) {
        tbtnStar.setChecked(newState);
    }

    @Override
    public void notifyRecipeDemoRetrieved(Bitmap bitmap) {
        imgRecipeDemo.setImageBitmap(bitmap);
    }
}