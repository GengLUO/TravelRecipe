package be.kuleuven.travelrecipe.models.recipe;

import be.kuleuven.travelrecipe.notifier.DetailNotifier;

public class AbstractDetailedRecipe {
    protected DetailNotifier detailNotifier;
    public final void setDetailNotifier(DetailNotifier detailNotifier){
        this.detailNotifier = detailNotifier;
    }
}
