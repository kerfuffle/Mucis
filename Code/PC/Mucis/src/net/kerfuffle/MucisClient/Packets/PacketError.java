package net.kerfuffle.MucisClient.Packets;

import net.kerfuffle.MucisClient.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketError extends Packet{

	private int type;
	
	public PacketError(CharSequence data)
	{
		super(data, Global.ERROR);
		String sp[] = data.toString().split(Global.SP);
		type = Integer.parseInt(sp[1]);
	}
	
	public int getType()
	{
		return type;
	}
	
}
