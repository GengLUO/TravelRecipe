package be.kuleuven.travelrecipe.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.ViewPagerAdapter;

public class MeFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView imgMore;
    private ImageView imgSetting;
    private RecipeFragment recipeFragment;
    private WorkFragment workFragment;

    public MeFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_me, container, false);

        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        imgMore = view.findViewById(R.id.img_More);
        imgSetting = view.findViewById(R.id.img_Setting);

        recipeFragment = new RecipeFragment();
        workFragment = new WorkFragment();

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(recipeFragment,"recipe");
        viewPagerAdapter.addFragment(workFragment,"work");
        viewPager.setAdapter(viewPagerAdapter);

        return view;
    }

}