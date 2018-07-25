package calle.david.udacityrecipeapp.UI.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import calle.david.udacityrecipeapp.Data.Database.Steps;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.Utilities.InjectorUtils;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModel;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModelFactory;
import okhttp3.internal.Util;


public class RecipeStepsFragment extends Fragment {
    private View mView;
    private RecipeAppViewModel mViewModel;
    private Context mContext;
    private ExoPlayer mExoPlayer;

    @BindView(R.id.step_number)TextView mStepNumTextView;
    @BindView(R.id.step_card_long_description)TextView mStepLongDescription;
    @BindView(R.id.step_video_player)PlayerView mPlayerView;
    @BindView(R.id.video_frame_layout)FrameLayout mFrameLayout;
    @BindView(R.id.recipe_video_step_card)CardView cardView;



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
            if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && mViewModel.isHasVideo() ){
                sendToFullscreenVideo();
            }

    }

    private void sendToFullscreenVideo() {

        if(mExoPlayer!=null) {
            mViewModel.setVideoPosition(mExoPlayer.getCurrentPosition());
            cleanUpPlayer();
        }
        Navigation.findNavController(mView).navigate(R.id.action_recipeStepsFragment_to_video_player);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      this.mView = inflater.inflate(R.layout.fragment_recipe_steps_fragment, container,false);
      this.mContext = getContext();
        ButterKnife.bind(this,mView);
        return mView;

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecipeAppViewModelFactory factory = InjectorUtils.provideRecipeCardViewFactory(Objects.requireNonNull(getActivity()));

        mViewModel = ViewModelProviders.of(getActivity(),factory).get(RecipeAppViewModel.class);
        mViewModel.getFocusedStep().removeObservers(this);
        mViewModel.getFocusedStep().observe(this, focusedStep->{
            cleanUpPlayer();
            mFrameLayout.setVisibility(View.GONE);
            if (focusedStep != null) populateUI(focusedStep);

        });



    }
    @OnClick(R.id.next_button)
    public void onNextClick(){
        mViewModel.getStepsforRecipe().observe(this, stepsList -> {
            int newPosition;
            mViewModel.setVideoPosition(0);
            if (stepsList != null) {
                newPosition = (mViewModel.getStepNum()+1) % stepsList.size();

            mViewModel.setStepNum(newPosition);
            mViewModel.getFocusedStep().postValue(stepsList.get(newPosition));
            if(stepsList.get(newPosition).getVideoURL().equals(""))mViewModel.setHasVideo(false);
            else mViewModel.setHasVideo(true);
            if(mViewModel.isHasVideo() && isLandscape()) sendToFullscreenVideo();
            }

        });
    }
    @OnClick(R.id.previous_button)
    public void onPreviousClick(){
        mViewModel.getStepsforRecipe().observe(this, stepsList -> {
            int newPosition;
            mViewModel.setVideoPosition(0);
            if (stepsList != null) {
                int a= mViewModel.getStepNum()-1;
                int b = stepsList.size();
                newPosition = a < 0 ? b + a : a % b;

                mViewModel.setStepNum(newPosition);
                mViewModel.getFocusedStep().postValue(stepsList.get(newPosition));}
        });
    }

    private void populateUI(Steps focusedStep) {

        String videoURL = focusedStep.getVideoURL();
        if(videoURL.equals(""))mViewModel.setHasVideo(false);
        else mViewModel.setHasVideo(true);
        if(mViewModel.isHasVideo())initializePlayer(videoURL);
        else cleanUpPlayer();
        mStepNumTextView.setText("Step: "+Integer.valueOf(focusedStep.getId()));
        mStepLongDescription.setText(focusedStep.getDescription());
    }

    @Override
    public void onResume() {
        super.onResume();

       if(mExoPlayer!=null) mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer!= null)mExoPlayer.setPlayWhenReady(false);
    }

    private void initializePlayer(String video){
            //it takes time for wrap content(height) on player view to calculate the height
            //so before it could calculate the height it would take up the whole screen then wrap the actuall height of video
            //this waits for mview to render and calculates height based on the view width and the video player matches parent
            cardView.post(() -> {
               float marginOffset = getResources().getDimension(R.dimen.card_view_margin);
               float width= mView.getWidth()- marginOffset*2;
               float aspectRatio = (float)9/(float)16;
               int height = (int) (width*aspectRatio);
                mFrameLayout.setLayoutParams(new LinearLayout.LayoutParams((int) width,height));
                if(mExoPlayer!= null)cleanUpPlayer();

                mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                        new DefaultRenderersFactory(mContext),
                        new DefaultTrackSelector(),
                        new DefaultLoadControl());
                mPlayerView.setPlayer(mExoPlayer);
                Uri uri = Uri.parse(video);
                MediaSource mediaSource = buildMediaSource(uri);
                mExoPlayer.prepare(mediaSource, true, false);
                if(mViewModel.getVideoPosition()>0)mExoPlayer.seekTo(mViewModel.getVideoPosition());
                mExoPlayer.setPlayWhenReady(true);
                mFrameLayout.setVisibility(View.VISIBLE);

            });


    }



    private void cleanUpPlayer(){
        if(mExoPlayer!=null){
        mPlayerView.setPlayer(null);
        mExoPlayer.release();
        mExoPlayer = null;}
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mExoPlayer!=null)cleanUpPlayer();

    }

    private MediaSource buildMediaSource(Uri uri){
        return  new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("RecipeAPP")).
                createMediaSource(uri);
    }

    private Boolean isLandscape(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            return true;
        }
        return false;
    }
}
