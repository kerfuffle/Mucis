package net.kerfuffle.MucisServer;

public class Song {

	private String name;
	private String path;
	
	public Song(String name)
	{
		this.name = name;
	}
	
	public Song(String name, String path)
	{
		this.name = name;
		this.path = path;
	}
	
	public String getName()
	{
		return name;
	}
	public String getPath()
	{
		return path;
	}
	
}
