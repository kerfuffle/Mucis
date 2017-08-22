package net.kerfuffle.Utilities.Network;

import java.net.InetAddress;

public class User {

	private InetAddress ip;
	private int port;
	private String username;
	
	/**
	 * For Server
	 * @param username
	 * @param ip
	 * @param port
	 */
	public User (String username, InetAddress ip, int port)
	{
		this.username=username;
		this.ip=ip;
		this.port=port;
	}
	
	/**
	 * For Client
	 * @param username
	 */
	public User(String username)
	{
		this.username=username;
	}
	
	
	public String getUsername()
	{
		return username;
	}
	public InetAddress getIp()
	{
		return ip;
	}
	public int getPort()
	{
		return port;
	}
	
}
