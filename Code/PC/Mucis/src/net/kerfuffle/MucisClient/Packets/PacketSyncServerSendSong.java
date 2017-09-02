package net.kerfuffle.MucisClient.Packets;

import net.kerfuffle.MucisClient.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketSyncServerSendSong extends Packet{
	
	private String fileName;
	private int fileLength, filePort;
	
	public PacketSyncServerSendSong(CharSequence data)
	{
		super(data, Global.SYNC_SERVER_SEND_SONG);
		String sp[] = data.toString().split(Global.SP);
		fileName = sp[1];
		fileLength = Integer.parseInt(sp[2]);
		filePort = Integer.parseInt(sp[3]);
	}
	
	public String getFileName()
	{
		return fileName;
	}
	public int getFilePort()
	{
		return filePort;
	}
	public int getFileLength()
	{
		return fileLength;
	}

}
