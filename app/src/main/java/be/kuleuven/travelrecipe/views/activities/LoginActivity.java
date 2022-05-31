package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.Country;
import be.kuleuven.travelrecipe.models.RecipeStep;

public class LoginActivity extends AppCompatActivity {
    private List<String> username;
    private List<String> password;
    private List<Integer> userid;
    private boolean logedin = false;
    private boolean request = false;
    private RequestQueue requestQueue;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private static String loginURL = "https://studev.groept.be/api/a21pt210/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.userNameInputEditText);
        passwordEditText = findViewById(R.id.passwordInputEditText);
        requestQueue = Volley.newRequestQueue(this);
        username = new ArrayList<String>();
        password = new ArrayList<String>();
        userid = new ArrayList<Integer>();
        JsonArrayRequest loginRequest = new JsonArrayRequest(Request.Method.GET, loginURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject o = response.getJSONObject(i);
                                username.add(o.getString("Username"));
                                password.add(o.getString("Password"));
                                userid.add(o.getInt("idUser"));
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
                        //Toast.makeText(DetailActivity.this, "Unable to communicate with server", Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(loginRequest);

    }

    public void onClickLoginButton(View caller)
    {
        for (int i = 0; i < username.size(); i++) {
            if (username.get(i).equals(usernameEditText.getText().toString())&&password.get(i).equals(passwordEditText.getText().toString()))
            {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("userid",userid.get(i));
                startActivity(intent);
            }
        }
    }





}