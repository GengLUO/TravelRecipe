package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.notifier.HomepageFragmentNotifier;
import be.kuleuven.travelrecipe.base.DatabaseConnect;
import be.kuleuven.travelrecipe.models.user.User;


public class SettingMain extends AppCompatActivity implements HomepageFragmentNotifier {
    private EditText usernameinput;
    private EditText passwordinput;
    private Button setButton;
    private TextView levelNumber;
    private TextView recipeAmountNumber;
    private ImageView profileImage;
    private Bitmap bitmap;
    private RequestQueue requestQueue;
    private int PICK_IMAGE_REQUEST = 111;
    private User user;
    private int userid;
    DatabaseConnect databaseConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_main);
        TextView usernameText = findViewById(R.id.usernameText);
        TextView passwordText = findViewById(R.id.passwordText);
        TextView levelText = findViewById(R.id.setting_levelText);
        levelNumber = findViewById(R.id.setting_levelNumber);
        TextView recipeAmountText = findViewById(R.id.setting_recipeAmountText);
        recipeAmountNumber = findViewById(R.id.setting_recipeAmountNumber);
        usernameinput = findViewById(R.id.usernameInput);
        passwordinput = findViewById(R.id.passwordInput);
        setButton = findViewById(R.id.setButton);
        profileImage = findViewById(R.id.setting_prifileImage);
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        userid = intent.getIntExtra("userid",1);
        user = new User(userid);
        user.setHomepageFragmentNotifier(this);
        databaseConnect = new DatabaseConnect(requestQueue);
        databaseConnect.retrieveUserInfo(user);

    }

    public void onBtnSetClicked(View caller)
    {
        LoginActivity loginActivity = new LoginActivity();
        databaseConnect.setUserInfo(caller,userid,usernameinput.getText().toString(),loginActivity.encrypt(passwordinput.getText().toString()));
    }
    public void onBtnPostClicked(View caller){
        databaseConnect.postProfileImage(caller,bitmap,user);
    }
    public void onProfileImage_Clicked(View caller)
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



    @Override
    public void notifyNameChanged() {
        usernameinput.setHint(user.getUserName());
        usernameinput.setText("");
    }

    @Override
    public void notifyLevelChanged() {
        levelNumber.setText(String.valueOf(user.getLevel()));
    }

    @Override
    public void notifyRecipeNumberChanged() {
        recipeAmountNumber.setText(String.valueOf(user.getRecipeAmount()));
    }

    @Override
    public void notifyImageChanged() {
        profileImage.setImageBitmap(user.getImage());
    }



    @Override
    public void notifyAsiaChanged() {
    }
    @Override
    public void notifyEuropeChanged() {
    }
    @Override
    public void notifyAmericaChanged() {
    }
    @Override
    public void notifyAfricaChanged() {
    }
}