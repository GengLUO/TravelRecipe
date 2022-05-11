package be.kuleuven.travelrecipe.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.RecipeStep;
import be.kuleuven.travelrecipe.models.SmallArea;
import be.kuleuven.travelrecipe.views.activities.DetailActivity;

public class SmallAreaAdapter extends RecyclerView.Adapter<SmallAreaAdapter.detailsViewHolder> {
    private List<SmallArea> list;
    private Context context;

    public SmallAreaAdapter(List<SmallArea> list, Context context) {
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
    public void onBindViewHolder(@NonNull SmallAreaAdapter.detailsViewHolder holder, int position) {
        SmallArea model = list.get(position);
        holder.medalnumber.setText(model.getNumber1());
        holder.medalname.setText(model.getName1());
        holder.medalimage.setImageResource(model.getPicture1());
        holder.medalnumber2.setText(model.getNumber2());
        holder.medalname2.setText(model.getName2());
        holder.medalimage2.setImageResource(model.getPicture2());
        holder.medalnumber3.setText(model.getNumber3());
        holder.medalname3.setText(model.getName3());
        holder.medalimage3.setImageResource(model.getPicture3());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class detailsViewHolder extends RecyclerView.ViewHolder {

        ImageView medalimage;
        TextView medalname;
        TextView medalnumber;
        ImageView medalimage2;
        TextView medalname2;
        TextView medalnumber2;
        ImageView medalimage3;
        TextView medalname3;
        TextView medalnumber3;

        public detailsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.medalimage = itemView.findViewById(R.id.medalimage);
            this.medalname = itemView.findViewById(R.id.medalname);
            this.medalnumber = itemView.findViewById(R.id.medalnumber);
            this.medalimage2 = itemView.findViewById(R.id.medalimage2);
            this.medalname2 = itemView.findViewById(R.id.medalname2);
            this.medalnumber2 = itemView.findViewById(R.id.medalnumber2);
            this.medalimage3 = itemView.findViewById(R.id.medalimage3);
            this.medalname3 = itemView.findViewById(R.id.medalname3);
            this.medalnumber3 = itemView.findViewById(R.id.medalnumber3);
        }
    }

}
