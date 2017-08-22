package net.kerfuffle.MucisClient.Packets;

import net.kerfuffle.MucisClient.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketLogin extends Packet{

	public PacketLogin(String username)
	{
		super(Global.LOGIN, Global.SP, username);
	}
	
}
