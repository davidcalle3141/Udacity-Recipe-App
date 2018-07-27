package calle.david.udacityrecipeapp.UI.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.R;

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


}
