package be.kuleuven.travelrecipe.controller;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.kuleuven.travelrecipe.models.Recipe;
import be.kuleuven.travelrecipe.models.RecipeStep;

public class DatabaseConnect {
    private static final String POST_URL = "https://studev.groept.be/api/a21pt210/insertStep";
    private static final String GET_IMAGE_URL = "https://studev.groept.be/api/a21pt210/getRecipe";
    private int PICK_IMAGE_REQUEST = 111;

    public static void uploadPic(Bitmap bitmap){
        //convert image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        StringRequest submitRequest = new StringRequest (Request.Method.POST, POST_URL,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Turn the progress widget off
                /////progressDialog.dismiss();
                /////Toast.makeText(MainActivity.this, "Post request executed", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //////Toast.makeText(MainActivity.this, "Post request failed", Toast.LENGTH_LONG).show();
            }
        }) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("image", imageString);
                return params;
            }
        };
    }

    public static Bitmap retrieveImage(){
//Standard Volley request. We don't need any parameters for this one
        Bitmap bitmap = null;
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, GET_IMAGE_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            //Check if the DB actually contains an image
                            if( response.length() > 0 ) {
                                JSONObject o = response.getJSONObject(0);

                                //converting base64 string to image
                                String b64String = o.getString("image");
                                byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
                                Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

                                //Link the bitmap to the ImageView, so it's visible on screen
                                ///////imageRetrieved.setImageBitmap( bitmap2 );

                                //Just a double-check to tell us the request has completed
                                ///////Toast.makeText(MainActivity.this, "Image retrieved from DB", Toast.LENGTH_SHORT).show();
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
                        ///////Toast.makeText(MainActivity.this, "Unable to communicate with server", Toast.LENGTH_LONG).show();
                    }
                }
        );
        return bitmap;
    }

    public List<RecipeStep> loadRecipeSteps(int recipeId){
        List<RecipeStep> steps = new ArrayList<>();
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, GET_IMAGE_URL+"/"+recipeId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            //Check if the DB actually contains an image
                            if( response.length() > 0 ) {
                                for(int i=0; i<response.length();i++){
                                    JSONObject o = response.getJSONObject(0);
                                    RecipeStep step = new RecipeStep(String.valueOf(i),o.getString("description"),o.getString("image"));
                                    steps.add(step);
                                }


                                //converting base64 string to image
//                                String b64String = o.getString("image");
//                                byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
//                                Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

                                //Link the bitmap to the ImageView, so it's visible on screen
                                ///////imageRetrieved.setImageBitmap( bitmap2 );

                                //Just a double-check to tell us the request has completed
                                ///////Toast.makeText(MainActivity.this, "Image retrieved from DB", Toast.LENGTH_SHORT).show();
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
                        ///////Toast.makeText(MainActivity.this, "Unable to communicate with server", Toast.LENGTH_LONG).show();
                    }
                }
        );
        return steps;
    }
}
