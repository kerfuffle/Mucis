package net.kerfuffle.MucisServer;

import java.util.ArrayList;

public class Library {

	private String homePath;
	private ArrayList<Playlist> playlists = new ArrayList<Playlist>();
	
	public Library(String homePath)
	{
		this.homePath = homePath;
	}
	
	public void addPlaylist(Playlist pl)
	{
		playlists.add(pl);
	}
	
	public Playlist getPlaylist(String name)
	{
		for (Playlist p : playlists)
		{
			if (p.getName().equals(name))
			{
				return p;
			}
		}
		return null;
	}
}
