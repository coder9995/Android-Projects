package com.example.sumitra.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import java.util.ArrayList;
import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
/**
 * Created by sumitra on 15/10/16.
 */
public abstract class MusicService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener{
    //media player
    private MediaPlayer player;
    //song list
    private ArrayList<Song> songs;
    //current position
    private int songPosn;
    private final IBinder musicBind=new MusicBinder();
    public void onCreate()
    {
        //create the service
        super.onCreate();
        //initialise the position
        songPosn=0;
        //create player
        player=new MediaPlayer();

        initMusicPlayer();
    }
    public void initMusicPlayer()
    {
        //set player properties
        player.setWakeMode(getApplicationContext(),PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }
    public void setList(ArrayList<Song> theSongs)
    {
        songs=theSongs;
    }

    public class MusicBinder extends Binder{
        MusicService getSrvice()
        {
            return MusicService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent){
        return musicBind;
    }
    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }
    public void playSong(){
        //play a song
        player.reset();
        //get song
        Song playSong=songs.get(songPosn);
        //get id
        long currSong=playSong.getId();
        //set uri
        Uri trackUri=ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,currSong);
        try{
            player.setDataSource(getApplicationContext(),trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE","Error setting data source",e);
        }
        player.prepareAsync();
    }
    @Override
    public void onPrepared(MediaPlayer mp)
    {
        //start playback
        mp.start();
    }
    public void setSong(int songIndex)
    {
        songPosn=songIndex;
    }
}