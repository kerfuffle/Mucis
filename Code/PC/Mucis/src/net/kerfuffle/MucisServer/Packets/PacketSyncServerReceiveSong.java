package net.kerfuffle.MucisServer.Packets;

import net.kerfuffle.MucisServer.Global;
import net.kerfuffle.Utilities.Network.Packet;

public class PacketSyncServerReceiveSong extends Packet{
	
	public PacketSyncServerReceiveSong(String songName)
	{
		super(Global.SYNC_SERVER_RECEIVE_SONG, Global.SP, songName);
	}
}
