package calle.david.udacityrecipeapp.UI.Adapters;

import android.support.v7.widget.RecyclerView;
import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class IngredientListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.IngredientName) public TextView ingredientName;
   // @BindView(R.id.IngredientMeasure)public TextView ingredientMeasure;
    @BindView(R.id.IngredientQuantity)public TextView ingredientQuantity;

    IngredientListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);
    }
}
