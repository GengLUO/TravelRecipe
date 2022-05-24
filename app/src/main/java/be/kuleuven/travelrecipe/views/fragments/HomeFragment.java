package be.kuleuven.travelrecipe.views.fragments;
//2754
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.HomepageFragmentNotifier;
import be.kuleuven.travelrecipe.controller.DatabaseConnect;
import be.kuleuven.travelrecipe.models.Country;
import be.kuleuven.travelrecipe.models.User;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomepageFragmentNotifier {
    private TextView recipeAmountText;
    private TextView recipeAmountTextview;
    private TextView levelText;
    private TextView levelTextview;
    private TextView usernameTextview;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView profileImage;
    private ProgressBar progressBar1;
    RequestQueue requestQueue;
    private static final String URL = "https://studev.groept.be/api/a21pt210";

    private User user;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recipeAmountText = view.findViewById(R.id.recipeAmountText);
        recipeAmountTextview = view.findViewById(R.id.recipeAmountTextview);
        levelTextview = view.findViewById(R.id.levelTextview);
        levelText = view.findViewById(R.id.levelText);
        usernameTextview = view.findViewById(R.id.usernameTextview);
        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);
        imageView4 = view.findViewById(R.id.imageView4);
        progressBar1 = view.findViewById(R.id.progressBar1);
        profileImage = view.findViewById(R.id.profileImage);
        requestQueue = Volley.newRequestQueue( getContext() );
        user = new User(1);
        user.setHomepageFragmentNotifier(this);
        DatabaseConnect databaseConnect = new DatabaseConnect(requestQueue);
        databaseConnect.retrieveUserInfo(user);
        //add progress bar!!!!!!





        //
        return view;
    }


    @Override
    public void notifyNameChanged() {
        usernameTextview.setText(user.getUserName());
    }

    @Override
    public void notifyLevelChanged() {
        levelTextview.setText(String.valueOf(user.getLevel()));
    }
    @Override
    public void notifyRecipeNumberChanged() {
        recipeAmountTextview.setText(String.valueOf(user.getRecipeAmount()));
    }
    @Override
    public void notifyImageChanged() {
        profileImage.setImageBitmap(user.getImage());
    }
}