package Client;

public class ClientInfo {

	private String id;
	private String password;
	private String ip;
	private int port;
	
	// Output Stream 
	//private 
	
	public ClientInfo(String id, String password, String ip, int port)
	{
		this.id = id; 
		this.password = password;
		this.ip = ip;
		this.port = port;
	}
	
	public void setStreamInfo()
	{;
	}
	
	public String getItem_Ip()
	{
		return ip;
	}
	
	public int getItem_Port()
	{
		return port;
	}
}
	
	
	
