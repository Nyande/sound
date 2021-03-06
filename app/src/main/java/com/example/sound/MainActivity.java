package com.example.sound;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    VideoView videoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView=findViewById(R.id.videoView);




        audioManager=(AudioManager) getSystemService(AUDIO_SERVICE);

        int maxvolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        int currentvolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        mediaPlayer =MediaPlayer.create(this,R.raw.you);

        SeekBar volumecontrol =(SeekBar) findViewById(R.id.volumeSeekBar);

        volumecontrol.setMax(maxvolume);
        volumecontrol.setProgress(currentvolume);

        volumecontrol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                Log.i("SeekBar changed",Integer.toString(i));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    final SeekBar scrubSeekbar = (SeekBar) findViewById(R.id.scrubSeekBar);
    scrubSeekbar.setMax(mediaPlayer.getDuration());


scrubSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
        Log.i(" Scrub SeekBar Moved",Integer.toString(i));

        mediaPlayer.seekTo(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
});

new Timer().scheduleAtFixedRate(new TimerTask() {
    @Override
    public void run() {
        scrubSeekbar.setProgress(mediaPlayer.getCurrentPosition());
    }
},0,300);
    }


    public void start(View view){
        videoView.setVideoPath("android.resource://"+getPackageName()+ "/" + R.raw.grid);


        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

    public void play(View view){


        mediaPlayer.start();

    }

    public void pause(View view){


        mediaPlayer.pause();
    }
}
