package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Server.GUI.ServerGUI_Main;
import Util.Message;
import Util.PrintMessage;

public class SocketThread extends Thread {

	private String socket_name;

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Message readMsg;
	ServerGUI_Main mainGUI;
	private boolean IsAlive =false;

	public SocketThread(Socket socket, String name, ServerGUI_Main mainGUI) {
		socket_name = name;
		System.out.println(name + "스레드 생성 완료");
		this.mainGUI = mainGUI;
		
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {

		IsAlive = true;

		while(true)
		{							
			try {
				readMsg = (Message)ois.readObject();				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				disconnect();
				break;
			}			
		}
	}

	public Message getMsgClass() {
		return readMsg;
	}

	public void setInitStatus() {
		readMsg = null;
	}

	public String getSocket_name() {
		return socket_name;
	}

	public ObjectOutputStream getObjectStream() {
		return oos;
	}
	
	public void sendMsg(Message msg)
	{
		try {
			oos.writeObject(msg);
			oos.flush();
			PrintMessage.printUserAndTimeOrder("클라이언트에게 전송 :"+ msg.getMsg());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}
	}
	
	public void disconnect()
	{
		try {			
			ois.close();
			oos.close();
					
			mainGUI.setExitPeople( socket_name ,"["+socket_name+"] 님이 퇴장하셨습니다.");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		IsAlive = false;		
	}
	
	public boolean getAliveStatus()
	{
		return IsAlive;
	}

}
