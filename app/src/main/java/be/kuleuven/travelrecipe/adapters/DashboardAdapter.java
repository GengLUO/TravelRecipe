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

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.base.CountryImageTranslation;
import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;
import be.kuleuven.travelrecipe.views.activities.DetailActivity;
import be.kuleuven.travelrecipe.views.activities.MainActivity;

public class DashboardAdapter extends  RecyclerView.Adapter<DashboardAdapter.dashboardViewHolder>{

    private List<RecipeInfo> list = new ArrayList<>();
    private final Context context;
    private CountryImageTranslation countryImageTranslation = new CountryImageTranslation();

    public DashboardAdapter(List<RecipeInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public DashboardAdapter(Context context){
        this.context = context;
    }

    public void setList(List<RecipeInfo> newList){
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public dashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_fall,parent,false);
        return new dashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dashboardViewHolder holder, int position) {
        RecipeInfo recipe = list.get(position);
        holder.flag.setText(countryImageTranslation.generateFlag(recipe.getCountryname()));
        holder.imgDish.setImageBitmap(recipe.getDemo());
        holder.txtName.setText(recipe.getName());
        holder.txtDescription.setText(recipe.getDescription());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("Recipe",  recipe);
            intent.putExtra("id", MainActivity.userID);
            System.out.println(recipe.getIngredients());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class dashboardViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDish;
        TextView txtName, txtDescription, flag;

        public dashboardViewHolder(@NonNull View itemView) {
            super(itemView);

            imgDish = itemView.findViewById(R.id.img_dish);
            flag = itemView.findViewById(R.id.flag);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDescription = itemView.findViewById(R.id.txt_descr);
        }
    }
}
