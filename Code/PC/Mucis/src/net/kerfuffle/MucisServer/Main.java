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
import net.kerfuffle.MucisServer.Packets.PacketSyncServerReceiveSong;
import net.kerfuffle.MucisServer.Packets.PacketSyncServerSendSong;
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

					server.receiveFileTCP(acc.library.getHomePath() + "/" + p.getFileName(), filePort);
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
					int fileport = server.getUnusedPort();

					PacketSync ps = new PacketSync(fileport);
					server.sendToUser(ps, packet.getIp(), packet.getPort());

					Account acc = getAccountByName(server.getUsername(packet.getIp(), packet.getPort()));
					server.receiveFileTCP(acc.library.getHomePath() + "temp/clientsongs.dab", fileport);

					//server.isLastFileTransferFinished

					String toSend[] = findMissingSongsToSend(Util.readTextFile(Global.homePath+"musicpaths.dab"), Util.readTextFile(acc.library.getHomePath() + "temp/clientsongs.dab"));
					String toReceive[] = findMissingSongsToReceive(Util.readTextFile(Global.homePath+"musicpaths.dab"), Util.readTextFile(acc.library.getHomePath() + "temp/clientsongs.dab"));

					for (int i = 0; i < toSend.length; i++)
					{
						System.out.println(toSend.length);
					}

					for (int i = 0; i < toSend.length; i++)
					{
						int sendPort = server.getUnusedPort();

						byte file[] = Util.readFile(lookupSongPath(toSend[i]));

						PacketSyncServerSendSong pssss = new PacketSyncServerSendSong(toSend[i], file.length, sendPort);
						server.sendToUser(pssss, packet.getIp(), packet.getPort());
						server.sendFileTCP(file, sendPort);
					}

					for (int i = 0; i < toReceive.length; i++)
					{
						PacketSyncServerReceiveSong pssrs = new PacketSyncServerReceiveSong(toReceive[i]);
						server.sendToUser(pssrs, packet.getIp(), packet.getPort());
					}
				}
				if (packet.getId() == DISCONNECT)
				{
					server.removeUser(packet.getIp(), packet.getPort());
				}
			}
		});
	}

	private String[] findMissingSongsToReceive(String serverList, String clientList)
	{
		ArrayList <String> receive = new ArrayList<String>();

		String serverListsp0[] = serverList.split(">");
		String clientListsp0[] = clientList.split(">");

		for (int i = 0; i < clientListsp0.length; i++)
		{
			if (true)//clientListsp0[i].contains("\\?"))
			{
				String clientListsp[] = clientListsp0[i].split("\\?");
				for (int j = 0; j < serverListsp0.length; j++)
				{
					String serverListsp[] = serverListsp0[j].split("\\?");

					if (serverListsp[0].equals(clientListsp[0]))
					{
						break;
					}

					if (j == serverListsp.length-1)
					{
						receive.add(clientListsp[0]);
					}
				}
			}
		}

		return receive.toArray(new String[receive.size()]);
	}

	private String lookupSongPath(String name)
	{
		String data = Util.readTextFile(Global.homePath + "MusicPaths.dab");
		String sp0[] = data.split(">");

		for (int i = 0; i < sp0.length; i++)
		{
			if (sp0[i].contains("\\?"))
			{
				String sp[] = sp0[i].split("\\?");
				if (sp[0].equals(name))
				{
					return sp[1];
				}
			}
		}
		return "[NOT FOUND]";
	}

	private String[] findMissingSongsToSend(String serverList, String clientList)		//[row][column] column1=receive from client, column2=send to client
	{
		ArrayList <String> send = new ArrayList<String>();

		String serverListsp0[] = serverList.split(">");
		String clientListsp0[] = clientList.split(">");

		for (int i = 0; i < serverListsp0.length; i++)
		{
			if (serverListsp0[i].contains("\\?"))
			{
				String serverListsp[] = serverListsp0[i].split("\\?");
				for (int j = 0; j < clientListsp0.length; j++)
				{
					String clientListsp[] = clientListsp0[j].split("\\?");

					if (serverListsp[0].equals(clientListsp[0]))
					{
						break;
					}

					if (j == clientListsp0.length-1)
					{
						send.add(serverListsp[0]);
					}
				}
			}
		}

		return send.toArray(new String[send.size()]);
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
