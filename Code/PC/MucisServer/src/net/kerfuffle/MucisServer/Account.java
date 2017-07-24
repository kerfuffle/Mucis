package net.kerfuffle.MucisServer;

import net.kerfuffle.Utilities.Util;

public class Account {

	private String username;
	private String password;
	
	private Library library;
	
	public Account(String username)
	{
		this.username=username;

		library = new Library();
		addPlaylists();
	}
	
	private void addPlaylists()
	{
		String data = Util.readTextFile(username);
		
		String sp0[] = data.split("%");
		
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
	
}
