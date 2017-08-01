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
	private volatile boolean running = false;
	private String path;
	private InetAddress ip;
	private int port;
	private boolean isServer = false;
	private Server server;
	
	public TCPFileSend(String path, int port, Server server)
	{
		this.path = path;
		this.port = port;
		this.server=server;
	}
	public TCPFileSend(String path, InetAddress ip, int port)
	{
		this.path=path;
		this.ip=ip;
		this.port = port;
	}
	
	public void start()
	{
		running = true;
		t = new Thread(this, "Sending: " + path);
		t.start();
	}
	
	public void run()
	{
		ServerSocket servsock = null;
		Socket socket = null;
		
		if (isServer)
		{
			try 
			{
				servsock = new ServerSocket(port);
				socket = servsock.accept();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			try 
			{
				socket = new Socket(ip, port);
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
		
		InputStream in = null;
		OutputStream out = null;
		
		Path fpath = Paths.get(path);
		byte data[] = null;
		try 
		{
			data = Files.readAllBytes(fpath);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		while (running)
		{
			
		}
	}
	
	public void close()
	{
		running = false;
	}
	
}
