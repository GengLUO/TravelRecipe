package be.kuleuven.travelrecipe.controller;

import be.kuleuven.travelrecipe.models.RecipesDashboard;

public class RecipeController {
    private RecipesDashboard model;

    public RecipeController(){
        model = new RecipesDashboard();
    }

    public void loadMoreRecipes(){
        //访问数据库
        //增加数据
        //model.addRecipes();
    }

    //listener
    public void add(onMoreListener listener) {

    }

    //回调接口
    public interface onMoreListener {
        void onComplete();
    }
}
