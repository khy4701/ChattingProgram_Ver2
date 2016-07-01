package Client;

import Util.DataBase;

public class ClientBackground {

	DataBase db ;
	ClientInfo userInfo;
	ClientGUI_Chatting Chatting_screen = null;
	
	public ClientBackground(ClientGUI_Chatting Chatting_screen){
		this.Chatting_screen = Chatting_screen;
		
		
	}
	
	public void disconnect()
	{
		
	}
	
	
}
