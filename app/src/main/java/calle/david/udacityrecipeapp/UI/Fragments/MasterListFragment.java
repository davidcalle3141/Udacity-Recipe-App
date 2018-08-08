package calle.david.udacityrecipeapp.UI.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.Utilities.FragmentNavUtils;

public class MasterListFragment extends Fragment {


    private Context mContext;


    public MasterListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_master_list, container, false);
        this.mContext = getContext();
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState==null){

            FragmentNavUtils.replaceFragment(Objects.requireNonNull(
                    getActivity()).getSupportFragmentManager() ,R.id.master_fragment_list, new RecipeIngredientsFragment(),"MASTER_FRAGMENT_LIST");
            FragmentNavUtils.replaceFragment(
                    getActivity().getSupportFragmentManager() ,R.id.master_fragment_detail,new RecipeStepsFragment(),"MASTER_FRAGMENT_DETAIL");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("masterListFragment","onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("masterListFragment","onDestroy");
        Fragment masterDetail = getActivity().getSupportFragmentManager().findFragmentByTag("MASTER_FRAGMENT_DETAIL");
        Fragment masterList = getActivity().getSupportFragmentManager().findFragmentByTag("MASTER_FRAGMENT_LIST");
        getActivity().getSupportFragmentManager().beginTransaction().remove(masterDetail).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().remove(masterList).commitAllowingStateLoss();


    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        Log.d("masterListFragment","onDestroyOM");

    }
}
