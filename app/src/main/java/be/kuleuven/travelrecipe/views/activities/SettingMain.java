package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Map;

import be.kuleuven.travelrecipe.R;


public class SettingMain extends AppCompatActivity {
    private EditText usernameinput;
    private EditText passwordinput;
    private Button setButton;
    private ImageView profileImage;

    private static final String URL = "https://studev.groept.be/api/a21pt210";
    private static final String ImagePostURL = "https://studev.groept.be/api/a21pt210/insertProfileImage";
    private Bitmap bitmap;
    private RequestQueue requestQueue;
    private int PICK_IMAGE_REQUEST = 111;
    private ProgressDialog progressDialog;
    private String username;
    private int userID;
    private int level;
    private int recipeAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_main);
        TextView usernameText = findViewById(R.id.usernameText);
        TextView passwordText = findViewById(R.id.passwordText);
        TextView levelText = findViewById(R.id.setting_levelText);
        TextView levelNumber = findViewById(R.id.setting_levelNumber);
        TextView recipeAmountText = findViewById(R.id.setting_recipeAmountText);
        TextView recipeAmountNumber = findViewById(R.id.setting_recipeAmountNumber);
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
    public void onProfileImage_Clicked(View caller){
        String UsernameURL = URL+"/setUsername/"+usernameinput.getText()+"/"+userID;
        String PasswordURL = URL+"/setPassword/"+passwordinput.getText()+"/"+userID;
        StringRequest settingRequest = new StringRequest(Request.Method.GET, UsernameURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(SettingMain.this, "Post request executed", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SettingMain.this, "Post request failed", Toast.LENGTH_LONG).show();
            }
        });
        //Start an animating progress widget
        progressDialog = new ProgressDialog(SettingMain.this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

        //convert image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        StringRequest  submitRequest = new StringRequest (Request.Method.POST, ImagePostURL,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Turn the progress widget off
                progressDialog.dismiss();
                Toast.makeText(SettingMain.this, "Post request executed", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SettingMain.this, "Post request failed", Toast.LENGTH_LONG).show();
            }
        }) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String userIDString = ""+userID;
                params.put("image", imageString);
                params.put("userid",userIDString);
                return params;
            }
        };

        requestQueue.add(submitRequest);
    }

    /**
     * Starts a new (automatic) activity to select an image from your phone
     */
    public void onBtnPickClicked(View caller)
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
                profileImage.setImageBitmap(bitmap);
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