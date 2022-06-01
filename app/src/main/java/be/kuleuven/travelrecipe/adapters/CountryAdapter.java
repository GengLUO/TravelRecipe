package be.kuleuven.travelrecipe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.base.CountryImageTranslation;
import be.kuleuven.travelrecipe.models.country.Country;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>{
    private List<Country> list;
    private Context context;
    private int continentNumber;
    //private List<Integer> countryPictureList;

    public CountryAdapter(List<Country> list,int continentNumber, Context context) {
        this.list = list;
        this.context = context;
        this.continentNumber = continentNumber;
    }


    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country,parent,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country model = list.get(position);
        CountryImageTranslation countryImageTranslation = new CountryImageTranslation();
        holder.number.setText(String.valueOf(model.getRecipeNumber()));
        holder.countryname.setText(model.getCountryName());
        holder.imgCountry.setText(countryImageTranslation.generateFlag(model.getCountryName()));


    }
    @Override
    public int getItemCount(){return list.size();}


    public class CountryViewHolder extends RecyclerView.ViewHolder {

        TextView imgCountry;
        TextView countryname,number;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCountry = itemView.findViewById(R.id.CountryFlags);
            countryname = itemView.findViewById(R.id.textViewRegionName);
            number = itemView.findViewById(R.id.textViewNumber);
        }

    }
}
