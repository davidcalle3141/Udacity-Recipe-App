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
import calle.david.udacityrecipeapp.Data.Database.Steps;
import calle.david.udacityrecipeapp.R;

public class StepListAdapter extends RecyclerView.Adapter<StepListViewHolder> {
    private final Context context;
    private final StepListAdapterOnClickListener onClickListener;
    private List<Steps> stepsList;

    public StepListAdapter(Context context, StepListAdapterOnClickListener listener){
        this.context = context;
        this.onClickListener = listener;
        this.stepsList = new ArrayList<>();
    }
    public void addStepList(List<Steps> stepsList){
        this.stepsList.clear();
        this.stepsList.addAll(stepsList);
    }

    @NonNull
    @Override
    public StepListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recipe_ingredients_step_list,parent,false);
        return new StepListViewHolder(view, onClickListener );
    }

    @Override
    public void onBindViewHolder(@NonNull StepListViewHolder holder, int position) {
        String shortDescription = stepsList.get(position).getShortDescription();
        holder.stepShortDescription.setText(shortDescription);

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public interface StepListAdapterOnClickListener{
        void onClick(int position);
    }
}
