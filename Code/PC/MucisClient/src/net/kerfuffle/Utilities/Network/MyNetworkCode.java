package net.kerfuffle.Utilities.Network;

import java.io.IOException;

public abstract class MyNetworkCode {

	/**
	 * Only really meant for this network library
	 * @param p
	 * @throws IOException
	 */
	public abstract void run(Packet p) throws IOException;
	
}
