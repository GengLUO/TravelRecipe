package be.kuleuven.travelrecipe.models;

//实体类，对数据对象分装

import android.graphics.Bitmap;

public class RecipeStep{
    private String stepNr, stepDesc;
    private Bitmap stepImg;

    public RecipeStep(String stepNr, String stepDesc, Bitmap stepImg) {
        this.stepNr = stepNr;
        this.stepDesc = stepDesc;
        this.stepImg = stepImg;
    }

    public RecipeStep(String stepNr, String stepDesc) {
        this.stepNr = stepNr;
        this.stepDesc = stepDesc;
    }

    public String getStepNr() {
        return stepNr;
    }

    public void setStepNr(String stepNr) {
        this.stepNr = stepNr;
    }

    public String getStepDesc() {
        return stepDesc;
    }

    public void setStepDesc(String stepDesc) {
        this.stepDesc = stepDesc;
    }

    public Bitmap getStepImg() {
        return stepImg;
    }

    public void setStepImg(Bitmap stepImg) {
        this.stepImg = stepImg;
    }
}
