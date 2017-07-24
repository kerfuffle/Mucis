package net.kerfuffle.MucisServer.Packets;

import net.kerfuffle.MucisServer.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketLogin extends Packet{

	private String username;
	private String password;
	
	public PacketLogin(CharSequence data)
	{
		super(data, Global.LOGIN);
		String sp[] = data.toString().split(Global.SP);
		username = sp[1];
		password = sp[2];
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
}
