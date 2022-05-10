package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.adapters.SmallAreaAdapter;
import be.kuleuven.travelrecipe.models.SmallArea;

public class MedalActivity extends AppCompatActivity {
    private SmallAreaAdapter smallAreaAdapter;
    private List<SmallArea> smallAreas;
    private RecyclerView smallareasRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medal);

        smallAreas = new ArrayList<>();
        smallAreas.add(new SmallArea("china",1,1,"china",1,1,"china",1,1));
        smallAreas.add(new SmallArea("china",1,1,"china",1,1,"china",1,1));
        smallAreas.add(new SmallArea("china",1,1,"china",1,1,"china",1,1));
        smallAreas.add(new SmallArea("china",1,1,"china",1,1,"china",1,1));
        smallAreas.add(new SmallArea("china",1,1,"china",1,1,"china",1,1));

    }
    private void setSmallareasRecyclerView(List<SmallArea> smallAreaList)
    {
        smallareasRecyclerView = findViewById(R.id.idmedal);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        smallareasRecyclerView.setLayoutManager(layoutManager);
        smallAreaAdapter = new SmallAreaAdapter(smallAreas,this);
        smallareasRecyclerView.setAdapter(smallAreaAdapter);
    }
    public void onReturn_Clicked(View caller)
    {
        startActivity(new Intent(this,Homepage.class));
    }
}