package be.kuleuven.travelrecipe.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class RecipeInfo implements Parcelable {

    private String name, description;
    private int country, recipeId;
    private Bitmap demo;

    public RecipeInfo(String title, String desc , int id, Bitmap demo) {
        this.name = title;
        this.description = desc;
        this.recipeId = id;
        this.demo = demo;
    }
    public RecipeInfo(int recipeId, String name, String description, int country, Bitmap demo) {
        this.recipeId = recipeId;
        this.name = name;
        this.description = description;
        this.country = country;
        this.demo = demo;
    }

    protected RecipeInfo(Parcel in) {
        name = in.readString();
        description = in.readString();
        country = in.readInt();
        recipeId = in.readInt();
        demo = in.readParcelable(Bitmap.class.getClassLoader());
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
