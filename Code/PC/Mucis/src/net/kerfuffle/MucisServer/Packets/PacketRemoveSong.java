package net.kerfuffle.MucisServer.Packets;

import net.kerfuffle.MucisClient.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketRemoveSong extends Packet{

	private String fileName;
	
	public PacketRemoveSong(CharSequence data)
	{
		super (data, Global.REMOVE_SONG);
		
		String sp[] = data.toString().split(Global.SP);
		fileName = sp[1];
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
}
