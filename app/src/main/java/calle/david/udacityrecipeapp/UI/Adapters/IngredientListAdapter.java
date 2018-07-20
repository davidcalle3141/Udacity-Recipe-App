package calle.david.udacityrecipeapp.UI.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import calle.david.udacityrecipeapp.Data.Database.Ingredients;
import calle.david.udacityrecipeapp.R;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListViewHolder>{
    private final Context context;
    private List<Ingredients> ingredientsList;

    public IngredientListAdapter(Context context) {
        this.context = context;
        ingredientsList = new ArrayList<>();
    }

    public void addIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList.clear();
        this.ingredientsList.addAll(ingredientsList);
    }


    @NonNull
    @Override
    public IngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recipe_ingredients_card_list,parent,false);

        return new IngredientListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientListViewHolder holder, int position) {

        String measure = ingredientsList.get(position).getMeasure();
        String quantity =ingredientsList.get(position).getQuantity()+" "+measure;
        String name =ingredientsList.get(position).getIngredientName();

        holder.ingredientQuantity.setText(quantity);
        holder.ingredientName.setText(name);
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

}
