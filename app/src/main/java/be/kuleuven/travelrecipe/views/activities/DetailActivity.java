package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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

    private ImageView imgBack, imgRecipeDemo, imgTimer;
    private TextView txtRecipeName, txtRecipeDesc, txtTime;
    private ToggleButton tbtnStar;
    private DetailsAdapter detailsAdapter;
    private IngredientAdapter ingredientAdapter;
    private ExpandListView detailsListView, ingredientsListView;
    private DetailedRecipe detailedRecipe;
    private DatabaseConnect databaseConnect;
    private Vibrator vibrator;
    private int userID;
    private int PICK_IMAGE_REQUEST = 111;
    private CountDownTimer countDownTimer;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        initView();
        handleIntent();
        setListView();
        initModel();
    }

    private void initView() {
        imgBack = findViewById(R.id.img_back);
        imgTimer = findViewById(R.id.img_timer);
        imgRecipeDemo = findViewById(R.id.img_recipe_demo);
        txtRecipeName = findViewById(R.id.txt_recipe_name);
        txtRecipeDesc = findViewById(R.id.txt_recipe_desc);
        txtTime = findViewById(R.id.txt_time);
        txtTime.setVisibility(View.INVISIBLE);
        tbtnStar = findViewById(R.id.tbtn_star);
        vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        detailsListView = findViewById(R.id.step_adapter);
        ingredientsListView = findViewById(R.id.ingredients_adapter);
    }

    private void handleIntent() {
        detailedRecipe = new DetailedRecipe();
        RecipeInfo recipe = getIntent().getExtras().getParcelable("Recipe");
        userID = getIntent().getExtras().getInt("id");
        System.out.println(userID);
        detailedRecipe.setRecipeInfo(recipe);
        imgRecipeDemo.setImageBitmap(recipe.getDemo());
        txtRecipeName.setText(recipe.getName());
        txtRecipeDesc.setText(recipe.getDescription());
    }

    private void setListView() {
        setDetailsListView();
        setIngredientsListView(detailedRecipe.getRecipeInfo().getIngredients());
    }

    private void initModel() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        databaseConnect = new DatabaseConnect(requestQueue);
        detailedRecipe.setDetailNotifier(this);
        databaseConnect.requestRecipeDemo(detailedRecipe);
        databaseConnect.requestLikeState(userID, detailedRecipe);
        databaseConnect.requestRecipeDetails(detailedRecipe);
    }


    private void setDetailsListView() {
        detailsAdapter = new DetailsAdapter(this);
        detailsListView.setAdapter(detailsAdapter);
    }

    private void setIngredientsListView(List<RecipeIngredient> ingredients){
        ingredientAdapter = new IngredientAdapter(ingredients,this);
        ingredientsListView.setAdapter(ingredientAdapter);
    }


    public void onImgBack_Clicked(View caller){
        onBackPressed();
    }

    public void onLike_Clicked(View caller){
        databaseConnect.uploadMealPlan(userID, detailedRecipe,tbtnStar.isChecked());
    }

    public void onUploadWork_Clicked(View caller){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    public void onTimer_Clicked(View caller){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                hour = hr;
                minute = min;
                long timeDuration = TimeUnit.HOURS.toMillis(hour) + TimeUnit.MINUTES.toMillis(minute);
                long interval = 1000;
                setCountDownTimer(timeDuration, interval);
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();
    }

    private void setCountDownTimer(long timeDuration, long interval) {
        if(countDownTimer!=null)
            countDownTimer.cancel();
        countDownTimer = new CountDownTimer(timeDuration, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hour = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long minute = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)-TimeUnit.HOURS.toMinutes(hour);
                long second = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-TimeUnit.HOURS.toSeconds(hour)-TimeUnit.MINUTES.toSeconds(minute);
                String countDownTime = String.format(Locale.getDefault(),"%02d:%02d:%02d", hour,minute,second);
                txtTime.setText(countDownTime);
            }
            @Override
            public void onFinish() {
                long[] vibrationPattern = new long[]{0, 1500, 500, 1500};//delay, duration, delay, duration
                vibrator.vibrate(vibrationPattern, -1);
                txtTime.setVisibility(View.INVISIBLE);
            }
        }.start();
        txtTime.setVisibility(View.VISIBLE);
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
                Toast.makeText(this,"upload Success",Toast.LENGTH_SHORT).show();
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