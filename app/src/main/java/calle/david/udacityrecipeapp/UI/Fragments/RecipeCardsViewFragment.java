package calle.david.udacityrecipeapp.UI.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.Data.Database.Steps;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.UI.Adapters.RecipeCardAdapter;
import calle.david.udacityrecipeapp.Utilities.InjectorUtils;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModelFactory;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModel;

public class RecipeCardsViewFragment extends Fragment implements RecipeCardAdapter.RecipeCardAdapterOnClickListener {
    private RecipeAppViewModel mViewModel;
    private Context mContext;


    RecipeCardAdapter recipeCardAdapter;

    @BindView(R.id.recipeCardsRV)RecyclerView recyclerView;
    private View view;

    public RecipeCardsViewFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_recipe_cards_fragment,container,false);
        mContext = this.getContext();
        ButterKnife.bind(this,view);
        GridLayoutManager mGrid = new GridLayoutManager(mContext,1);
        recyclerView.setLayoutManager(mGrid);
        recipeCardAdapter = new RecipeCardAdapter(mContext, this);
        recyclerView.setAdapter(recipeCardAdapter);





        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecipeAppViewModelFactory factory = InjectorUtils.provideRecipeCardViewFactory(getActivity());
        mViewModel = ViewModelProviders.of(getActivity(),factory).get(RecipeAppViewModel.class);
        populateUI();

    }


    private void populateUI() {
        mViewModel.getRecipeList().removeObservers(this);

        mViewModel.getRecipeList().observe(this,
                recipes->{
            if(recipes != null){
                if(recipes.size()!=0){
                recipeCardAdapter.addRecipeList(recipes);
                recipeCardAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);}

            }
                });
    }


    @Override
    public void onItemClick(int position) {

        mViewModel.getRecipeList().observe(this, recipes -> {
            if(recipes!=null)
            mViewModel.setSelectedRecipe(recipes.get(position));
            Navigation.findNavController(view).navigate(R.id.action_recipeCardsViewFragment_to_recipeIngredientsFragment);

        });

    }
}
