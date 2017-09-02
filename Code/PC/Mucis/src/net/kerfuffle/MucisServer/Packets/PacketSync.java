package net.kerfuffle.MucisServer.Packets;

import net.kerfuffle.MucisServer.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketSync extends Packet{
	
	public PacketSync(CharSequence data)
	{
		super(data, Global.SYNC);
	}
	
	public PacketSync(int port)
	{
		super (Global.SYNC, Global.SP, String.valueOf(port));
	}
}
