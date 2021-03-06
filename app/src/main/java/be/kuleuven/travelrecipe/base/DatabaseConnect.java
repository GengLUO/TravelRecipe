package be.kuleuven.travelrecipe.base;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.kuleuven.travelrecipe.models.country.Countries;
import be.kuleuven.travelrecipe.models.country.Country;
import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;
import be.kuleuven.travelrecipe.models.recipe.DetailedRecipe;
import be.kuleuven.travelrecipe.models.recipe.RecipeIngredient;
import be.kuleuven.travelrecipe.models.recipe.RecipeStep;
import be.kuleuven.travelrecipe.models.dashboard.Dashboard;
import be.kuleuven.travelrecipe.models.user.User;
import be.kuleuven.travelrecipe.views.activities.LoginActivity;

public class DatabaseConnect implements UsableDatabaseConnect{
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    private int PICK_IMAGE_REQUEST = 111;
    private User user;

    public DatabaseConnect(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void retrieveUserInfo(User inuser) {
        String URL = "https://studev.groept.be/api/a21pt210";
        this.user = inuser;
        int userID = this.user.getUserID();
        user.setUserID(userID);
        String infoURL = URL+'/'+"getUserinfo"+'/'+user.getUserID();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, infoURL, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                JSONObject o;
                try {
                    o = response.getJSONObject(0);
                    user.setUserName(o.getString("Username"));
                    user.setLevel(o.getInt("Level"));
                    user.setRecipeAmount(o.getInt("RecipeAmount"));
                    //image
                    String b64String = o.getString("image");
                    byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
                    Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
                    user.setImage(bitmap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
        });
        requestQueue.add(request);
    }

    public void retrieveAllCountries(Countries countries)
    {
        String countriesURL = "https://studev.groept.be/api/a21pt210/getAllCountries";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, countriesURL, null, response -> {
            List<Country> temCountries= new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                JSONObject o;
                try {
                    o = response.getJSONObject(i);
                    int countryImg = o.getInt("idcountry");
                    String countryName = o.getString("country_name");
                    int recipeNumber = 0;
                    int continent = o.getInt("continent");
                    Country country = new Country(countryImg,countryName,recipeNumber,false,continent);
                    temCountries.add(country);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            countries.setCountries(temCountries);
        }, error -> {
        });
        requestQueue.add(request);
    }
    public void retrieveCountries(Countries countries) {
        int userID = countries.getUserid();
        String countriesURL = "https://studev.groept.be/api/a21pt210/retrieveCountriesInfo/"+userID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, countriesURL, null, response -> {
            List<Country> temCountries= new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                JSONObject o;
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
                    ac = actived != 0;
                    Country country = new Country(countryImg,countryName,recipeNumber,ac,continent);
                    temCountries.add(country);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            countries.setCountries(temCountries);
        }, error -> {
        });
        requestQueue.add(request);
    }

    public void retrieveCountryByContinent(Countries countries, int continent) {
        int userID = countries.getUserid();
        String countriesURL = "https://studev.groept.be/api/a21pt210/retrieveCountriesInfo/"+userID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, countriesURL, null, response -> {
            List<Country> temCountries= new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                JSONObject o;
                try {
                    o = response.getJSONObject(i);
                    int countryImg;
                    String countryName;
                    int recipeNumber;
                    int continented;
                    int actived;
                    boolean ac;
                    countryImg = o .getInt("idcountry");
                    countryName = o.getString("country_name");
                    recipeNumber = o.getInt("number");
                    if (recipeNumber==0) {actived = 0;}
                    else { actived = 1; }
                    continented = o.getInt("continent");
                    ac = actived != 0;
                    Country country = new Country(countryImg,countryName,recipeNumber,ac,continented);
                    if (continent ==continented ){temCountries.add(country);}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            countries.setCountries(temCountries,1);
        }, error -> {
        });
        JsonArrayRequest fullrequest = new JsonArrayRequest(Request.Method.GET, "https://studev.groept.be/api/a21pt210/getAllCountries", null, response -> {
            List<Country> temCountries= new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                JSONObject o;

                temCountries = countries.getCountries();
                try {
                    o = response.getJSONObject(i);
                    int countryImg;
                    boolean mul = false;
                    String countryName;
                    int recipeNumber;
                    int continented;
                    int actived;
                    boolean ac;
                    countryImg = o .getInt("idcountry");
                    countryName = o.getString("country_name");
                    continented = o.getInt("continent");
                    Country country = new Country(countryImg,countryName,0,false,continented);
                    if (continent ==continented){
                        for (int j = 0; j < countries.getCountries().size(); j++) {
                            if (countries.getCountries().get(j).getCountryName().equals(country.getCountryName()))
                            {
                                mul = true;
                            }
                        }
                        if (!mul)
                        {
                            temCountries.add(country);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            countries.setCountries(temCountries);
        }, error -> {
        });
        requestQueue.add(request);
        requestQueue.add(fullrequest);
    }

    public void retrieveContinentInfo( User user)
    {
        int userID = user.getUserID();
        String POST_URL = "https://studev.groept.be/api/a21pt210/retrieveCountriesInfo/"+userID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, POST_URL, null, response -> {
            List<Country> temCountries= new ArrayList<>();
            int continent1 = 0;
            int continent2 = 0;
            int continent3 = 0;
            int continent4 = 0;
            for (int i = 0; i < response.length(); i++) {
                JSONObject o;
                try {
                    o = response.getJSONObject(i);
                    int continented = o.getInt("continent");
                    int recipeNumber = o.getInt("number");
                    if (continented ==1)
                    {
                        continent1 = continent1+recipeNumber;
                    }
                    else if (continented == 2)
                    {
                        continent2 = continent2 +recipeNumber;
                    }
                    else if (continented == 3)
                    {
                        continent3 = continent3 +recipeNumber;
                    }
                    else if (continented == 4)
                    {
                        continent4 = continent4 +recipeNumber;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            user.setCountryAsiaAmount(continent1);
            user.setCountryEuropeAmount(continent2);
            user.setCountryAmericaAmount(continent3);
            user.setCountryAfricaAmount(continent4);
        }, error -> {
        });
        requestQueue.add(request);
    }
    public void setUserInfo(View caller,int userid, String username,String password)
    {
        String URL = "https://studev.groept.be/api/a21pt210/setUsername";
        StringRequest  registerRequest = new StringRequest (Request.Method.POST, URL, response -> {
            //Turn the progress widget off
            Toast.makeText(caller.getContext(), "succeed", Toast.LENGTH_SHORT).show();
        }, error -> Toast.makeText(caller.getContext(), "failed", Toast.LENGTH_LONG).show()) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password",password);
                params.put("userid",String.valueOf(userid));
                return params;
            }
        };
        requestQueue.add(registerRequest);
    }
    public void login(LoginActivity loginActivity, String username, String password)
    {
        String loginURL = "https://studev.groept.be/api/a21pt210/login/"+username + "/" +password;
        JsonArrayRequest loginRequest = new JsonArrayRequest(Request.Method.GET, loginURL, null,
                response -> {
                    try
                    {
                        if (response.length() ==1)
                        {
                            JSONObject o = response.getJSONObject(0);
                            int userid = o.getInt("idUser");
                            loginActivity.login(userid);
                        }
                        else
                        {
                            Toast.makeText(loginActivity,"username or password wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch( JSONException e )
                    {
                        e.printStackTrace();
                    }
                },
                error -> {
                    //Toast.makeText(DetailActivity.this, "Unable to communicate with server", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(loginRequest);
    }
    public void register(View caller, String username, String password)
    {
        String loginURL = "https://studev.groept.be/api/a21pt210/register";
        StringRequest  registerRequest = new StringRequest (Request.Method.POST, loginURL, response -> {
            //Turn the progress widget off
            Toast.makeText(caller.getContext(), "register succeed", Toast.LENGTH_SHORT).show();
        }, error -> Toast.makeText(caller.getContext(), "register failed", Toast.LENGTH_LONG).show()) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password",password);
                return params;
            }
        };
        requestQueue.add(registerRequest);
    }

    public void postProfileImage(View caller,Bitmap bitmap,User user) {
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
        StringRequest  submitRequest = new StringRequest (Request.Method.POST, POST_URL, response -> {
            //Turn the progress widget off
            progressDialog.dismiss();
            Toast.makeText(caller.getContext(), "Post request executed", Toast.LENGTH_SHORT).show();
        }, error -> Toast.makeText(caller.getContext(), "Post request failed", Toast.LENGTH_LONG).show()) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("image", imageString);
                params.put("userid",String.valueOf(user.getUserID()));
                return params;
            }
        };

        requestQueue.add(submitRequest);
    }

    public void uploadRecipe(View caller, RecipeInfo recipe, int userid) {
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
        StringRequest  submitRequest = new StringRequest (Request.Method.POST, POSTImage_URL, response -> {
            //Turn the progress widget off
            progressDialog.dismiss();
            Toast.makeText(caller.getContext(), "Post request executed", Toast.LENGTH_SHORT).show();
        }, error -> Toast.makeText(caller.getContext(), "Post request failed", Toast.LENGTH_LONG).show()) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("demo", imageString);
                params.put("recipeid",String.valueOf(recipe.getRecipeId()));
                params.put("name",recipe.getName());
                params.put("description",recipe.getDescription());
                params.put("country",String.valueOf(recipe.getCountry()));
                return params;
            }
        };

        requestQueue.add(submitRequest);
    }

    public void addIngredient(View caller,int recipeid,String name,String amount)
    {
        String ingredientURL = "https://studev.groept.be/api/a21pt210/addIngredient";
        StringRequest  ingredientrequest = new StringRequest (Request.Method.POST, ingredientURL, response -> {
            //Turn the progress widget off
            Toast.makeText(caller.getContext(), "Post request executed", Toast.LENGTH_SHORT).show();
        }, error -> Toast.makeText(caller.getContext(), "Post request failed", Toast.LENGTH_LONG).show()) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("recipeid", String.valueOf(recipeid));
                params.put("name",name);
                params.put("amount",amount);
                return params;
            }
        };
        requestQueue.add(ingredientrequest);
    }

    public void uploadStep(View caller, int recipeid, int sequence, String description,Bitmap image) {
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
        StringRequest  submitRequest = new StringRequest (Request.Method.POST, POSTImage_URL, response -> {
            //Turn the progress widget off
            progressDialog.dismiss();
            Toast.makeText(caller.getContext(), "Post request executed", Toast.LENGTH_SHORT).show();
        }, error -> Toast.makeText(caller.getContext(), "Post request failed", Toast.LENGTH_LONG).show()) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("recipeid",String.valueOf(recipeid));
                params.put("sequence",String.valueOf(sequence));
                params.put("description",description);
                params.put("image",imageString);
                return params;
            }
        };

        requestQueue.add(submitRequest);
    }

    public void retrieveRecipes(Dashboard dashboard){
        //Standard Volley request. We don't need any parameters for this one
        List<RecipeInfo> newRecipes = new ArrayList<>();
        String GET_RECIPE_URL = "https://studev.groept.be/api/a21pt210/getRecipe";
        String GET_INGREDIENTS_URL = "https://studev.groept.be/api/a21pt210/getIngredients";
        JsonArrayRequest retrieveRecipeIngredientsRequest = new JsonArrayRequest(Request.Method.GET, GET_INGREDIENTS_URL, null,
                response -> {
                    try
                    {
                        //Check if the DB actually contains an image
                        if( response.length() > 0 ) {
                            for(int i=0; i<response.length();i++){
                                JSONObject o = response.getJSONObject(i);
                                //converting base64 string to image
                                int id = o.getInt("recipe_id");
                                String name = o.getString("name");
                                String amount = o.getString("amount");
                                //Link the bitmap to the ImageView, so it's visible on screen
                                //imageRetrieved.setImageBitmap( bitmap2 );
                                newRecipes.get(id-1).addIngredients(new RecipeIngredient(name,amount));

                                //Just a double-check to tell us the request has completed
                            }
                            //progressDialog.dismiss();
                        }
                    }
                    catch( JSONException e )
                    {
                        e.printStackTrace();
                    }
                },
                error -> {
                });
        JsonArrayRequest retrieveRecipeInfoRequest = new JsonArrayRequest(Request.Method.GET, GET_RECIPE_URL, null,
                response -> {
                    try
                    {
                        //Check if the DB actually contains an image
                        if( response.length() > 0 ) {
                            for(int i=0; i<response.length();i++){
                                JSONObject o = response.getJSONObject(i);

                                //converting base64 string to image
                                int id = o.getInt("recipe_id");
                                String countryname = o.getString("countryname");
                                int country = o.getInt("country");
                                String name = o.getString("name");

                                String desc = o.getString("recipe_desc");
                                String b64String = o.getString("recipe_image");
                                byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
                                Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

                                //Link the bitmap to the ImageView, so it's visible on screen
                                //imageRetrieved.setImageBitmap( bitmap2 );
                                newRecipes.add(new RecipeInfo(name,desc,country,countryname,id,bitmap));

                                //Just a double-check to tell us the request has completed
                            }
                            //progressDialog.dismiss();
                            dashboard.setDashboard(newRecipes);
                            requestQueue.add(retrieveRecipeIngredientsRequest);
                        }
                    }
                    catch( JSONException e )
                    {
                        e.printStackTrace();
                    }
                },
                error -> {
                });

        requestQueue.add(retrieveRecipeInfoRequest);
    }

    public void requestRecipeDetails(DetailedRecipe detailedRecipeDetails) {
        //Standard Volley request. We don't need any parameters for this one
        String url = "https://studev.groept.be/api/a21pt210/getStep/";
        int recipeId = detailedRecipeDetails.getRecipeInfo().getRecipeId();
        List<RecipeStep> steps = new ArrayList<>();
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, url+ recipeId, null,
                response -> {
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
                },
                error -> {
                    //Toast.makeText(DetailActivity.this, "Unable to communicate with server", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(retrieveImageRequest);
    }

    public void uploadMealPlan(int userId, DetailedRecipe detailedRecipe,boolean state) {
        String addMealPlan_URL = "https://studev.groept.be/api/a21pt210/addMealPlan";
        String deleteMealPlan_URL = "https://studev.groept.be/api/a21pt210/deleteMealPlan";
        String url = state? addMealPlan_URL : deleteMealPlan_URL;
        int recipeId = detailedRecipe.getRecipeInfo().getRecipeId();
        //Start an animating progress widget

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        StringRequest  submitRequest = new StringRequest (Request.Method.POST, url, response -> {
            //Turn the progress widget off
            detailedRecipe.setLikeState(state);
        }, error -> {
        }) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
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
                response -> {
                    //Toast.makeText(DetailActivity.this, "start ingredients", Toast.LENGTH_SHORT).show();
                    //Check if the DB actually contains an image
                    if( response.length() > 0 ) {
                        detailedRecipe.setLikeState(true);
                        //progressDialog.dismiss();
                        //Toast.makeText(DetailActivity.this, "Image retrieved from DB", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    //Toast.makeText(DetailActivity.this, "Unable to communicate with server", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(retrieveImageRequest);
    }

    public void requestRecipeDemo(DetailedRecipe detailedRecipe) {
        String url = "https://studev.groept.be/api/a21pt210/getRecipeDemo/";
        int id = detailedRecipe.getRecipeInfo().getRecipeId();
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, url+ id, null,
                response -> {
                    //Toast.makeText(DetailActivity.this, "start ingredients", Toast.LENGTH_SHORT).show();
                    //Check if the DB actually contains an image
                    if( response.length() > 0 ) {
                        JSONObject o;
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
                },
                error -> {
                    //Toast.makeText(DetailActivity.this, "Unable to communicate with server", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(retrieveImageRequest);
    }

    public void uploadWork(int recipeId, int userId, Bitmap bitmap){
        String url = "https://studev.groept.be/api/a21pt210/insertWork";
        bitmap = getResizedBitmap( bitmap, 400 );
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        StringRequest submitRequest = new StringRequest (Request.Method.POST, url, response -> {
            //Turn the progress widget off

        }, error -> {

        }) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userid", String.valueOf(userId));
                params.put("recipeid",String.valueOf(recipeId));
                params.put("image",imageString);
                params.put("i",String.valueOf(userId));
                return params;
            }
        };
        requestQueue.add(submitRequest);
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scale = ((float) newWidth) / width;

        // We create a matrix to transform the image
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create the new bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void requestListRecipe(Dashboard dashboard, int userId) {
            //Standard Volley request. We don't need any parameters for this one
        String GET_LIKED_URL = "https://studev.groept.be/api/a21pt210/getLikedRecipe/";
        GET_LIKED_URL = GET_LIKED_URL + userId;
        List<RecipeInfo> newRecipes = new ArrayList<>();
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, GET_LIKED_URL, null,
                response -> {
                    try
                    {
                        //Check if the DB actually contains an image
                        if( response.length() > 0 ) {
                            for(int i=0; i<response.length();i++){
                                JSONObject o = response.getJSONObject(i);

                                //converting base64 string to image
                                int id = o.getInt("recipe_id");
                                int country = o.getInt("country");
                                String countryName = o.getString("country_name");
                                String name = o.getString("name");
                                String desc = o.getString("recipe_desc");
                                String b64String = o.getString("recipe_image");
                                byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
                                Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

                                //Link the bitmap to the ImageView, so it's visible on screen
                                //imageRetrieved.setImageBitmap( bitmap2 );
                                newRecipes.add(new RecipeInfo(name,desc,country,countryName,id,bitmap));

                                //Just a double-check to tell us the request has completed
                            }
                            dashboard.setDashboard(newRecipes);
                        }
                    }
                    catch( JSONException e )
                    {
                        e.printStackTrace();
                    }
                },
                error -> {
                }
        );
        requestQueue.add(retrieveImageRequest);
    }
}
