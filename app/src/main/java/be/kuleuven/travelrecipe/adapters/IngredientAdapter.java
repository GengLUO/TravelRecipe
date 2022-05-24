package be.kuleuven.travelrecipe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;

import be.kuleuven.travelrecipe.R;

public class IngredientAdapter extends BaseAdapter {

    private LinkedHashMap<String,String> ingredients;
    private Context context;

    public IngredientAdapter(LinkedHashMap ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int i) {
        String key = (String) ingredients.keySet().toArray()[i];
        return new LinkedHashMap<String, String>().put(key,ingredients.get(key));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view = LayoutInflater.from(context).inflate(R.layout.ingredient_list,null);
        //final ViewHolder holder = getViewHolder(view);
        ViewHolder holder = getViewHolder(view);

        String key = (String) ingredients.keySet().toArray()[i];
        holder.txtIngNmLbl.setText(key);
        holder.txtIngAmntLbl.setText(ingredients.get(key));
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
        private TextView txtIngNmLbl,txtIngAmntLbl;

        ViewHolder(View view){
            txtIngNmLbl = view.findViewById(R.id.txtIngNmLbl);
            txtIngAmntLbl = view.findViewById(R.id.txtIngAmntLbl);
        }
    }
}
