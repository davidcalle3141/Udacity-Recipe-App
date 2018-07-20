package calle.david.udacityrecipeapp.UI.Adapters;

import android.support.v7.widget.RecyclerView;

import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
public class StepListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.ingredients_step_list_description)public TextView stepShortDescription;

    private StepListAdapter.StepListAdapterOnClickListener onItemClickListener;

    public StepListViewHolder(View itemView, StepListAdapter.StepListAdapterOnClickListener listener) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(this);
        this.onItemClickListener = listener;

    }

    @Override
    public void onClick(View view) {
        if(onItemClickListener != null){
            onItemClickListener.onClick(getAdapterPosition());
        }
    }
}
