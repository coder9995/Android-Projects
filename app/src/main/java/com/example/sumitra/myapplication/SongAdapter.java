package com.example.sumitra.myapplication;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sumitra on 15/10/16.
 */

public class SongAdapter extends BaseAdapter {

    private ArrayList<Song> songs;
    private LayoutInflater songInf;
    SongAdapter(Context c,ArrayList<Song> theSongs)
    {
        songs=theSongs;
        songInf=LayoutInflater.from(c);
    }
    @Override
    public int getCount()
    {
        return songs.size();
    }
    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public Object getItem(int arg0)
    {
        return null;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //map to song layout
        LinearLayout songLay= (LinearLayout)songInf.inflate(R.layout.song,parent,false);
        //get title and artist views

        TextView songView=(TextView)songLay.findViewById(R.id.song_title);
        songView.setTextColor(Color.RED);
        TextView artistView=(TextView)songLay.findViewById(R.id.song_artist);
        artistView.setTextColor(Color.BLUE);
        //get song using position
        Song currSong=songs.get(position);
        //get title and artist strings
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getArtist());
        //set position as tag
        songLay.setTag(position);
        return songLay;
    }
}
