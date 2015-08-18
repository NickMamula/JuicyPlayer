package com.example.nicola.juicyplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {

    final String LOG_TAG = "myLogs";
    MediaPlayer mediaPlayer;
    AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        am = (AudioManager) getSystemService(AUDIO_SERVICE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClickStart(View view) {
        releaseMP();
        Log.d(LOG_TAG, "start SD");
        mediaPlayer = new MediaPlayer();
        mediaPlayer= MediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Music/ride_unicorn.mp3"));
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        if (!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }

    public void onClick (View view) {

        switch (view.getId()) {
           /* case R.id.ibtnPause:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;*/
            /*case R.id.btnResume:
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                break;*/
            case R.id.ibtnStop:
                mediaPlayer.stop();
                break;
            case R.id.ibtnPrevious:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 3000);
                break;
            case R.id.ibtnNext:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 3000);
                break;
            case R.id.ibtnModePlay:
                Log.d(LOG_TAG, "Playing " + mediaPlayer.isPlaying());
                Log.d(LOG_TAG, "Time " + mediaPlayer.getCurrentPosition() + " / "
                        + mediaPlayer.getDuration());
                Log.d(LOG_TAG, "Looping " + mediaPlayer.isLooping());
                Log.d(LOG_TAG,
                        "Volume " + am.getStreamVolume(AudioManager.STREAM_MUSIC));
                break;

        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(LOG_TAG, "onPrepared");
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(LOG_TAG, "onCompletion");
    }


    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }
}

