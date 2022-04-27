package be.kuleuven.travelrecipe.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView imgMore;
    private ImageView imgSetting;

    private RecipeFragment recipeFragment;
    private WorkFragment workFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ImageView imgMore = findViewById(R.id.img_More);
        ImageView imgSetting = findViewById(R.id.img_Setting);

        recipeFragment = new RecipeFragment();
        workFragment = new WorkFragment();

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(recipeFragment,"recipe");
        viewPagerAdapter.addFragment(workFragment,"work");
        viewPager.setAdapter(viewPagerAdapter);

    }

    public void onImgSetting_Clicked(View caller){
        startActivity(new Intent(this, Settings.class));
    }
}