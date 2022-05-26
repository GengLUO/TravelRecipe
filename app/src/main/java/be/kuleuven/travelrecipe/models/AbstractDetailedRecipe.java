package be.kuleuven.travelrecipe.models;

import be.kuleuven.travelrecipe.adapters.DetailNotifier;

public class AbstractDetailedRecipe {
    protected DetailNotifier detailNotifier;
    public final void setDetailNotifier(DetailNotifier detailNotifier){
        this.detailNotifier = detailNotifier;
    }
}
