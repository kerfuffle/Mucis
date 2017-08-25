package net.kerfuffle.MucisClient.Packets;

import net.kerfuffle.MucisClient.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketRemoveSong extends Packet{
	
	public PacketRemoveSong(String fileName)
	{
		super(Global.REMOVE_SONG, Global.SP, fileName);
	}
	
}
