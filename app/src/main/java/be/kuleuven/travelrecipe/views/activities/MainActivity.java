//package be.kuleuven.travelrecipe.views.activities;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import be.kuleuven.travelrecipe.R;
//
//public class MainActivity extends AppCompatActivity {
//
////    private ViewPager viewPager;
////    private TabLayout tabLayout;
////    private ImageView imgMore;
////    private ImageView imgSetting;
////    private ImageView imgSearch;
////    private ImageView imgHome;
////    private ImageView imgMe;
////
////    private RecipeFragment recipeFragment;
////    private WorkFragment workFragment;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
////        viewPager = findViewById(R.id.view_pager);
////        tabLayout = findViewById(R.id.tab_layout);
////        imgMore = findViewById(R.id.img_More);
////        imgSetting = findViewById(R.id.img_Setting);
////        imgSearch = findViewById(R.id.img_search);
////        imgHome = findViewById(R.id.img_home);
////        imgMe = findViewById(R.id.img_me);
////
////        recipeFragment = new RecipeFragment();
////        workFragment = new WorkFragment();
////
////        tabLayout.setupWithViewPager(viewPager);
////        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
////        viewPagerAdapter.addFragment(recipeFragment,"recipe");
////        viewPagerAdapter.addFragment(workFragment,"work");
////        viewPager.setAdapter(viewPagerAdapter);
//
//        //initialize and assign variable
//        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav);
//
//        //set home selected
//        bottomNavigationView.setSelectedItemId(R.id.search);
//
//        //perforn ItemSelectedListener
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.search:
//                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.me:
//                        startActivity(new Intent(getApplicationContext(),MeActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.homePage:
//                        startActivity(new Intent(getApplicationContext(), Homepage.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//                return false;
//            };
//
//    });
//
////    public void onImgSearch_Clicked(View caller){
////        startActivity(new Intent(this, DashboardActivity.class));
////    }
////
////    public void onImgHome_Clicked(View caller){
////        startActivity(new Intent(this, Homepage.class));
////    }
////
////    public void onImgMe_Clicked(View caller){
////        startActivity(new Intent(this, MainActivity.class));
//    }
//}
package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.RecipesDashboard;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavController navController;
    private int userID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.btm_nav);
        navController = Navigation.findNavController(this, R.id.nav_controller);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                bottomNavigationView.getMenu()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);


//        requestQueue = Volley.newRequestQueue(this);
//        {
//            //Standard Volley request. We don't need any parameters for this one
//            JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, GET_IMAGE_URL, null,
//                    new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try
//                            {
//                                //Check if the DB actually contains an image
//                                System.out.println("lsz sb");
//                                if( response.length() > 0 ) {
//                                    for(int i=0; i<response.length();i++){
//                                        JSONObject o = response.getJSONObject(0);
//
//                                        //converting base64 string to image
//                                        String name = o.getString("name");
//                                        String desc = o.getString("recipe_desc");
//                                        String b64String = o.getString("recipe_image");
//                                        byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
//                                        Bitmap bitmap = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
//
//                                        //Link the bitmap to the ImageView, so it's visible on screen
//                                        //imageRetrieved.setImageBitmap( bitmap2 );
//                                        recipesModel.addRecipe(new Recipe(name,desc,bitmap));
//
//                                        //Just a double-check to tell us the request has completed
//                                        Toast.makeText(MainActivity.this, "Image retrieved from DB", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                            catch( JSONException e )
//                            {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(MainActivity.this, "Unable to communicate with server", Toast.LENGTH_LONG).show();
//                        }
//                    }
//            );
//
//            requestQueue.add(retrieveImageRequest);
//        }
    }

    public void onButtonCreateRecipeClicked(View caller)
    {
        Intent intent = new Intent(this, UploadRecipeActivity.class);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
    public void onImgSetting_Clicked(View caller){
        Intent intent = new Intent(this,SettingMain.class);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
    public void onImgContinent1_Clicked(View caller){
        Intent intent = new Intent(this,CountrysActivity.class);
        intent.putExtra("continentnumber",1);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
    public void onImgContinent2_Clicked(View caller){
        Intent intent = new Intent(this,CountrysActivity.class);
        intent.putExtra("continentnumber",2);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
    public void onImgContinent3_Clicked(View caller){
        Intent intent = new Intent(this,CountrysActivity.class);
        intent.putExtra("continentnumber",3);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
    public void onImgContinent4_Clicked(View caller){
        Intent intent = new Intent(this,CountrysActivity.class);
        intent.putExtra("continentnumber",4);
        intent.putExtra("userid",userID);
        startActivity(intent);
    }
}