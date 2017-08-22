package net.kerfuffle.MucisServer.Packets;

import net.kerfuffle.MucisServer.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketLogin extends Packet{

	private String username;
	
	public PacketLogin(CharSequence data)
	{
		super(data, Global.LOGIN);
		String sp[] = data.toString().split(Global.SP);
		username = sp[1];
	}
	
	public String getUsername()
	{
		return username;
	}
}
