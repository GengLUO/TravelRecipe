package be.kuleuven.travelrecipe.views.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.notifier.HomepageFragmentNotifier;
import be.kuleuven.travelrecipe.models.user.User;
import be.kuleuven.travelrecipe.views.activities.MainActivity;


public class HomeFragment extends Fragment implements HomepageFragmentNotifier {

    private TextView usernameTextview;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView profileImage;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
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
        initView(view);
        initModel();
        return view;
    }

    public void initView(View view) {
        usernameTextview = view.findViewById(R.id.usernameTextview);
        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);
        imageView4 = view.findViewById(R.id.imageView4);
        progressBar1 = view.findViewById(R.id.progressBar1);
        progressBar2 = view.findViewById(R.id.progressBar2);
        progressBar3 = view.findViewById(R.id.progressBar3);
        progressBar4 = view.findViewById(R.id.progressBar4);
        profileImage = view.findViewById(R.id.profileImage);
        imageView1.setImageResource(R.drawable.asia);
        imageView2.setImageResource(R.drawable.europe);
        imageView3.setImageResource(R.drawable.america);
        imageView4.setImageResource(R.drawable.africa);
    }

    private void initModel() {
        user = new User(MainActivity.userID);
        user.setHomepageFragmentNotifier(this);
        MainActivity.databaseConnect.retrieveUserInfo(user);
        MainActivity.databaseConnect.retrieveContinentInfo(user);
    }

    @Override
    public void notifyNameChanged() {
        usernameTextview.setText(user.getUserName());
    }

    @Override
    public void notifyLevelChanged() {

    }

    @Override
    public void notifyRecipeNumberChanged() {
    }

    @Override
    public void notifyImageChanged() {
        profileImage.setImageBitmap(user.getImage());
    }

    @Override
    public void notifyAsiaChanged() {
        progressBar1.setProgress(user.getCountryAsiaAmount()*10);
    }

    @Override
    public void notifyEuropeChanged() {
        progressBar2.setProgress(user.getCountryEuropeAmount()*10);
    }

    @Override
    public void notifyAmericaChanged() {
        progressBar3.setProgress(user.getCountryAmericaAmount()*10);
    }

    @Override
    public void notifyAfricaChanged() {
        progressBar4.setProgress(user.getCountryAfricaAmount()*10);
    }
}