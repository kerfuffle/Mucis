package net.kerfuffle.Utilities.Network;

import static net.kerfuffle.Utilities.Network.Packet.*;
import static net.kerfuffle.Utilities.Network.Packet.receivePacket;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Server implements Runnable{
	
	private Thread t;
	private String threadName;
	private int port;
	private volatile boolean running = false;
	private MyNetworkCode myNetworkCode;
	private DatagramSocket socket;
	
	private ArrayList <User> users = new ArrayList<User>();
	
	public Server(String threadName, int port) throws SocketException
	{
		this.port = port;
		this.threadName = threadName;
		socket = new DatagramSocket(port);
	}

	public void setMyNetworkCode(MyNetworkCode myNetworkCode)
	{
		this.myNetworkCode = myNetworkCode;
	}
	
	public void start()
	{
		running = true;
		t = new Thread(this, threadName);
		t.start();
	}
	
	public void run()
	{
		Packet incoming = null;
		while (running)
		{
			try 
			{
				incoming = receivePacket(socket);
				myNetworkCode.run(incoming);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void close()
	{
		running = false;
	}
	
	public int getPort()
	{
		return port;
	}
	public void addUser(User u)
	{
		users.add(u);
	}
	public void removeUser(User u)
	{
		users.remove(u);
	}
	public void removeUser (int i)
	{
		users.remove(i);
	}
	public void removeUser(InetAddress ip, int port)
	{
		for (User u : users)
		{
			if (u.getIp().toString().equals(ip.toString()) && u.getPort() == port)
			{
				users.remove(u);
				return;
			}
		}
	}
	
	public String getUsername(InetAddress ip, int port)
	{
		for (User u : users)
		{
			if (u.getIp().toString().equals(ip.toString()) && u.getPort() == port)
			{
				return u.getUsername();
			}
		}
		
		System.err.println("IP and Port do not match any users.");
		return null;
	}
	
	public void sendToUser(Packet p, InetAddress ip, int port) throws IOException
	{
		Packet.sendPacket(p, socket, ip, port);
	}
	public void sendToAllUsers(Packet p) throws IOException
	{
		for (User u : users)
		{
			Packet.sendPacket(p, socket, u.getIp(), u.getPort());
		}
	}
	public void sendToAllUsersExcept(Packet p, InetAddress ip, int port) throws IOException
	{
		for (User u : users)
		{
			if (u.getIp().toString().equals(ip.toString()) && u.getPort() == port)
			{
				continue;
			}
			Packet.sendPacket(p, socket, u.getIp(), u.getPort());
		}
	}
	
}
