package net.kerfuffle.MucisServer;

public class Account {

	private String username;
	private String password;
	
	private Library library;
	
	
	public void initAccount()
	{
		library = new Library();
		Playlist pl = new Playlist("All");
		library.addPlaylist(pl);
	}
	
	
	private String read
	
}