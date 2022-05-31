package be.kuleuven.travelrecipe.views.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.DashboardAdapter;
import be.kuleuven.travelrecipe.controller.DatabaseConnect;
import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;
import be.kuleuven.travelrecipe.models.dashboard.Dashboard;
import be.kuleuven.travelrecipe.models.user.User;
import be.kuleuven.travelrecipe.notifier.HomepageFragmentNotifier;
import be.kuleuven.travelrecipe.notifier.RecipeNotifier;
import be.kuleuven.travelrecipe.views.activities.MainActivity;

public class MeFragment extends Fragment implements RecipeNotifier {

    private ImageView imgMore;
    private ImageView imgSetting;
    private TextView usernameTextView;
    private ImageView profileImageView;
    RecyclerView listRecyclerView;
    DashboardAdapter listDashboardAdapter;
    List<RecipeInfo> listRecipeList;
    Dashboard dashboard;

    //private User user;

    private RequestQueue requestQueue;
    private String GET_LIKED_URL = "https://studev.groept.be/api/a21pt210/getLikedRecipe/";
    private ProgressDialog progressDialog;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("bundle"+getArguments().getInt("userid"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_me, container, false);

        listRecyclerView = view.findViewById(R.id.rvList);
        imgSetting = view.findViewById(R.id.img_Setting);
//        user = new User(userid);
//        user.setHomepageFragmentNotifier(this);
//        databaseConnect.retrieveUserInfo(user);
        bindAdapter();

        dashboard = new Dashboard();
        dashboard.setRecipeNotifier(this);
        requestQueue = Volley.newRequestQueue(getContext());
        DatabaseConnect databaseConnect = new DatabaseConnect(requestQueue);
        databaseConnect.requestListRecipe(dashboard);
        System.out.println("bundle"+getArguments().getInt("userID"));
        return view;
    }

    private void bindAdapter() {
        listDashboardAdapter = new DashboardAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listRecyclerView.setLayoutManager(layoutManager);
        listRecyclerView.setNestedScrollingEnabled(false);
        listRecyclerView.setAdapter(listDashboardAdapter);
    }

    @Override
    public void notifyRecipesListChanged(List<RecipeInfo> recipes) {
        listDashboardAdapter.setList(recipes);
    }
}