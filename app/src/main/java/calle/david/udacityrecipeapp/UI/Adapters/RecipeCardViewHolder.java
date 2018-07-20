package calle.david.udacityrecipeapp.UI.Adapters;

import android.support.v7.widget.RecyclerView;

import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class RecipeCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.recipeCard_Item_Image) public ImageView recipeImage;
    @BindView(R.id.recipeCard_Item_Title) public TextView recipeTitle;
    @BindView(R.id.recipeCard_Steps_text) public TextView recipeNumOfSteps;

    private RecipeCardAdapter.RecipeCardAdapterOnClickListener onItemClickListener;

    public RecipeCardViewHolder(View itemView, RecipeCardAdapter.RecipeCardAdapterOnClickListener listener){
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        this.onItemClickListener = listener;

    }

    @Override
    public void onClick(View v){
        if(onItemClickListener != null){
            onItemClickListener.onItemClick(getAdapterPosition());
        }

    }
}
