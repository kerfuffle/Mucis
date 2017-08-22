package net.kerfuffle.Utilities;

import java.util.ArrayList;

public class Flags {

	private ArrayList<String> flags = new ArrayList<String>();
	
	public void addFlag(String flag)
	{
		flags.add(flag);
	}
	
	public boolean flagTriggered(String flag)
	{
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).equals(flag))
			{
				return true;
			}
		}
		return false;
	}
	
	public void removeFlag(String flag)
	{
		for (int i = 0; i < flags.size(); i++)
		{
			if (flags.get(i).equals(flag))
			{
				flags.remove(i);
			}
		}
	}
	
	public String getLastFlag()
	{
		return flags.get(flags.size()-1);
	}
}
