package net.kerfuffle.MucisServer.Packets;

import net.kerfuffle.MucisServer.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketSyncServerSendSong extends Packet{

	public PacketSyncServerSendSong(String fileName, int fileSize, int port)
	{
		super (Global.SYNC_SERVER_SEND_SONG, Global.SP, fileName, String.valueOf(fileSize), String.valueOf(port));
	}
	
	
	
}
