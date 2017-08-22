package net.kerfuffle.MucisClient.Packets;

import net.kerfuffle.MucisClient.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketAddSong extends Packet{

	private int port;
	
	public PacketAddSong (String fileName, int fileSize)
	{
		super(Global.ADD_SONG, Global.SP, fileName, String.valueOf(fileSize));
	}
	
	public PacketAddSong(CharSequence data)
	{
		super (data, Global.ADD_SONG);
		String sp[] = data.toString().split(Global.SP);
		port = Integer.parseInt(sp[1]);
	}
	
	public int getFilePort()
	{
		return port;
	}
	
}
