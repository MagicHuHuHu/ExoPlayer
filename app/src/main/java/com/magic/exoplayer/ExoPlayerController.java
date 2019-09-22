package com.magic.exoplayer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Magic
 * @date on 2019/9/22
 * @description this class made for exo player to control the player
 */
public class ExoPlayerController {

    private static final String TAG = "ExoPlayerController";

    private volatile ExoPlayer player;
    private DataSource.Factory dataSourceFactory;


    private static class InstanceHodler {
        private static ExoPlayerController exoPlayerController = new ExoPlayerController();
    }

    public static ExoPlayerController getInstance() {
        return InstanceHodler.exoPlayerController;
    }


    public void initSimpleExoPlayer(PlayerView playerView, Context context) {
        if (player != null) {
            Log.w(TAG, "[initSimpleExoPlayer]:player has been already init ");
            return;
        }
        if (playerView == null) {
            throw new RuntimeException("Pls init a Play View first");
        }
        player = ExoPlayerFactory.newSimpleInstance(context);
        playerView.setPlayer(player);

        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.getApplicationContext().getPackageName()));
    }


    public void prepareMediaPlayWhenReady(Uri uri, boolean playWhenReady) {
        if (player == null) {
            throw new RuntimeException("Please init player first");
        }
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        // Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(playWhenReady);
    }

    public void setPlayParameter(float speed, float pitch, boolean isSkipSilence) {
        if (player == null) {
            throw new RuntimeException("Please init player first");
        }
        PlaybackParameters playbackParameters = new PlaybackParameters(speed, pitch, isSkipSilence);
        player.setPlaybackParameters(playbackParameters);
    }

    public void setPlayerSpeed(float speed) {
        if (player == null) {
            throw new RuntimeException("Please init player first");
        }
        speed = speed < 0 ? 1F : speed;
        PlaybackParameters playbackParameters = new PlaybackParameters(speed);
        player.setPlaybackParameters(playbackParameters);
    }

    public void setPlayerPitch(float speed, float pitch) {
        if (player == null) {
            throw new RuntimeException("Please init player first");
        }
        speed = speed < 0 ? 1F : speed;
        pitch = pitch < 0 ? 1F : pitch;
        PlaybackParameters playbackParameters = new PlaybackParameters(speed, pitch);
        player.setPlaybackParameters(playbackParameters);
    }

    public void pausePlayCurrent() {
        if (player == null) {
            throw new RuntimeException("Please init player first");
        }
        player.setPlayWhenReady(false);
    }

    public void resumePlayCurrent() {
        if (player == null) {
            throw new RuntimeException("Please init player first");
        }
        player.setPlayWhenReady(true);
    }


    public ConcatenatingMediaSource loadMediaSource(File[] files) {

        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();

        ProgressiveMediaSource.Factory factory = new ProgressiveMediaSource.Factory(dataSourceFactory);
        for (int i = 0; i < files.length; i++) {
            if (!files[i].getName().endsWith(".wav")) {
                continue;
            }
            MediaSource mediaSource = factory.createMediaSource(Uri.parse(files[i].getAbsolutePath()));
            concatenatingMediaSource.addMediaSource(mediaSource);
        }
        return concatenatingMediaSource;
    }

    public void prepareMediaScource(ConcatenatingMediaSource concatenatingMediaSource) {
        if (player == null) {
            throw new RuntimeException("Please init player first");
        }
        player.prepare(concatenatingMediaSource);
    }

    public void startPlayList() {
        if (player == null) {
            throw new RuntimeException("Please init player first");
        }
        player.setPlayWhenReady(true);
    }

    public void pausePlayList() {
        if (player == null) {
            throw new RuntimeException("Please init player first");
        }
        player.setPlayWhenReady(false);
    }

    public void currentPlay() {
        ;
    }

    public void release() {
        if (player != null) {
            player.release();
        }
    }
}


