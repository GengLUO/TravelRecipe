package be.kuleuven.travelrecipe.models;

import android.graphics.Bitmap;

public abstract class AbstractRecipeStep {
    private String stepNr, stepDesc;
    private Bitmap stepImg;

    public abstract Bitmap getStepImg();
}
