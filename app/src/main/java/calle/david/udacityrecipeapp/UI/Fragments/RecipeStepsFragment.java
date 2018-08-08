package calle.david.udacityrecipeapp.UI.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import calle.david.udacityrecipeapp.Data.Database.Steps;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.Utilities.EspressoIdlingResource;
import calle.david.udacityrecipeapp.Utilities.InjectorUtils;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModel;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModelFactory;


public class RecipeStepsFragment extends Fragment {
    private View mView;
    private RecipeAppViewModel mViewModel;
    private Context mContext;
    private ExoPlayer mExoPlayer;
    private Resources resources;
    private int numofExoPlayers = 0;




    @BindView(R.id.step_number)TextView mStepNumTextView;
    @BindView(R.id.step_card_long_description)TextView mStepLongDescription;
    @BindView(R.id.step_video_player)PlayerView mPlayerView;
    @BindView(R.id.video_frame_layout)FrameLayout mFrameLayout;
    @BindView(R.id.recipe_video_step_card)CardView mCardView;
    @BindView(R.id.steps_buttons_layout)FrameLayout mButtonsContainer;
    @BindView(R.id.next_button)Button nextButton;
    @BindView(R.id.previous_button)Button prevButton;
    @BindView(R.id.thumbnail_image)ImageView thumbnailImageContainer;
    private Steps focuedStep;


    public RecipeStepsFragment(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(!isTwoPane())this.mView = inflater.inflate(R.layout.fragment_recipe_steps_fragment, container,false);
        if(isTwoPane())this.mView = inflater.inflate(R.layout.fragment_recipe_steps_fragment_tablet,container,false);
        this.mContext = getContext();
        focuedStep=null;
        ButterKnife.bind(this,mView);
        resources = getResources();
        return mView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        RecipeAppViewModelFactory factory = InjectorUtils.provideRecipeCardViewFactory(Objects.requireNonNull(getActivity()));
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(RecipeAppViewModel.class);
        mViewModel.getFocusedStep().removeObservers(this);
                mViewModel.getFocusedStep().observe(this, focusedStep -> {
                    if (focusedStep != null)
                        if(isTwoPane())cleanUpPlayer();
                        this.focuedStep = focusedStep;
                        populateUI(this.focuedStep);
                    EspressoIdlingResource.Unlock();

                });




    }

    @Override
    public void onResume() {
        super.onResume();
        if(mExoPlayer==null &&focuedStep!=null&& !focuedStep.getVideoURL().equals("")&& numofExoPlayers==0){
            initializePlayer(buildMediaSource(Uri.parse(this.focuedStep.getVideoURL())));
        }

}

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer!=null){
            mViewModel.setVideoPosition(mExoPlayer.getCurrentPosition());
            mViewModel.setPlayerState(mExoPlayer.getPlayWhenReady());
            if(Build.VERSION.SDK_INT<=23)cleanUpPlayer();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if(Build.VERSION.SDK_INT>23)cleanUpPlayer();
    }



    @OnClick(R.id.next_button)
    public void onNextClick(){

        cleanUpPlayer();

        mViewModel.getStepsforRecipe().observe(this, stepsList -> {

            int newPosition;
            mViewModel.setVideoPosition(0);
            if (stepsList != null) {
                newPosition = (mViewModel.getStepNum()+1) % stepsList.size();

            mViewModel.setStepNum(newPosition);
            mViewModel.getFocusedStep().postValue(stepsList.get(newPosition));
            mViewModel.setHasVideo(!stepsList.get(newPosition).getVideoURL().equals(""));

            }


        });

    }
    @OnClick(R.id.previous_button)
    public void onPreviousClick(){
        cleanUpPlayer();

        mViewModel.getStepsforRecipe().observe(this, stepsList -> {

            int newPosition;
            mViewModel.setVideoPosition(0);
            if (stepsList != null) {
                //wrap around going backwards
                int a= mViewModel.getStepNum()-1;
                int b = stepsList.size();
                newPosition = a < 0 ? b + a : a % b;

                mViewModel.setStepNum(newPosition);
                mViewModel.getFocusedStep().postValue(stepsList.get(newPosition));
                mViewModel.setHasVideo(!stepsList.get(newPosition).getVideoURL().equals(""));

            }



        });

    }

    private void populateUI(Steps focusedStep) {

        String videoURL = focusedStep.getVideoURL();
        String thumbnail = focusedStep.getThumbnailURL();


        if (!videoURL.equals("")&&isLandscape()&&!isTwoPane()){
            initializePlayer(buildMediaSource(Uri.parse(videoURL)));
            fullScreen();
        }
        else if(!videoURL.equals("")){
            mFrameLayout.setVisibility(View.VISIBLE);
            thumbnailImageContainer.setVisibility(View.GONE);
            renderMediaHolderSize();
            initializePlayer(buildMediaSource(Uri.parse(videoURL)));
            mStepNumTextView.setText("Step: "+Integer.valueOf(focusedStep.getId()));
            mStepLongDescription.setText(focusedStep.getDescription());
        }
        else if (!thumbnail.equals("")){
            mFrameLayout.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
            Picasso.get().load(thumbnail).into(thumbnailImageContainer);
            thumbnailImageContainer.setVisibility(View.VISIBLE);
            mStepNumTextView.setText("Step: "+Integer.valueOf(focusedStep.getId()));
            mStepLongDescription.setText(focusedStep.getDescription());
        }
        else {
            mFrameLayout.setVisibility(View.GONE);
            mStepNumTextView.setText("Step: "+Integer.valueOf(focusedStep.getId()));
            mStepLongDescription.setText(focusedStep.getDescription());

        }



    }


    private void renderMediaHolderSize(){
        //it takes time for wrap content(height) on player view to calculate the height
        //so before it could calculate the height it would take up the whole screen then wrap the actuall height of video
        //this waits for mview to render and calculates height based on the view width and the video player matches parent
        mCardView.post(() -> {

            float marginOffset = resources.getDimension(R.dimen.card_view_margin);
            float width = mView.getWidth() - marginOffset * 2;
            float aspectRatio = (float) 9 / (float) 16;
            int height = (int) (width * aspectRatio);
            mFrameLayout.setLayoutParams(new RelativeLayout.LayoutParams((int) width, height));

        });
    }
    private  void initializePlayer(MediaSource mediaSource){
        Log.d("recipe_steps", String.valueOf(++numofExoPlayers));
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(mContext),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
        mPlayerView.setPlayer(mExoPlayer);
        mExoPlayer.prepare(mediaSource, false, false);
        if (mViewModel.getVideoPosition() > 0)
            mExoPlayer.seekTo(mViewModel.getVideoPosition());
        mExoPlayer.setPlayWhenReady(mViewModel.isPlayerState());
        mFrameLayout.setVisibility(View.VISIBLE);

    }


    private void cleanUpPlayer(){
        if(mExoPlayer!=null){
            numofExoPlayers--;
        mPlayerView.setPlayer(null);
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
        mViewModel.setExoPlayerExists(false);
        Log.d("recipe_steps","clean up player");}
    }

    private MediaSource buildMediaSource(Uri uri){
        return  new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("RecipeAPP")).
                createMediaSource(uri);
    }

    private Boolean isLandscape(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
    private void hideUI(){
        View decorView = Objects.requireNonNull(getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        getActivity().findViewById(R.id.my_toolbar).setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showUI();
        focuedStep=null;
    }

    private void fullScreen(){
        hideUI();
        mButtonsContainer.setVisibility(View.GONE);
        mStepLongDescription.setVisibility(View.GONE);
        mStepNumTextView.setVisibility(View.GONE);
        mCardView.setCardBackgroundColor(Color.BLACK);
        mCardView.getLayoutParams();
        android.support.v7.widget.CardView.LayoutParams params= (CardView.LayoutParams) mCardView.getLayoutParams();
        params.topMargin=0; params.bottomMargin = 0; params.leftMargin =0; params.rightMargin = 0;
        mCardView.setLayoutParams(params);
    }
    private boolean isTwoPane(){
        if(Objects.requireNonNull(getActivity()).findViewById(R.id.twoPane)!=null){
            return true;
        }else return false;
    }

    private void showUI(){
        if(isTwoPane())return;
            View decorView = Objects.requireNonNull(getActivity()).getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_VISIBLE);
            getActivity().findViewById(R.id.my_toolbar).setVisibility(View.VISIBLE);
        }
    }