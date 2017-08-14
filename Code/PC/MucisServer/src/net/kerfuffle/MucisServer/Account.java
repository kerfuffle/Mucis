package net.kerfuffle.MucisServer;

import net.kerfuffle.Utilities.Util;

public class Account {

	private String username;
	private String data;
	
	private Library library;
	
	public Account(String username, String homePath)
	{
		this.username=username;

		data = Util.readTextFile(username);
		String sp[] = data.split("?");
		
		library = new Library(sp[0]);
		loadPlaylists();
	}
	
	private void loadPlaylists()
	{
		String sp00[] = data.split("?");
		String sp0[] = sp00[1].split("%");
		
		for (int i = 0 ; i < sp0.length; i++)
		{
			String sp[] = sp0[i].split(",");
			Playlist pl = new Playlist(sp[0]);
			for (int j = 1; j < sp.length; j++)
			{
				Song s = new Song(sp[j]);
				pl.addSong(s);
			}
			library.addPlaylist(pl);
		}
	}
	
	
	
	public String getUsername()
	{
		return username;
	}
}
