package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
import be.kuleuven.travelrecipe.controller.DatabaseConnect;
import be.kuleuven.travelrecipe.models.Country;
import be.kuleuven.travelrecipe.models.RecipeInfo;

public class UploadRecipeActivity extends AppCompatActivity {

    private ImageView recipeMainImageView;
    private EditText recipeNameEditText;
    private EditText countryEditText;
    private EditText descriptionEditText;
    private Button uploadMainRecipeButton;

    private Bitmap bitmap = null;
    private RequestQueue requestQueue;
    private DatabaseConnect databaseConnect;
    private int PICK_IMAGE_REQUEST = 111;
    private ProgressDialog progressDialog;
    private RecipeInfo recipe;
    private int userID;
    private int recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_recipe);
        Intent intent = getIntent();
        userID=intent.getIntExtra("userid",1);
        recipeNameEditText = findViewById(R.id.recipeNameEditText);
        countryEditText = findViewById(R.id.countryEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        uploadMainRecipeButton = findViewById(R.id.uploadMainRecipeButton);
        recipeMainImageView = findViewById(R.id.recipeMainImageView);
        requestQueue = Volley.newRequestQueue(this);
        databaseConnect = new DatabaseConnect(requestQueue);
        this.getBiggestRecipeID();
        recipeMainImageView.setImageResource(R.drawable.ic_baseline_star_24);
    }



    public void onPostRecipeButton_Clicked(View caller)
    {
        String countryName = recipeNameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String country = countryEditText.getText().toString();
        int countryid = Integer.valueOf(country);
        //TODO 暂时删掉了
        //recipe = new RecipeInfo(recipeID,countryName,description,countryid,bitmap);
        databaseConnect.uploadRecipe(caller,recipe,userID);

        Intent intent = new Intent(this,UploadStepsActivity.class);
        intent.putExtra("recipeid",recipeID);
        startActivity(intent);
    }

    public void getBiggestRecipeID()
    {
        String biggestURL = "https://studev.groept.be/api/a21pt210/getBiggestRecipeID";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, biggestURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Country> temCountries= new ArrayList<Country>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                        recipeID = o.getInt("max")+1;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
    }
    public void onRecipeMainImage_Clicked(View caller)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        //this line will start the new activity and will automatically run the callback method below when the user has picked an image
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }
    /**
     * Processes the image picked by the user. For now, the bitmap is simply stored in an attribute.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Rescale the bitmap to 400px wide (avoid storing large images!)
                bitmap = getResizedBitmap( bitmap, 400 );

                //Setting image to ImageView
                recipeMainImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Helper method to create a rescaled bitmap. You enter a desired width, and the height is scaled uniformly
     */
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth) {
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
}