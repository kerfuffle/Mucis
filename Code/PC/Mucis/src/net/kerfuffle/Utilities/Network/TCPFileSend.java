package net.kerfuffle.Utilities.Network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TCPFileSend implements Runnable{

	private Thread t;
	private String path;
	private InetAddress ip;
	private int port;
	private boolean isServer = false;
	private Server server;
	private byte[] file;
	
	public TCPFileSend(byte file[], int port, Server server)
	{
		this.port = port;
		this.server=server;
		isServer = true;
		this.file = file;
	}
	public TCPFileSend(byte file[], InetAddress ip, int port)
	{
		this.ip=ip;
		this.port = port;
		isServer = false;
		this.file = file;
	}
	
	public void start()
	{
		t = new Thread(this, "Sending: " + path);
		t.start();
	}
	
	public void run()
	{
		ServerSocket servsock = null;
		Socket socket = null;
		OutputStream out = null;
		
		try
		{
			if (isServer)
			{
				servsock = new ServerSocket(port);
				socket = servsock.accept();
			}
			else
			{
				socket = new Socket(ip, port);
			}
			
			out = socket.getOutputStream();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try 
		{	
			out.write(file, 0, file.length);
			out.flush();
			
			out.close();
			socket.close();
			if (isServer)
			{
				servsock.close();
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		if (isServer)
		{
			server.removeUsedPort(port);
		}
	}
}
