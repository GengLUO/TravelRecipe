package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.travelrecipe.R;


public class SettingMain extends AppCompatActivity {
    private TextView usernameText;
    private TextView passwordText;
    private TextView levelText;
    private TextView levelNumber;
    private TextView recipeAmountText;
    private TextView recipeAmountNumber;
    private EditText usernameinput;
    private EditText passwordinput;
    private Button setButton;
    private ImageView profileImage;

    private static final String URL = "https://studev.groept.be/api/a21pt210";

    private String username;
    private int userID;
    private int level;
    private int recipeAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_main);
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        levelText = findViewById(R.id.setting_levelText);
        levelNumber = findViewById(R.id.setting_levelNumber);
        recipeAmountText = findViewById(R.id.setting_recipeAmountText);
        recipeAmountNumber = findViewById(R.id.setting_recipeAmountNumber);
        usernameinput = findViewById(R.id.usernameInput);
        passwordinput = findViewById(R.id.passwordInput);
        setButton = findViewById(R.id.setButton);
        profileImage = findViewById(R.id.setting_prifileImage);

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
                Toast.makeText(SettingMain.this,"Unable to communicate with the server",Toast.LENGTH_LONG).show();;
            }
        });
        recipeAmountNumber.setText(recipeAmount);
        recipeAmountText.setText("recipeAmount");
        usernameText.setText("username");
        usernameinput.setText(username);
        levelText.setText("level");
        levelNumber.setText(level);
        passwordText.setText("password");
    }
    public void onSettingButton_Clicked(View caller){
        String UsernameURL = URL+"/setUsername/"+usernameinput.getText()+"/"+userID;
        String PasswordURL = URL+"/setPassword/"+passwordinput.getText()+"/"+userID;
        StringRequest settingRequest = new StringRequest(Request.Method.GET, UsernameURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}