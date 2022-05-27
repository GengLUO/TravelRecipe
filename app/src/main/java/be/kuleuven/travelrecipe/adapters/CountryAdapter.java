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
    private List<Integer> countryPictureList;

    public CountryAdapter(List<Country> list,int continentNumber, Context context) {
        this.list = list;
        this.context = context;
        this.continentNumber = continentNumber;
        this.generateCountryList();
    }

    private void generateCountryList()
    {
        countryPictureList = new ArrayList<Integer>();
        countryPictureList.add(R.drawable.spain);
        countryPictureList.add(R.drawable.germany);
        countryPictureList.add(R.drawable.denmark);
        countryPictureList.add(R.drawable.sweden);
        countryPictureList.add(R.drawable.france);
        countryPictureList.add(R.drawable.belgium);
        countryPictureList.add(R.drawable.italy);
        countryPictureList.add(R.drawable.england);
        countryPictureList.add(R.drawable.netherland);
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
        //
        holder.imgCountry.setImageResource(countryPictureList.get(model.getCountryImg()-1));




        //
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
