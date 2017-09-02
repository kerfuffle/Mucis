package net.kerfuffle.MucisClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import net.kerfuffle.MucisClient.Packets.PacketAddSong;
import net.kerfuffle.MucisClient.Packets.PacketError;
import net.kerfuffle.MucisClient.Packets.PacketLogin;
import net.kerfuffle.MucisClient.Packets.PacketRemoveSong;
import net.kerfuffle.MucisClient.Packets.PacketSync;
import net.kerfuffle.MucisClient.Packets.PacketSyncServerReceiveSong;
import net.kerfuffle.MucisClient.Packets.PacketSyncServerSendSong;
import net.kerfuffle.Utilities.Util;
import net.kerfuffle.Utilities.Network.Client;
import net.kerfuffle.Utilities.Network.MyNetworkCode;
import net.kerfuffle.Utilities.Network.Packet;

public class Main {

	private Client client;
	private InetAddress ip;
	private int port;
	
	private String tempPath = "";
	private String homePath = "C:/users/rdavis/desktop/mucisclient/";

	public Main() throws IOException
	{
		String in = JOptionPane.showInputDialog("IP:Port");
		String sp[] = in.split(":");
		ip = InetAddress.getByName(sp[0]);
		port = Integer.parseInt(sp[1]);
		
		client = new Client("MucisClient", ip, port, Global.SP);
		
		String username = JOptionPane.showInputDialog("Username");
		PacketLogin pl = new PacketLogin(username);
		client.sendPacket(pl);

		client.setMyNetworkCode(new MyNetworkCode() {
			public void run(Packet packet) throws IOException
			{
				if (packet.getId() == Global.ADD_SONG)
				{
					PacketAddSong pas = new PacketAddSong(packet.getData());
					
					byte file[] = Util.readFile(tempPath);
					
					client.sendFileTCP(file, pas.getFilePort());
				}
				if (packet.getId() == Global.SYNC_SERVER_RECEIVE_SONG)
				{
					PacketSyncServerReceiveSong p = new PacketSyncServerReceiveSong(packet.getData());
					
					byte file[] = Util.readFile(lookupSongPath(p.getFileName()));	//TODO access denied exception?
					
					PacketAddSong pas = new PacketAddSong(p.getFileName(), file.length);
					client.sendPacket(pas);
				}
				if (packet.getId() == Global.SYNC_SERVER_SEND_SONG)
				{
					PacketSyncServerSendSong p = new PacketSyncServerSendSong(packet.getData());
					client.receiveFileTCP(p.getFilePort(), Global.homePath+p.getFileName());
				}
				if (packet.getId() == Global.SYNC)
				{
					PacketSync p = new PacketSync(packet.getData());
					
					byte file[] = Util.readFile(homePath + "songlist.dab"); 		// need a home path for client
					
					client.sendFileTCP(file, p.getFilePort());
				}
				if (packet.getId() == Global.ERROR)
				{
					PacketError p = new PacketError(packet.getData());
					
					if (p.getType() == Global.USERNAME_NO_EXIST)
					{
						String username = JOptionPane.showInputDialog("Username");
						PacketLogin pl = new PacketLogin(username);
						client.sendPacket(pl);
					}
				}
			}
		});
	}
	
	public void sendTestFile() throws IOException
	{
		tempPath = "C:/users/rdavis/desktop/Snapchat-336644946.jpg";
		byte file[] = Util.readFile(tempPath);
		
		PacketAddSong pas = new PacketAddSong("Snapchat-336644946.jpg", file.length);
		client.sendPacket(pas);
	}

	private boolean running = true;
	public void run() throws IOException
	{
		client.start();
		
		Scanner scan = new Scanner(System.in);
		while (running)
		{	
			String in = scan.nextLine();
			
			if (in.equals("test"))
			{
				sendTestFile();
			}
			else if (in.equals("quit"))
			{
				running = false;
			}
			else if (in.equals("sync"))
			{
				PacketSync ps = new PacketSync();
				client.sendPacket(ps);
			}
			else
			{
				String sp[] = in.split(" ");
				
				if (sp[0].equals("addsong"))
				{
					tempPath = sp[1];
					byte file[] = Util.readFile(tempPath);
				
					String sp1[] = (sp[1].contains("/")) ? sp[1].split("/") : sp[1].split("\\");
					
					PacketAddSong pas = new PacketAddSong(sp1[sp1.length-1], file.length);
					client.sendPacket(pas);
				}
				else if (sp[0].equals("removesong"))
				{
					PacketRemoveSong prs = new PacketRemoveSong(sp[1]);
					client.sendPacket(prs);
				}
			}
		}
		scan.close();
	}
	
	private String lookupSongPath(String song)
	{
		String data = Util.readTextFile(Global.homePath + "songlist.dab");
		String sp0[] = data.split(">");
		
		for (int i = 0; i < sp0.length; i++)
		{
			String sp[] = sp0[i].split("\\?");
			if (sp[0].equals(song))
			{
				return sp[1];
			}
		}
		return "[NOT FOUND]";
	}

	public static void main (String args[]) throws IOException
	{
		new Main().run();
	}
}
