package be.kuleuven.travelrecipe.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import be.kuleuven.travelrecipe.R;

public class MainActivity extends AppCompatActivity {

//    private ViewPager viewPager;
//    private TabLayout tabLayout;
//    private ImageView imgMore;
//    private ImageView imgSetting;
//    private ImageView imgSearch;
//    private ImageView imgHome;
//    private ImageView imgMe;
//
//    private RecipeFragment recipeFragment;
//    private WorkFragment workFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        viewPager = findViewById(R.id.view_pager);
//        tabLayout = findViewById(R.id.tab_layout);
//        imgMore = findViewById(R.id.img_More);
//        imgSetting = findViewById(R.id.img_Setting);
//        imgSearch = findViewById(R.id.img_search);
//        imgHome = findViewById(R.id.img_home);
//        imgMe = findViewById(R.id.img_me);
//
//        recipeFragment = new RecipeFragment();
//        workFragment = new WorkFragment();
//
//        tabLayout.setupWithViewPager(viewPager);
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        viewPagerAdapter.addFragment(recipeFragment,"recipe");
//        viewPagerAdapter.addFragment(workFragment,"work");
//        viewPager.setAdapter(viewPagerAdapter);

        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.search);

        //perforn ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.me:
                        startActivity(new Intent(getApplicationContext(),MeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.homePage:
                        startActivity(new Intent(getApplicationContext(), Homepage.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            };

    });

//    public void onImgSearch_Clicked(View caller){
//        startActivity(new Intent(this, DashboardActivity.class));
//    }
//
//    public void onImgHome_Clicked(View caller){
//        startActivity(new Intent(this, Homepage.class));
//    }
//
//    public void onImgMe_Clicked(View caller){
//        startActivity(new Intent(this, MainActivity.class));
    }
}