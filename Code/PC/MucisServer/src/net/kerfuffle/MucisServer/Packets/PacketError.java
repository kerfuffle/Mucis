package net.kerfuffle.MucisServer.Packets;

import net.kerfuffle.MucisServer.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketError extends Packet{

	public PacketError(int type, String message)
	{
		super (Global.ERROR, Global.SP, String.valueOf(type), message);
	}
	
}
