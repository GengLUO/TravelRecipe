package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.controller.DatabaseConnect;
import be.kuleuven.travelrecipe.models.Recipe;

public class UploadStepsActivity extends AppCompatActivity {
    private TextView stepNumberTextView ;
    private EditText descriptionStepEditText;
    private ImageView stepImageView;
    private Button oneMoreStepButton;
    private Button finishButton;

    DatabaseConnect databaseConnect;
    RequestQueue requestQueue;
    Bitmap bitmap = null;
    private int PICK_IMAGE_REQUEST = 111;
    int step;
    int recipeid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_steps);
        Intent intent = getIntent();
        recipeid = intent.getIntExtra("recipeid",1);
        stepNumberTextView = findViewById(R.id.stepNumberTextView);
        descriptionStepEditText = findViewById(R.id.descriptionStepEditText);
        stepImageView = findViewById( R.id.stepImageView);
        oneMoreStepButton = findViewById( R.id.oneMoreStepButton);
        finishButton = findViewById(R.id.finishButton);

        requestQueue = Volley.newRequestQueue(this);
        databaseConnect = new DatabaseConnect(requestQueue);
        step = 1;
        stepImageView.setImageResource(R.drawable.ic_baseline_star_24);
    }

    public void onAnotherStep_Clicked(View caller)
    {
        String description = descriptionStepEditText.getText().toString();
        databaseConnect.uploadStep(caller,recipeid,step,description,bitmap);
        this.step = step +1;
        stepNumberTextView.setText("Step"+String.valueOf(step));
        stepImageView.setImageResource(R.drawable.ic_baseline_star_24);
        descriptionStepEditText.setText("");
    }
    public void onFinishButton_Clicked(View caller)
    {
        String description = descriptionStepEditText.getText().toString();
        databaseConnect.uploadStep(caller,recipeid,step,description,bitmap);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void onStepMainImage_Clicked(View caller)
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
                stepImageView.setImageBitmap(bitmap);
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