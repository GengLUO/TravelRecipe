package be.kuleuven.travelrecipe.models;

public abstract class AbstractRecipeStep {
    private String stepNr, stepDesc;
    private String stepImg;

    public abstract String getStepImg();
}
