package calle.david.udacityrecipeapp.UI.Fragments;

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

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.Data.Database.Steps;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModel;
import okhttp3.internal.Util;


public class RecipeStepsFragment extends Fragment {
    private View mView;
    private RecipeAppViewModel mViewModel;
    private Context mContext;
    private int mRecipeID;
    private ExoPlayer mExoPlayer;
    private String mImage;
    private String mVideo;
    private ArrayList<Steps> stepsArrayList;
    private int stepNum;

    @BindView(R.id.step_video_player)PlayerView mPlayerView;
    @BindView(R.id.video_frame_layout)FrameLayout mFrameLayout;
    @BindView(R.id.recipe_video_step_card)CardView cardView;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
           sendToFullscreenVideo();
        }
    }

    private void sendToFullscreenVideo() {
        long position = 0;

        if(mExoPlayer!=null) {
            position = mExoPlayer.getCurrentPosition();
            cleanUpPlayer();
        }

        Bundle bundle = new Bundle();
        bundle.putString("video", mVideo);
        bundle.putLong("position", position);
        Navigation.findNavController(mView).navigate(R.id.action_recipeStepsFragment_to_video_player,bundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if(savedInstanceState==null){
      this.mView = inflater.inflate(R.layout.fragment_recipe_steps_fragment, container,false);
      this.mContext = getContext();
        if (getArguments() != null) {

            this.stepsArrayList = getArguments().getParcelableArrayList("stepList");
            this.stepNum = getArguments().getInt("position");
            this.mRecipeID = stepsArrayList.get(stepNum).getRecipeID();
            this.mVideo = stepsArrayList.get(stepNum).getVideoURL();


        }

        ButterKnife.bind(this,mView);
    }
        return mView;

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                ) sendToFullscreenVideo();
        else populateUI();


    }


    private void populateUI() {

        if(!(mVideo != null && mVideo.equals("")))initializePlayer();



    }

    private void initializePlayer(){
            //it takes time for wrap content(height) on player view to calculate the height
            //so before it could calculate the height it would take up the whole screen then wrap the actuall height of video
            //this waits for mview to render and calculates height based on the view width and the video player matches parent
            cardView.post(() -> {
               float marginOffset = getResources().getDimension(R.dimen.card_view_margin);
               float width= mView.getWidth()- marginOffset*2;
               float aspectRatio = (float)9/(float)16;
               int height = (int) (width*aspectRatio);
                mFrameLayout.setLayoutParams(new LinearLayout.LayoutParams((int) width,height));

                mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                        new DefaultRenderersFactory(mContext),
                        new DefaultTrackSelector(),
                        new DefaultLoadControl());
                mPlayerView.setPlayer(mExoPlayer);
                Uri uri = Uri.parse(mVideo);
                MediaSource mediaSource = buildMediaSource(uri);
                mExoPlayer.prepare(mediaSource, true, false);
                mExoPlayer.setPlayWhenReady(true);
                mPlayerView.setVisibility(View.VISIBLE);

            });


    }



    private void cleanUpPlayer(){
        mPlayerView.setPlayer(null);
        mExoPlayer.release();
        mExoPlayer = null;
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
}
