package calle.david.udacityrecipeapp.UI.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import calle.david.udacityrecipeapp.Data.Database.Recipe;
import calle.david.udacityrecipeapp.R;

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardViewHolder> {
    private Context context;
    private List<Recipe> recipeList;

    public RecipeCardAdapter(Context context){
        this.context = context;
        recipeList = new ArrayList<>();
    }
    public void addRecipeList(List<Recipe> recipeList){
        this.recipeList.clear();
        this.recipeList.addAll(recipeList);
    }
    @NonNull
    @Override
    public RecipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recipe_card_row,parent,false);
        return new RecipeCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCardViewHolder holder, int position) {
        String recipeTitle = recipeList.get(position).getName();
        String recipeImage = recipeList.get(position).getImage();
        String recipeNumOfSteps = String.valueOf(recipeList.get(position).getNumOfSteps());
        if(recipeImage!= null && recipeImage.trim().length() != 0){
            Picasso.get().load(recipeImage).into(holder.recipeImage);

        }
        holder.recipeTitle.setText(recipeTitle);
        holder.recipeNumOfSteps.setText(context.getString(R.string.number_of_steps)+recipeNumOfSteps);


    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
