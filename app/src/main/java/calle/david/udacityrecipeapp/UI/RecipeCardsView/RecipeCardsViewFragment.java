package calle.david.udacityrecipeapp.UI.RecipeCardsView;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.Data.Database.Recipe;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.UI.Adapters.RecipeCardAdapter;
import calle.david.udacityrecipeapp.UI.Adapters.RecipeCardViewHolder;
import calle.david.udacityrecipeapp.Utilities.InjectorUtils;

public class RecipeCardsViewFragment extends Fragment {
    private RecipeCardsViewVM mViewModel;
    private Context context;

    RecipeCardAdapter recipeCardAdapter;

    @BindView(R.id.recipeCardsRV)RecyclerView recyclerView;

    public RecipeCardsViewFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_cards_view,container,false);
        context= this.getContext();
        ButterKnife.bind(this,view);
        GridLayoutManager mGrid = new GridLayoutManager(context,1);
        recyclerView.setLayoutManager(mGrid);
        recipeCardAdapter = new RecipeCardAdapter(context);
        recyclerView.setAdapter(recipeCardAdapter);





        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecipeCardsViewModelFactory factory = InjectorUtils.provideRecipeCardViewFactory(context);
        mViewModel = ViewModelProviders.of(this,factory).get(RecipeCardsViewVM.class);
        populateUI();
    }


    private void populateUI() {
        mViewModel.getmRecipeList().removeObservers(this);

        mViewModel.getmRecipeList().observe(this,
                recipes->{
            if(recipes != null){
                recipeCardAdapter.addRecipeList(recipes);
                recipeCardAdapter.notifyDataSetChanged();

            }
                });

    }



}
