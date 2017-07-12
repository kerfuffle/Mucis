package net.kerfuffle.Utilities.Network;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import static net.kerfuffle.Utilities.Network.Packet.*;


public class Client implements Runnable{

	private Thread t;
	private volatile boolean running = false;
	
	private MyNetworkCode myNetworkCode;

	private DatagramSocket socket;
	private InetAddress ip;
	private int port;
	private String threadName;

	private Packet incoming = null;
	
	private ArrayList <User> users = new ArrayList <User>();

	public Client(String threadName, InetAddress ip, int port) throws SocketException
	{
		this.ip = ip;
		this.port=port;
		socket = new DatagramSocket();
		this.threadName = threadName;
	}

	public void close()
	{
		running = false;
	}
	
	public void start()
	{
		running = true;
		t = new Thread(this, threadName);
		t.start();
	}
	public void run()
	{
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
	
	public void setMyNetworkCode(MyNetworkCode myNetworkCode)
	{
		this.myNetworkCode = myNetworkCode;
	}
	public void addUser(User u)
	{
		users.add(u);
	}
	public void removeUser(User u)
	{
		users.remove(u);
	}
	public void removeUser(int i)
	{
		users.remove(i);
	}
	public void removeUser(String username)
	{
		for (User u : users)
		{
			if (u.getUsername().equals(username))
			{
				users.remove(u);
				return;
			}
		}
	}

	public InetAddress getIp()
	{
		return ip;
	}
	public int getPort()
	{
		return port;
	}

	public void sendPacket(Packet p) throws IOException
	{
		Packet.sendPacket(p, socket, ip, port);
	}

}
