package calle.david.udacityrecipeapp.UI.Adapters;

import android.support.v7.widget.RecyclerView;

import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class RecipeCardViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recipeCard_Item_Image) public ImageView recipeImage;
    @BindView(R.id.recipeCard_Item_Title) public TextView recipeTitle;
    @BindView(R.id.recipeCard_Servings_text) public TextView recipeServingSize;

    public RecipeCardViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
