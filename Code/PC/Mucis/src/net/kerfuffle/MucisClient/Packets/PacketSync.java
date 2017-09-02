package net.kerfuffle.MucisClient.Packets;

import net.kerfuffle.MucisClient.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketSync extends Packet{
	
	private int port;
	
	public PacketSync()
	{
		super (Global.SYNC, Global.SP);
	}
	
	public PacketSync(CharSequence data)
	{
		super (data, Global.SYNC);
		
		String sp[] = data.toString().split(Global.SP);
		port = Integer.parseInt(sp[1]);
	}
	
	public int getFilePort()
	{
		return port;
	}

}
