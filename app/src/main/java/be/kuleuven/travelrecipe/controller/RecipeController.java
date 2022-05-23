package be.kuleuven.travelrecipe.controller;

import be.kuleuven.travelrecipe.models.RecipesModel;

public class RecipeController {
    private RecipesModel model;

    public RecipeController(){
        model = new RecipesModel();
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
