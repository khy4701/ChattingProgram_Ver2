package Util;

import Client.ClientInfo;

public class DataBase {
	
	public DataBase()
	{
		
	}
	
	public boolean Connection(String id , String pw)
	{		
		PrintMessage.printUserAndTimeOrder("ip : "+id + " , password : " + pw);
		
		
		//PrintMessage.printUserAndTimeOrder(" ���ῡ ���� �Ͽ����ϴ�. ");
		return true;
	}
	
	
	public ClientInfo getClientInfo()
	{
		return null;
	}
	

}
