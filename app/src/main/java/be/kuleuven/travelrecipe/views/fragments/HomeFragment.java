package be.kuleuven.travelrecipe.views.fragments;

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
import be.kuleuven.travelrecipe.models.Country;
import be.kuleuven.travelrecipe.models.User;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


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
    private RequestQueue requestQueue;
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

        retrieveUserInfo(1);
        retrieveCountries(1);

        //set content

        recipeAmountTextview.setText(user.getRecipeAmount());
        recipeAmountText.setText("recipeAmount");
        usernameTextview.setText(user.getUserName());
        levelText.setText("level");
        levelTextview.setText(user.getLevel());
        //question setting progressbar
        progressBar1.setProgress(10);

        return view;

    }

    public User retrieveUserInfo(int userID)
    {
        //get username, recipeAmount, level
        user.setUserID(userID);
        String infoURL = URL+'/'+"getUserinfo"+'/'+user.getUserID();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, infoURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
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
                        ;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        return user;
    }
    public User retrieveCountries(int userID)
    {
        String countriesURL = "";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, countriesURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
                for (int i = 0; i < response.length(); i++) {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(0);
                        int countryImg;
                        String countryName;
                        int recipeNumber;
                        int actived;
                        boolean ac;
                        int continent;
                        countryImg = o .getInt("idcountries");
                        countryName = o.getString("name");
                        recipeNumber = o.getInt("recipeNumber");
                        actived = o.getInt("actived");
                        continent = o.getInt("continent");
                        if (actived == 0){ac = false;}
                        else {ac = true;}
                        Country country = new Country(countryImg,countryName,recipeNumber,ac,continent);
                        user.getCountries().add(country);
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
        return user;
    }

}