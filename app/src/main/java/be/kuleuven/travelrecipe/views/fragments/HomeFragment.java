package be.kuleuven.travelrecipe.views.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.views.activities.Homepage;


public class HomeFragment extends Fragment {
    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private ImageView profileImage;
    private TextView recipeAmountText;
    private TextView recipeAmountTextview;
    private TextView levelText;
    private TextView levelTextview;
    private TextView usernameTextview;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private ImageView settingButton;
    private static final String URL = "https://studev.groept.be/api/a21pt210";
    private String username;
    private int userID;
    private int level;
    private int recipeAmount;

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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        viewPager = view.findViewById(R.id.homePage);
        profileImage = view.findViewById(R.id.profileImage);
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
        progressBar2 = view.findViewById(R.id.progressBar2);
        progressBar3 = view.findViewById(R.id.progressBar3);
        progressBar4 = view.findViewById(R.id.progressBar4);
        settingButton = view.findViewById(R.id.settingButton);




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
            }
        });
        recipeAmountTextview.setText(recipeAmount);
        recipeAmountText.setText("recipeAmount");
        usernameTextview.setText(username);
        levelText.setText("level");
        levelTextview.setText(level);
        return view;
    }
}