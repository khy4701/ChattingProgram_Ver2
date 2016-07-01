package Util;

import Client.ClientInfo;

public class DataBase {
	
	public DataBase()
	{
		
	}
	
	public boolean Connection(String id , String pw)
	{		
		PrintMessage.printUserAndTimeOrder("ip : "+id + " , password : " + pw);
		
		
		//PrintMessage.printUserAndTimeOrder(" 연결에 실패 하였습니다. ");
		return true;
	}
	
	
	public ClientInfo getClientInfo()
	{
		return null;
	}
	

}
