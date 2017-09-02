package net.kerfuffle.MucisClient.Packets;

import net.kerfuffle.MucisClient.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketSyncServerReceiveSong extends Packet{

	private String fileName;
	
	public PacketSyncServerReceiveSong(CharSequence data)
	{
		super(data, Global.SYNC_SERVER_RECEIVE_SONG);
		
		String sp[] = data.toString().split(Global.SP);
		fileName = sp[1];
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
}
