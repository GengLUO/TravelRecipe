package be.kuleuven.travelrecipe.views.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.DashboardAdapter;
import be.kuleuven.travelrecipe.models.Dashboard;


public class SearchFragment extends Fragment {

    RecyclerView dashboardRecyclerView;
    DashboardAdapter dashboardAdapter;
    List<Dashboard> dashboardModelList;

    private RequestQueue requestQueue;
    private static final String GET_IMAGE_URL = "https://studev.groept.be/api/a21pt210/getLastImage";
    private int PICK_IMAGE_REQUEST = 111;
    private Bitmap bitmap;
    private ProgressDialog progressDialog;

    public SearchFragment() {
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
        requestQueue = Volley.newRequestQueue(getContext());
        {
            //Standard Volley request. We don't need any parameters for this one
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
                                    bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

                                    //Link the bitmap to the ImageView, so it's visible on screen
                                    //imageRetrieved.setImageBitmap( bitmap2 );

                                    Toast.makeText(getContext(), "Image retrieved from DB", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), "Unable to communicate with server", Toast.LENGTH_LONG).show();
                        }
                    }
            );

            requestQueue.add(retrieveImageRequest);
        }
        dashboardModelList = new ArrayList<>();
        dashboardModelList.add(new Dashboard(bitmap,bitmap,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(bitmap,bitmap,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(bitmap,bitmap,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(bitmap,bitmap,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(bitmap,bitmap,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(bitmap,bitmap,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(bitmap,bitmap,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(bitmap,bitmap,"水晶虾饺","很好吃"));
        dashboardModelList.add(new Dashboard(bitmap,bitmap,"水晶虾饺","很好吃"));

        dashboardRecyclerView = view.findViewById(R.id.recycler_view);
        dashboardAdapter = new DashboardAdapter(dashboardModelList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        dashboardRecyclerView.setLayoutManager(layoutManager);
        dashboardRecyclerView.setNestedScrollingEnabled(false);
        dashboardRecyclerView.setAdapter(dashboardAdapter);

        return view;
    }
}