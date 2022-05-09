package be.kuleuven.travelrecipe.controller;

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

    List<RecipeStep> list;
    Context context;

    public DetailsAdapter(List<RecipeStep> list, Context context) {
        this.list = list;
        this.context = context;
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
            view = LayoutInflater.from(context).inflate(R.layout.recipe_step,null);
            ImageView imgStep = view.findViewById(R.id.img_step);
            TextView txtStepNr = view.findViewById(R.id.txt_step_nr);
            TextView txtStepDesc = view.findViewById(R.id.txt_step_desc);
            RecipeStep model = list.get(i);
            imgStep.setImageResource(model.getStepImg());
            txtStepNr.setText(model.getStepNr());
            txtStepDesc.setText(model.getStepDesc());
        return view;
    }

}
