package be.kuleuven.travelrecipe.controller;

import be.kuleuven.travelrecipe.models.dashboard.Dashboard;

public class RecipeController {
    private Dashboard model;

    public RecipeController(){
        model = new Dashboard();
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
