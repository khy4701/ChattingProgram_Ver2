package Client;

import java.net.Socket;

public class ClientInfo {

	private Socket socket; 
	private String id;
	private String password;
	
	// Output Stream 
	//private 
	
	public ClientInfo(String id, String password)
	{
		this.id = id; 
		this.password = password;
	}
	public void setSocket(Socket socket)
	{
		this.socket = socket;
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public String getItem_Id()
	{
		return id;
	}
	public String getItem_Pw()
	{
		return password;
	}
}
	
	
	
