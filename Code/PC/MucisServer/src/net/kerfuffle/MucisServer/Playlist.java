package net.kerfuffle.MucisServer;

import java.util.ArrayList;

public class Playlist {
	
	private String name;

	private ArrayList<Song> songs = new ArrayList<Song>();
	
	public Playlist(String name)
	{
		this.name = name;
	}
	
	public void addSong(Song s)
	{
		songs.add(s);
	}
}