package calle.david.udacityrecipeapp.UI.Fragments;

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

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.R;


public class RecipeFullScreenVideoFragment extends Fragment {
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private int mResumeWindow;
    private long mResumePosition=0;
    private View mView;
    private Context mContext;
    private String mVideo;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Navigation.findNavController(mView).popBackStack();
    }

    private ExoPlayer mExoPlayer;

    @BindView(R.id.fullscreen_video_player)PlayerView mPlayerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mView= inflater.inflate(R.layout.fragment_video_player,container,false);
        this.mContext = getContext();
        ButterKnife.bind(this,mView);
        if (getArguments() != null) {
            mVideo = getArguments().getString("video");
            mResumePosition = getArguments().getLong("position");

        }
        hideSystemUI();
        populateUI();
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


    private void populateUI() {
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(mContext),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
        mPlayerView.setPlayer(mExoPlayer);
        Uri uri = Uri.parse(mVideo);
        MediaSource mediaSource = buildMediaSource(uri);
        mExoPlayer.prepare(mediaSource, true, false);
        if(mResumePosition != 0)mExoPlayer.seekTo(mResumePosition);
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
    private void cleanUpPlayer(){
        mPlayerView.setPlayer(null);
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
