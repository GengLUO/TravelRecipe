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
import be.kuleuven.travelrecipe.models.Dashboard;

public class DashboardAdapter extends  RecyclerView.Adapter<DashboardAdapter.dashboardViewHolder>{

    private List<Dashboard> list;
    private Context context;
    private LayoutInflater inflater;

    public DashboardAdapter(List<Dashboard> list, Context context) {
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
        Dashboard model = list.get(position);
        holder.imgFlag.setImageBitmap(model.getFlag());
        holder.imgDish.setImageBitmap(model.getDish());
        holder.txtName.setText(model.getName());
        holder.txtDescription.setText(model.getDescription());

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
