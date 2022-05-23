package be.kuleuven.travelrecipe.models;

//实体类，对数据对象分装

public class RecipeStep {
    private String stepNr, stepDesc;
    private String stepImg;

    public RecipeStep(String stepNr, String stepDesc, String stepImg) {
        this.stepNr = stepNr;
        this.stepDesc = stepDesc;
        this.stepImg = stepImg;
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

    public String getStepImg() {
        return stepImg;
    }

    public void setStepImg(String stepImg) {
        this.stepImg = stepImg;
    }
}
