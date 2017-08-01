package net.kerfuffle.MucisServer;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import net.kerfuffle.MucisServer.Packets.PacketLogin;
import net.kerfuffle.Utilities.Util;
import net.kerfuffle.Utilities.Network.MyNetworkCode;
import net.kerfuffle.Utilities.Network.Packet;
import net.kerfuffle.Utilities.Network.Server;
import net.kerfuffle.Utilities.Network.User;

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
		server = new Server("Mucis Server", port, Global.SP);

		server.setMyNetworkCode(new MyNetworkCode()
		{
			public void run(Packet packet)
			{
				if (packet.getId() == Global.LOGIN)
				{
					PacketLogin p = new PacketLogin(packet.getData());
					server.addUser(new User(p.getUsername(), packet.getIp(), packet.getPort()));
				}
			}
		});
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
