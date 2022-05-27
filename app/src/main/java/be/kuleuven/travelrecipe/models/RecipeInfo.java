package be.kuleuven.travelrecipe.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RecipeInfo implements Parcelable{

    private String name, description;
    private int country, recipeId;
    private Bitmap demo;
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    public RecipeInfo(String name, String description, int country, int recipeId, List<RecipeIngredient> ingredients) {
        this.name = name;
        this.description = description;
        this.country = country;
        this.recipeId = recipeId;
        this.ingredients = ingredients;
    }

    public RecipeInfo(String name, String description, int country, int recipeId, Bitmap demo, List<RecipeIngredient> ingredients) {
        this.name = name;
        this.description = description;
        this.country = country;
        this.recipeId = recipeId;
        this.demo = demo;
        this.ingredients = ingredients;
    }

    public RecipeInfo(String name, String description, int country, int recipeId, Bitmap demo) {
        this.name = name;
        this.description = description;
        this.country = country;
        this.recipeId = recipeId;
        this.demo = demo;
    }

    protected RecipeInfo(Parcel in) {
        name = in.readString();
        description = in.readString();
        country = in.readInt();
        recipeId = in.readInt();
        ingredients = new ArrayList<>();
        in.readList(ingredients,RecipeIngredient.class.getClassLoader());
    }

    public static final Creator<RecipeInfo> CREATOR = new Creator<RecipeInfo>() {
        @Override
        public RecipeInfo createFromParcel(Parcel in) {
            return new RecipeInfo(in);
        }

        @Override
        public RecipeInfo[] newArray(int size) {
            return new RecipeInfo[size];
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

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredients(RecipeIngredient ingredient){
        ingredients.add(ingredient);
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
        parcel.writeList(ingredients);
    }
}
