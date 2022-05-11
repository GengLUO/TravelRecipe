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
import be.kuleuven.travelrecipe.models.Country;
import be.kuleuven.travelrecipe.models.Dashboard;
import be.kuleuven.travelrecipe.views.activities.DetailActivity;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>{
    private List<Country> list;
    private Context context;

    public CountryAdapter(List<Country> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_fall,parent,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country model = list.get(position);
        holder.number.setText(model.getNumber());
        holder.countryname.setText(model.getCountryname());
        holder.imgCountry.setImageResource(model.getImage());

    }
    @Override
    public int getItemCount(){return list.size();}


    public class CountryViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCountry;
        TextView countryname,number;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCountry = itemView.findViewById(R.id.imageViewCountry);
            countryname = itemView.findViewById(R.id.textViewRegionName);
            number = itemView.findViewById(R.id.textViewNumber);
        }
    }
}
