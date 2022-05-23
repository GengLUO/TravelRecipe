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

import java.util.LinkedList;
import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.Recipe;
import be.kuleuven.travelrecipe.views.activities.DetailActivity;

public class DashboardAdapter extends  RecyclerView.Adapter<DashboardAdapter.dashboardViewHolder>{

    private List<Recipe> list = new LinkedList<>();
    private Context context;
    private LayoutInflater inflater;

    public DashboardAdapter(List<Recipe> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public dashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recipe_fall,parent,false);
        return new dashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dashboardViewHolder holder, int position) {
        Recipe recipe = list.get(position);
        //Todo
        //holder.imgFlag.setImageBitmap(recipe.getCountry());
        holder.imgDish.setImageBitmap(recipe.getDemo());
        holder.txtName.setText(recipe.getName());
        holder.txtDescription.setText(recipe.getDescription());

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

    public class dashboardViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDish, imgFlag;
        TextView txtName, txtDescription;

        public dashboardViewHolder(@NonNull View itemView) {
            super(itemView);

            imgDish = itemView.findViewById(R.id.img_dish);
            imgFlag = itemView.findViewById(R.id.img_flag);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDescription = itemView.findViewById(R.id.txt_descr);
        }
    }
}
