package be.kuleuven.travelrecipe.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.travelrecipe.R;

public class Homepage extends AppCompatActivity {
    private ViewPager viewPager;
    private ImageView profileImage;
    private TextView recipeAmountText;
    private TextView recipeAmountTextview;
    private TextView levelText;
    private TextView levelTextview;
    private TextView usernameTextview;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private ImageView settingButton;

    private static final String URL = "https://studev.groept.be/api/a21pt210";

    private String username;
    private int userID;
    private int level;
    private int recipeAmount;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_homepage);

        viewPager = findViewById(R.id.homePage);
        profileImage = findViewById(R.id.profileImage);
        recipeAmountText = findViewById(R.id.recipeAmountText);
        recipeAmountTextview = findViewById(R.id.recipeAmountTextview);
        levelTextview = findViewById(R.id.levelTextview);
        levelText = findViewById(R.id.levelText);
        usernameTextview = findViewById(R.id.usernameTextview);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar3 = findViewById(R.id.progressBar3);
        progressBar4 = findViewById(R.id.progressBar4);
        settingButton = findViewById(R.id.settingButton);




        //get username, recipeAmount, level
        userID = 1;
        String infoURL = URL+'/'+"getUserinfo"+'/'+userID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, infoURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
                for (int i = 0; i < response.length(); i++) {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(0);
                        username = o.getString("Username");
                        level = o.getInt("Level");
                        userID = o.getInt("idUser");
                        recipeAmount = o.getInt("RecipeAmount");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Homepage.this,"Unable to communicate with the server",Toast.LENGTH_LONG).show();;
            }
        });
        recipeAmountTextview.setText(recipeAmount);
        recipeAmountText.setText("recipeAmount");
        usernameTextview.setText(username);
        levelText.setText("level");
        levelTextview.setText(level);

    }

    public void onImgSetting_Clicked(View caller){
        startActivity(new Intent(this, DashboardActivity.class));
    }
}
