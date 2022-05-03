package be.kuleuven.travelrecipe.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.Adapter.RecipeDetailsAdapter;
import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.RecipeStep;

public class DetailActivity extends AppCompatActivity {

    ImageView imgBack;
    RecyclerView recipeDetailsRV;
    RecipeDetailsAdapter recipeDetailsAdapter;
    List<RecipeStep> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgBack = findViewById(R.id.img_back);

        recipeList = new ArrayList<>();
        RecipeStep step1 = new RecipeStep("Step1","111",R.drawable.step1);
        RecipeStep step2 = new RecipeStep("Step2","222",R.drawable.step2);
        RecipeStep step3 = new RecipeStep("Step3","333",R.drawable.step3);
        RecipeStep step4 = new RecipeStep("Step4","444",R.drawable.step4);
        RecipeStep step5 = new RecipeStep("Step5","555",R.drawable.step5);
//        Recipe recipe = new Recipe();
//        recipe.addRecipeSteps(step1);
//        recipe.addRecipeSteps(step2);
//        recipe.addRecipeSteps(step3);
//        recipe.addRecipeSteps(step4);
//        recipe.addRecipeSteps(step5);
        recipeList.add(step1);
        recipeList.add(step2);
        recipeList.add(step3);
        recipeList.add(step4);
        recipeList.add(step5);
        recipeList.add(step5);

        setRecipeRecyclerView(recipeList);
    }

    private void setRecipeRecyclerView(List<RecipeStep> recipeList){
        recipeDetailsRV = findViewById(R.id.details_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recipeDetailsRV.setLayoutManager(layoutManager);
        recipeDetailsAdapter = new RecipeDetailsAdapter(recipeList,this);
        recipeDetailsRV.setAdapter(recipeDetailsAdapter);
    }

    public void onImgBack_Clicked(View caller){
        onBackPressed();
    }
}