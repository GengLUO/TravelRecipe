package be.kuleuven.travelrecipe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.RecipeStep;

public class DetailsAdapter extends BaseAdapter {

    private List<RecipeStep> list;
    private Context context;
    private LayoutInflater inflater;


    public DetailsAdapter(List<RecipeStep> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view = inflater.inflate(R.layout.recipe_step,null);
        //final ViewHolder holder = getViewHolder(view);
        ViewHolder holder = getViewHolder(view);

        RecipeStep model = list.get(i);
        holder.imgStep.setImageBitmap(model.getStepImg());
        holder.txtStepNr.setText(model.getStepNr());
        holder.txtStepDesc.setText(model.getStepDesc());
        return view;
    }

    private ViewHolder getViewHolder(View view){
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder == null){
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    private class ViewHolder{
        private ImageView imgStep;
        private TextView txtStepNr,txtStepDesc;

        ViewHolder(View view){
            imgStep = view.findViewById(R.id.img_step);
            txtStepNr = view.findViewById(R.id.txt_step_nr);
            txtStepDesc = view.findViewById(R.id.txt_step_desc);
        }
    }
}
