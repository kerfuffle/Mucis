package net.kerfuffle.MucisServer;

import java.util.ArrayList;

import net.kerfuffle.Utilities.Util;

public class Playlist {
	
	private String name, username;

	private ArrayList<Song> songs = new ArrayList<Song>();
	
	public Playlist(String name, String username)
	{
		this.name = name;
		this.username = username;
	}
	
	public void addSong(Song s)
	{
		StringBuilder newData = new StringBuilder();
		
		String data = Util.readTextFile(Global.homePath + username + ".dab");
		String sp00[] = data.split("\\?");
		String sp0[] = sp00[1].split("%");
		newData.append(sp00[0]+"?");
		
		int lpls = -1;
		
		for (int i = 0; i < sp0.length; i++)
		{
			String sp[] = sp0[i].split(",");
			if (sp[0].equals(name))
			{
				lpls = i+1;
				for (int j = 0; j < i; j++)
				{
					newData.append(sp0[j]+"%");
				}
				newData.append(sp[0]+",");
				newData.append(s.getName()+",");
				for (int a = 2; a < sp.length; a++)
				{
					newData.append(sp[a]+",");
				}
				newData.append("%");
				break;
			}
		}
		
		for (int x = lpls; x < sp0.length; x++)
		{
			newData.append(sp0[x]+"%");
		}
		
		newData.append("?");
		
		Util.writeToFile(Global.homePath + username + ".dab", newData.toString());
		
		songs.add(s);
	}
	
	public Song getSong(String name)
	{
		for (Song s : songs)
		{
			if (s.getName().equals(name))
			{
				return s;
			}
		}
		return null;
	}
	
	public ArrayList<Song> getSongs()
	{
		return songs;
	}
	
	public String getName()
	{
		return name;
	}
}
