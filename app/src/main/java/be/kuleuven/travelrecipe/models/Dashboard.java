package be.kuleuven.travelrecipe.models;

import android.graphics.Bitmap;

//暂时废弃
public class Dashboard {
    private Bitmap dish, flag;
    private String name, description;

    public Dashboard(Bitmap dish, Bitmap flag, String name, String description) {
        this.dish = dish;
        this.flag = flag;
        this.name = name;
        this.description = description;
    }

    public Bitmap getDish() {
        return dish;
    }

    public void setDish(Bitmap dish) {
        this.dish = dish;
    }

    public Bitmap getFlag() {
        return flag;
    }

    public void setFlag(Bitmap flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
