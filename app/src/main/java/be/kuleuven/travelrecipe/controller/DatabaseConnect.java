package be.kuleuven.travelrecipe.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
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

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.Countries;
import be.kuleuven.travelrecipe.models.Country;
import be.kuleuven.travelrecipe.models.Recipe;
import be.kuleuven.travelrecipe.models.RecipeStep;
import be.kuleuven.travelrecipe.models.User;
import be.kuleuven.travelrecipe.views.activities.SettingMain;

public class DatabaseConnect {
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;
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
    public Countries retrieveCountries(Countries countries)
    {
        int userID = countries.getUserid();
        String countriesURL = "https://studev.groept.be/api/a21pt210/retrieveCountriesInfo/"+userID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, countriesURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Country> temCountries= new ArrayList<Country>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                        int countryImg;
                        String countryName;
                        int recipeNumber;

                        int actived;
                        boolean ac;
                        int continent;
                        countryImg = o .getInt("idcountry");
                        countryName = o.getString("country_name");
                        recipeNumber = o.getInt("number");
                        if (recipeNumber==0) {actived = 0;}
                        else { actived = 1; }
                        continent = o.getInt("continent");
                        if (actived == 0){ac = false;}
                        else {ac = true;}
                        Country country = new Country(countryImg,countryName,recipeNumber,ac,continent);
                        temCountries.add(country);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ;
                    }
                }
                countries.setCountries(temCountries);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
        return countries;
    }
    public void postProfileImage(View caller,Bitmap bitmap,User user)
    {
        String POST_URL = "https://studev.groept.be/api/a21pt210/insertProfileImage";
        //Start an animating progress widget
        progressDialog = new ProgressDialog(caller.getContext());
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

        //convert image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        StringRequest  submitRequest = new StringRequest (Request.Method.POST, POST_URL,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Turn the progress widget off
                progressDialog.dismiss();
                Toast.makeText(caller.getContext(), "Post request executed", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(caller.getContext(), "Post request failed", Toast.LENGTH_LONG).show();
            }
        }) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("image", imageString);
                params.put("userid",String.valueOf(user.getUserID()));
                return params;
            }
        };

        requestQueue.add(submitRequest);
    }

}
