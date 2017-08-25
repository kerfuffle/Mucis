package net.kerfuffle.MucisServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import net.kerfuffle.Utilities.Util;

public class Library {

	private String homePath;
	private ArrayList<Playlist> playlists = new ArrayList<Playlist>();
	
	public Library(String homePath)
	{
		this.homePath = homePath;
	}
	
	public void removeSong(String name) throws IOException
	{
		for (Song s : playlists.get(0).getSongs())
		{
			if (s.getName().equals(name))
			{
				playlists.get(0).removeSong(s);
				
				StringBuilder sb = new StringBuilder();
				String data = Util.readTextFile(Global.homePath + "MusicPaths.dab");
				String sp0[] = data.split(">");
				
				for (int i = 0; i < sp0.length; i++)
				{
					String sp[] = sp0[i].split("\\?");
					if (!sp[0].equals(name))
					{
						sb.append(sp0[i] + ">");
					}
				}
				
				Util.writeToFile(Global.homePath + "MusicPaths.dab", sb.toString());
				
				Files.delete(Paths.get(homePath + name));
				
				return;
			}
		}
	}
	public void addSong(Song s)
	{
		if (hasSong(s.getName()))
		{
			//TODO
			// send packet to ask if you want to replace song
			// replace file
		}
		else
		{
			playlists.get(0).addSong(s);
			Util.addToFile(Global.homePath + "MusicPaths.dab", s.getName()+"?"+s.getPath()+">");
		}
	}
	public void addPlaylist(Playlist pl)
	{
		playlists.add(pl);
	}
	public String getHomePath() 
	{
		return homePath;
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
	
	
	
	private boolean hasSong(String name)
	{
		String mpaths = Util.readTextFile(Global.homePath + "MusicPaths.dab");
		String sp0[] = mpaths.split(">");
		for (int i = 0; i < sp0.length; i++)
		{
			String sp[] = sp0[i].split("\\?");
			if (sp[0].equals(name))
			{
				return true;
			}
		}
		return false;
	}
}
