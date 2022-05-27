package be.kuleuven.travelrecipe.controller;

import android.app.ProgressDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import be.kuleuven.travelrecipe.models.Countries;
import be.kuleuven.travelrecipe.models.Country;
import be.kuleuven.travelrecipe.models.RecipeInfo;
import be.kuleuven.travelrecipe.models.DetailedRecipe;
import be.kuleuven.travelrecipe.models.RecipeStep;
import be.kuleuven.travelrecipe.models.RecipesDashboard;
import be.kuleuven.travelrecipe.models.User;

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

    public void uploadRecipe(View caller, RecipeInfo recipe, int userid)
    {
        String POSTImage_URL = "https://studev.groept.be/api/a21pt210/insertRecipe";
        //Start an animating progress widget
        progressDialog = new ProgressDialog(caller.getContext());
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

        //convert image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        recipe.getDemo().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        StringRequest  submitRequest = new StringRequest (Request.Method.POST, POSTImage_URL,  new Response.Listener<String>() {
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
                params.put("demo", imageString);
                params.put("recipeid",String.valueOf(recipe.getRecipeId()));
                params.put("name",recipe.getName());
                params.put("description",recipe.getDescription());
                params.put("country",String.valueOf(recipe.getCountry()));
                params.put("recipeidd",String.valueOf(recipe.getRecipeId()));
                params.put("userid",String.valueOf(userid));
                return params;
            }
        };

        requestQueue.add(submitRequest);
    }

    public void uploadStep(View caller, int recipeid, int sequence, String description,Bitmap image)
    {
        String POSTImage_URL = "https://studev.groept.be/api/a21pt210/insetStep";
        //Start an animating progress widget
        progressDialog = new ProgressDialog(caller.getContext());
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

        //convert image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        StringRequest  submitRequest = new StringRequest (Request.Method.POST, POSTImage_URL,  new Response.Listener<String>() {
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

                params.put("recipeid",String.valueOf(recipeid));
                params.put("sequence",String.valueOf(sequence));
                params.put("description",description);
                params.put("image",imageString);
                return params;
            }
        };

        requestQueue.add(submitRequest);
    }

    public void retrieveRecipes(RecipesDashboard recipesDashboard){
        //Standard Volley request. We don't need any parameters for this one
        List<RecipeInfo> newRecipes = new ArrayList<>();
        String GET_RECIPE_URL = "https://studev.groept.be/api/a21pt210/getRecipe";
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, GET_RECIPE_URL, null,
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
                                    int id = o.getInt("recipe_id");
                                    int country = o.getInt("country");
                                    String name = o.getString("name");
                                    String desc = o.getString("recipe_desc");
                                    String b64String = o.getString("recipe_image");
                                    byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
                                    Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

                                    //Link the bitmap to the ImageView, so it's visible on screen
                                    //imageRetrieved.setImageBitmap( bitmap2 );
                                    newRecipes.add(new RecipeInfo(name,desc,country,id,bitmap));

                                    //Just a double-check to tell us the request has completed
                                }
                                //progressDialog.dismiss();
                                recipesDashboard.setRecipes(newRecipes);
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
                    }
                });
        requestQueue.add(retrieveImageRequest);
    }

    public void requestIngredients(DetailedRecipe detailedRecipeDetails) {
        //Standard Volley request. We don't need any parameters for this one
        String url = "https://studev.groept.be/api/a21pt210/getIngredients/";
        int recipeId = detailedRecipeDetails.getRecipeInfo().getRecipeId();
        LinkedHashMap<String,String> ingredients = new LinkedHashMap<>();
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, url+ recipeId, null,
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
                                    String name = o.getString("name");
                                    String amount = o.getString("amount");
                                    ingredients.put(name,amount);
                                    //Just a double-check to tell us the request has completed
                                }
                                //detailedRecipeDetails.setIngredients(ingredients);
                            }

                            //progressDialog.dismiss();

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
                    }
                }
        );
        requestQueue.add(retrieveImageRequest);
    }

    public void requestRecipeDetails(DetailedRecipe detailedRecipeDetails) {
        //Standard Volley request. We don't need any parameters for this one
        String url = "https://studev.groept.be/api/a21pt210/getStep/";
        int recipeId = detailedRecipeDetails.getRecipeInfo().getRecipeId();
        List<RecipeStep> steps = new ArrayList<>();
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, url+ recipeId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            //Toast.makeText(DetailActivity.this, "start ingredients", Toast.LENGTH_SHORT).show();
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
                                    steps.add(new RecipeStep(String.valueOf(i+1),desc,bitmap));
                                    //Just a double-check to tell us the request has completed

                                }
                                detailedRecipeDetails.setSteps(steps);
                                //progressDialog.dismiss();
                                //Toast.makeText(DetailActivity.this, "Image retrieved from DB", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(retrieveImageRequest);
    }

    public void uploadMealPlan(int userId, DetailedRecipe detailedRecipe,boolean state)
    {
        String addMealPlan_URL = "https://studev.groept.be/api/a21pt210/addMealPlan";
        String deleteMealPlan_URL = "https://studev.groept.be/api/a21pt210/deleteMealPlan";
        String url = state? addMealPlan_URL : deleteMealPlan_URL;
        int recipeId = detailedRecipe.getRecipeInfo().getRecipeId();
        //Start an animating progress widget

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        StringRequest  submitRequest = new StringRequest (Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Turn the progress widget off
                detailedRecipe.setLikeState(state);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", String.valueOf(userId));
                params.put("recipeid",String.valueOf(recipeId));
                return params;
            }
        };

        requestQueue.add(submitRequest);
    }

    public void requestLikeState(int userId, DetailedRecipe detailedRecipe) {
        String url = "https://studev.groept.be/api/a21pt210/getLikeState/";
        int recipeId = detailedRecipe.getRecipeInfo().getRecipeId();
        //Start an animating progress widget

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, url+ userId+"/"+recipeId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(DetailActivity.this, "start ingredients", Toast.LENGTH_SHORT).show();
                        //Check if the DB actually contains an image
                        if( response.length() > 0 ) {
                            detailedRecipe.setLikeState(true);
                            //progressDialog.dismiss();
                            //Toast.makeText(DetailActivity.this, "Image retrieved from DB", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(retrieveImageRequest);
    }

    public void requestRecipeDemo(DetailedRecipe detailedRecipe) {
        String url = "https://studev.groept.be/api/a21pt210/getRecipeDemo/";
        int id = detailedRecipe.getRecipeInfo().getRecipeId();
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, url+ id, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(DetailActivity.this, "start ingredients", Toast.LENGTH_SHORT).show();
                        //Check if the DB actually contains an image
                        if( response.length() > 0 ) {
                            JSONObject o = null;
                            try {
                                o = response.getJSONObject(0);
                                String b64String = o.getString("recipe_image");
                                byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
                                Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
                                detailedRecipe.setRecipeDemo(bitmap);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //progressDialog.dismiss();
                            //Toast.makeText(DetailActivity.this, "Image retrieved from DB", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(retrieveImageRequest);
    }
}
