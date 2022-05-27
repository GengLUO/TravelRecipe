package be.kuleuven.travelrecipe.adapters;

import android.content.Context;
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
import be.kuleuven.travelrecipe.models.Country;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>{
    private List<Country> list;
    private Context context;
    private int continentNumber;
    //private List<Integer> countryPictureList;
    private final int[] flags = {R.drawable.spain, R.drawable.germany, R.drawable.denmark, R.drawable.sweden,
            R.drawable.france,R.drawable.belgium,R.drawable.italy,R.drawable.england,R.drawable.netherland,R.drawable.ukraine,R.drawable.korea,R.drawable.korea,R.drawable.japan,
    R.drawable.singapore,R.drawable.indonesia,R.drawable.philippines,R.drawable.thai,R.drawable.vietname,R.drawable.india,R.drawable.turkey,R.drawable.agentina,R.drawable.unitedstate,R.drawable.canada,R.drawable.brazil
    };

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
        holder.number.setText(String.valueOf(model.getRecipeNumber()));
        holder.countryname.setText(model.getCountryName());
        holder.imgCountry.setImageResource(flags[model.getCountryImg()-1]);
        System.out.println("0");

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
