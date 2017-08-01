package net.kerfuffle.Utilities.Network;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPFileReceive implements Runnable{

	private Thread t;
	private volatile boolean running = false;
	private String path;
	private int fileSize = -1;
	private InetAddress ip;
	private int port;
	private boolean isServer = false;
	private Server server;
	

	public TCPFileReceive(InetAddress ip, int port, String path, int fileSize)
	{
		this.path = path;
		this.fileSize = fileSize;
		this.ip = ip;
		this.port = port;
		isServer = false;
	}
	public TCPFileReceive(int port, String path, int fileSize, Server server)
	{
		this.port = port;
		this.path = path;
		this.fileSize = fileSize;
		isServer = true;
		this.server=server;
	}

	public void start()
	{
		running = true;
		t = new Thread(this, "Receiving: " + path);
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
		
		while (running)
		{
			try {
				in = socket.getInputStream();
				out = new FileOutputStream(path);

				byte[] bytes = new byte[fileSize];

				int count;
				while ((count = in.read(bytes)) > 0) {
					out.write(bytes, 0, count);
				}
				
				if (in.available() != 0)
				{
					running = false;
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		try 
		{
			out.close();
			in.close();
			socket.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		server.removeUsedPort(port);
	}




	public void close()
	{
		running = false;
	}
}
