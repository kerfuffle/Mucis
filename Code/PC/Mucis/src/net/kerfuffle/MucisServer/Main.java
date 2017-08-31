package net.kerfuffle.MucisServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import net.kerfuffle.MucisServer.Packets.PacketAddSong;
import net.kerfuffle.MucisServer.Packets.PacketError;
import net.kerfuffle.MucisServer.Packets.PacketLogin;
import net.kerfuffle.MucisServer.Packets.PacketRemoveSong;
import net.kerfuffle.MucisServer.Packets.PacketSync;
import net.kerfuffle.Utilities.Util;
import net.kerfuffle.Utilities.Network.MyNetworkCode;
import net.kerfuffle.Utilities.Network.Packet;
import net.kerfuffle.Utilities.Network.Server;
import net.kerfuffle.Utilities.Network.User;
import static net.kerfuffle.MucisServer.Global.*;

public class Main {

	private Server server;
	private int port;

	private ArrayList<Account> accounts = new ArrayList<Account>();

	public Main() throws SocketException
	{
		String users = Util.readTextFile("Users");
		String sp[] = users.split(",");
		for (int i = 0; i < sp.length; i++)
		{
			accounts.add(new Account(sp[i]));
		}

		port = Integer.parseInt(JOptionPane.showInputDialog("Port"));
		server = new Server("Mucis Server", port, SP);

		server.setMyNetworkCode(new MyNetworkCode()
		{
			public void run(Packet packet) throws IOException
			{
				if (packet.getId() == LOGIN)
				{
					PacketLogin p = new PacketLogin(packet.getData());
					
					if (userExists(p.getUsername()))
					{
						server.addUser(new User(p.getUsername(), packet.getIp(), packet.getPort()));
					}
					else
					{
						PacketError pe = new PacketError(USERNAME_NO_EXIST, "There is no account with that username.");
						server.sendToUser(pe, packet.getIp(), packet.getPort());
					}
				}
				if (packet.getId() == ADD_SONG)
				{
					PacketAddSong p = new PacketAddSong(packet.getData());
					int filePort = server.getUnusedPort();
					
					PacketAddSong pas = new PacketAddSong(filePort);
					server.sendToUser(pas, packet.getIp(), packet.getPort());
					
					Account acc = getAccountByName(server.getUsername(packet.getIp(), packet.getPort()));
					acc.library.addSong(new Song(p.getFileName(), acc.library.getHomePath()));
					
					server.receiveFileTCP(acc.library.getHomePath() + "/" + p.getFileName(), filePort, p.getFileSize());
				}
				if (packet.getId() == REMOVE_SONG)
				{
					PacketRemoveSong p = new PacketRemoveSong(packet.getData());
					Account acc = getAccountByName(server.getUsername(packet.getIp(), packet.getPort()));
					acc.library.removeSong(p.getFileName());
				}
				if (packet.getId() == SYNC)
				{
					PacketSync p = new PacketSync(packet.getData());
					
				}
				if (packet.getId() == DISCONNECT)
				{
					server.removeUser(packet.getIp(), packet.getPort());
				}
			}
		});
	}

	private Account getAccountByName(String name)
	{
		for (Account a : accounts)
		{
			if (a.getUsername().equals(name))
			{
				return a;
			}
		}
		return null;
	}
	private boolean userExists(String name)
	{
		for (Account a : accounts)
		{
			if (a.getUsername().equals(name))
			{
				return true;
			}
		}
		return false;
	}
	
	public void run()
	{
		server.start();
	}

	public static void main(String args[]) throws SocketException
	{
		new Main().run();
	}
}
