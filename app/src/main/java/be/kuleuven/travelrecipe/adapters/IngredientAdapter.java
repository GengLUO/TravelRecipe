package be.kuleuven.travelrecipe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.recipe.RecipeIngredient;

public class IngredientAdapter extends BaseAdapter {

    private List<RecipeIngredient> ingredients = new ArrayList<>();
    private Context context;

    public IngredientAdapter(List<RecipeIngredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    public IngredientAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int i) {
        return ingredients.get(i);
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
        RecipeIngredient ingredient = ingredients.get(i);
        holder.txtIngNmLbl.setText(ingredient.getName());
        holder.txtIngAmntLbl.setText(ingredient.getAmount());
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
