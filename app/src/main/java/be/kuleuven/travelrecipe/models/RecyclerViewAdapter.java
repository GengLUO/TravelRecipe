package be.kuleuven.travelrecipe.models;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.LinearViewHolder> {

    private List<Recipe> recipes;

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
