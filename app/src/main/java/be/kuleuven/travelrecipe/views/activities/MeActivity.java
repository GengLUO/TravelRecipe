package be.kuleuven.travelrecipe.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import be.kuleuven.travelrecipe.controller.ViewPagerAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.views.fragments.RecipeFragment;
import be.kuleuven.travelrecipe.views.fragments.WorkFragment;

public class MeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView imgMore;
    private ImageView imgSetting;
    private RecipeFragment recipeFragment;
    private WorkFragment workFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        imgMore = findViewById(R.id.img_More);
        imgSetting = findViewById(R.id.img_Setting);

        recipeFragment = new RecipeFragment();
        workFragment = new WorkFragment();

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(recipeFragment,"recipe");
        viewPagerAdapter.addFragment(workFragment,"work");
        viewPager.setAdapter(viewPagerAdapter);

        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.me);

        //perforn ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.me:
                        return true;

                    case R.id.homePage:
                        startActivity(new Intent(getApplicationContext(), Homepage.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            };

        });
    }
}