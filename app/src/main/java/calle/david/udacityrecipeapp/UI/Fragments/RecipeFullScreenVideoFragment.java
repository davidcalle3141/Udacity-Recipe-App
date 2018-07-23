package calle.david.udacityrecipeapp.UI.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;
import java.util.Objects;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.Data.Database.Steps;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.Utilities.InjectorUtils;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModel;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModelFactory;


public class RecipeFullScreenVideoFragment extends Fragment {
    private View mView;
    private Context mContext;
    private RecipeAppViewModel mViewModel;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        cleanUpPlayer();
        Navigation.findNavController(mView).popBackStack();
    }

    private ExoPlayer mExoPlayer;

    @BindView(R.id.fullscreen_video_player)PlayerView mPlayerView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecipeAppViewModelFactory factory = InjectorUtils.provideRecipeCardViewFactory(Objects.requireNonNull(getActivity()));
        mViewModel = ViewModelProviders.of(getActivity(),factory).get(RecipeAppViewModel.class);

        mViewModel.getFocusedStep().removeObservers(this);

        mViewModel.getFocusedStep().observe(this, this::populateUI);
        mViewModel.getStepsforRecipe(3).observe(this,boo->{
            ArrayList<Steps> steps = new ArrayList(boo);
            mViewModel.getFocusedStep().postValue(boo.get(0));
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mView= inflater.inflate(R.layout.fragment_video_player,container,false);
        this.mContext = getContext();
        ButterKnife.bind(this,mView);
        hideSystemUI();
        return mView;

    }

    private void hideSystemUI() {
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    private void showSystemUI(){
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_VISIBLE);




    }


    private void populateUI(Steps focusedStep) {

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(mContext),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
        mPlayerView.setPlayer(mExoPlayer);
        Uri uri = Uri.parse(focusedStep.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        mExoPlayer.prepare(mediaSource, true, false);
        if(mViewModel.getVideoPosition() != 0)mExoPlayer.seekTo(mViewModel.getVideoPosition());
        mExoPlayer.setPlayWhenReady(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        super.onDestroy();
        if(mExoPlayer!=null)cleanUpPlayer();
        showSystemUI();
    }

    private MediaSource buildMediaSource(Uri uri) {
        return  new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("RecipeAPP")).
                createMediaSource(uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mExoPlayer!=null)mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mExoPlayer.setPlayWhenReady(false);
    }

    private void cleanUpPlayer(){
        mPlayerView.setPlayer(null);
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
