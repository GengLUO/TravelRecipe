package be.kuleuven.travelrecipe.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.kuleuven.travelrecipe.models.Country;
import be.kuleuven.travelrecipe.models.Recipe;
import be.kuleuven.travelrecipe.models.RecipeStep;
import be.kuleuven.travelrecipe.models.User;

public class DatabaseConnect {
    private RequestQueue requestQueue;
    private int PICK_IMAGE_REQUEST = 111;
    private User user;

    public DatabaseConnect(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public User retrieveUserInfo(User inuser)
    {
        String URL = "https://studev.groept.be/api/a21pt210";
        //get username, recipeAmount, level
        this.user = inuser;
        int userID = this.user.getUserID();
        user.setUserID(userID);
        String infoURL = URL+'/'+"getUserinfo"+'/'+user.getUserID();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, infoURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(0);
                        user.setUserName(o.getString("Username"));
                        user.setLevel(o.getInt("Level"));
                        user.setRecipeAmount(o.getInt("RecipeAmount"));
                        user.setCountryAfricaAmount(o.getInt("CountryAfricaAmount"));
                        user.setCountryAmericaAmount(o.getInt("CountryAmericaAmount"));
                        user.setCountryAsiaAmount(o.getInt("CountryAsiaAmount"));
                        user.setCountryEuropeAmount(o.getInt("CountryEuropeAmount"));
                        //image
                        String b64String = o.getString("image");
                        byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
                        Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
                        user.setImage(bitmap);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
        return user;
    }
    public User retrieveCountries(User inuser)
    {
        this.user = inuser;
        int userID = user.getUserID();
        String countriesURL = "https://studev.groept.be/api/a21pt210/getCountriesByID/"+userID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, countriesURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Country> countries= new ArrayList<Country>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(0);
                        int countryImg;
                        String countryName;
                        int recipeNumber;
                        int actived;
                        boolean ac;
                        int continent;
                        countryImg = o .getInt("idcountries");
                        countryName = o.getString("name");
                        recipeNumber = o.getInt("recipeNumber");
                        actived = o.getInt("actived");
                        continent = o.getInt("continent");
                        if (actived == 0){ac = false;}
                        else {ac = true;}
                        Country country = new Country(countryImg,countryName,recipeNumber,ac,continent);
                        countries.add(country);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ;
                    }
                }
                user.setCountries(countries);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
        return user;
    }


}
