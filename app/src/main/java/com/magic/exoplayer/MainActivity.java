package com.magic.exoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;

import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SubtitleView;
import com.magic.exoplayer.adapter.MusicInfoAdapter;
import com.magic.exoplayer.bean.MusicInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Magic
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE = 12983;
    @BindView(R.id.exo_play_view)
    PlayerView playerView;
    @BindView(R.id.btn_start_play)
    Button startPlayBtn;
    @BindView(R.id.btn_pause_play)
    Button pausePlayBtn;
    @BindView(R.id.btn_resume_play)
    Button resumePlayBtn;
    @BindView(R.id.btn_stop_play)
    Button stopPlayBtn;
    @BindView(R.id.speed_seek_bar)
    SeekBar playSpeedSeekBar;
    @BindView(R.id.pitch_seek_bar)
    SeekBar playPitchSeekBar;

    @BindView(R.id.music_list_recycle_view)
    RecyclerView recyclerView;
    MusicInfoAdapter musicInfoAdapter;


    private float mPlaySpeed = 1F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

        startPlayBtn.setOnClickListener(this);
        pausePlayBtn.setOnClickListener(this);
        resumePlayBtn.setOnClickListener(this);
        stopPlayBtn.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            playSpeedSeekBar.setMin(1);
        }
        playSpeedSeekBar.setMax(100);
        playSpeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ExoPlayerController.getInstance().setPlayerSpeed(progress);
                mPlaySpeed = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            playPitchSeekBar.setMin(1);
        }
        playPitchSeekBar.setMax(100);
        playPitchSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ExoPlayerController.getInstance().setPlayerPitch(mPlaySpeed, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        List<MusicInfo> musicInfoList = new ArrayList<>();
        File[] files = new File("/sdcard/WangMusic").listFiles();
        for (int i = 0; i < files.length; i++) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setMusicName(files[i].getName());
            musicInfo.setUri(Uri.parse(files[i].getAbsolutePath()));
            musicInfoList.add(musicInfo);
        }

        musicInfoAdapter = new MusicInfoAdapter(musicInfoList, this);

        musicInfoAdapter.setItemClickListenser(new RecycleViewOnItemClickListenser() {
            @Override
            public void onItemClick(int position) {
                MusicInfo musicInfo = musicInfoAdapter.getMusicInfo(position);
                Log.i(TAG, "onItemClick: " + musicInfo);
                ExoPlayerController.getInstance().prepareMediaPlayWhenReady(musicInfo.getUri(), true);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(musicInfoAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ExoPlayerController.getInstance().initSimpleExoPlayer(playerView, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_play:
//                Uri uri = Uri.parse("/sdcard/test.wav");
//                ExoPlayerController.getInstance().prepareMediaPlayWhenReady(uri, true);
                File[] files = new File("/sdcard/WangMusic").listFiles();
                ConcatenatingMediaSource concatenatingMediaSource =
                        ExoPlayerController.getInstance().loadMediaSource(files);
                ExoPlayerController.getInstance().prepareMediaScource(concatenatingMediaSource);
                ExoPlayerController.getInstance().resumePlayCurrent();
                break;
            case R.id.btn_pause_play:
                ExoPlayerController.getInstance().pausePlayCurrent();
                break;
            case R.id.btn_resume_play:
                ExoPlayerController.getInstance().resumePlayCurrent();
                break;
            case R.id.btn_stop_play:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExoPlayerController.getInstance().release();
    }
}
