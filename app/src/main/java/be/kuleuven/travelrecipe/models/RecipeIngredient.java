package be.kuleuven.travelrecipe.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeIngredient implements Parcelable {
    private String name;
    private String amount;

    public RecipeIngredient(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    protected RecipeIngredient(Parcel in) {
        name = in.readString();
        amount = in.readString();
    }

    public static final Creator<RecipeIngredient> CREATOR = new Creator<RecipeIngredient>() {
        @Override
        public RecipeIngredient createFromParcel(Parcel in) {
            return new RecipeIngredient(in);
        }

        @Override
        public RecipeIngredient[] newArray(int size) {
            return new RecipeIngredient[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(amount);
    }
}
