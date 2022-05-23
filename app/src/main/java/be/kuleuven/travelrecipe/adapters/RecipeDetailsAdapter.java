package be.kuleuven.travelrecipe.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.views.activities.DetailActivity;
import be.kuleuven.travelrecipe.models.RecipeStep;

public class RecipeDetailsAdapter extends  RecyclerView.Adapter<RecipeDetailsAdapter.detailsViewHolder>{

    private List<RecipeStep> list;
    private Context context;

    public RecipeDetailsAdapter(List<RecipeStep> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public detailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_step,parent,false);
        return new detailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull detailsViewHolder holder, int position) {
        RecipeStep model = list.get(position);
        holder.imgStep.setImageBitmap(model.getStepImg());
        holder.txtStepNr.setText(model.getStepNr());
        holder.txtStepDesc.setText(model.getStepDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class detailsViewHolder extends RecyclerView.ViewHolder {

        ImageView imgStep;
        TextView txtStepNr, txtStepDesc;

        public detailsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgStep = itemView.findViewById(R.id.img_step);
            txtStepNr = itemView.findViewById(R.id.txt_step_nr);
            txtStepDesc = itemView.findViewById(R.id.txt_step_desc);
        }
    }
}
