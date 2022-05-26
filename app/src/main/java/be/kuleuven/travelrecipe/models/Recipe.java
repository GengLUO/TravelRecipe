package be.kuleuven.travelrecipe.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    private String name, description;
    private int country, recipeId;
    private Bitmap demo;
    private List<RecipeStep> steps = new ArrayList<>();

    public Recipe(String title, String desc , int id, Bitmap demo) {
        this.name = title;
        this.description = desc;
        this.recipeId = id;
        this.demo = demo;
    }
    public Recipe(int recipeId, String name, String description, int country,Bitmap demo) {
        this.recipeId = recipeId;
        this.name = name;
        this.description = description;
        this.country = country;
        this.demo = demo;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        description = in.readString();
        country = in.readInt();
        recipeId = in.readInt();
        demo = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public Bitmap getDemo() {
        return demo;
    }

    public void setDemo(Bitmap demo) {
        this.demo = demo;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
    }

    public void addRecipeSteps(RecipeStep recipeStep){
        steps.add(recipeStep);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(country);
        parcel.writeInt(recipeId);
        parcel.writeParcelable(demo, i);
    }
}
