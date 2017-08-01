package net.kerfuffle.MucisServer.Packets;

import net.kerfuffle.MucisServer.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketAddSong extends Packet{

	private String fileName;
	private int fileSize, port;
	
	public PacketAddSong(CharSequence data)
	{
		super(data, Global.ADD_SONG);
		String sp[] = data.toString().split(Global.SP);
		fileName = sp[1];
		fileSize = Integer.parseInt(sp[2]);
		port = Integer.parseInt(sp[3]);
	}
	public PacketAddSong(int port)
	{
		super(null, Global.ADD_SONG);
		data = id + Global.SP + port + Global.SP;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	public int getFileSize()
	{
		return fileSize;
	}
	public int getFilePort()
	{
		return port;
	}
}
