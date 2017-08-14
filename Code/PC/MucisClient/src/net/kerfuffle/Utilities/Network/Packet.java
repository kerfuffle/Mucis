package net.kerfuffle.Utilities.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Packet {
	
	protected int id = -1;
	protected CharSequence data;
	
	private InetAddress ip;
	private int port;
	
	/**
	 * Need to define packet ids in some Global class (outside of this library)
	 */
	public Packet(CharSequence data, int id, InetAddress ip, int port)
	{
		this.id = id;
		this.data=data;
		this.ip=ip;
		this.port=port;
	}
	public Packet(CharSequence data, InetAddress ip, int port)
	{
		this.data=data;
		this.ip=ip;
		this.port=port;
	}
	public Packet(CharSequence data, int id)
	{
		this.id=id;
		this.data=data;
	}
	public Packet(int id, String splitChar, String...str)
	{
		this.id=id;
		this.data = formatData(splitChar, str);
	}
	public Packet(){}
	
	public CharSequence getData()
	{
		return data;
	}
	public int getId()
	{
		return id;
	}
	public InetAddress getIp()
	{
		return ip;
	}
	public int getPort()
	{
		return port;
	}
	public String toString()
	{
		return data.toString();
	}
	
	protected String formatData(String splitChar, String...str)
	{
		String ret = String.valueOf(id) + splitChar;
		for (String s : str)
		{
			ret += s + splitChar;
		}
		return ret;
	}
	
	
	public static void sendPacket(Packet p, DatagramSocket socket, InetAddress ip, int port) throws IOException
	{
		if (p.toString() == null)
		{
			return;
		}
		
		byte buffer[] = p.toString().getBytes();
		DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, ip, port);
		socket.send(sendPacket);
	}
	
	public static Packet receivePacket(DatagramSocket socket, String spc) throws IOException
	{
		byte buffer[] = new byte[256];
		DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
		socket.receive(receivePacket);
		
		String data = new String(receivePacket.getData());
		String sp[] = data.split(spc);
		
		int id = Integer.parseInt(sp[0]);
		Packet p = new Packet(data, id, receivePacket.getAddress(), receivePacket.getPort());

		return p;
	}
	
}

