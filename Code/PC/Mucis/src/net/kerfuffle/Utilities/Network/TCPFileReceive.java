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
	private InetAddress ip;
	private int port;
	private boolean isServer = false;
	private Server server;


	public TCPFileReceive(InetAddress ip, int port, String path)
	{
		this.path = path;
		this.ip = ip;
		this.port = port;
		isServer = false;
	}
	public TCPFileReceive(int port, String path, Server server)
	{
		this.port = port;
		this.path = path;
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

		/***************************************
		 * 
		 * 
		 * OutputStream wont write to file
		 * 
		 * 
		 * 
		 * 
		 * 
		 */



		try {
			in = socket.getInputStream();
			out = new FileOutputStream(path);

			byte[] bytes = new byte[1024*8];
			
			 int count;
			 while ((count = in.read(bytes)) > 0) 
			 {
				 out.write(bytes, 0, count);
			 }
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		try 
		{
			out.close();
			in.close();
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




	public void close()
	{
		running = false;
	}
}
