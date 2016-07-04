package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

import Server.GUI.ServerGUI_Main;
import Util.Database;
import Util.Message;

public class ServerBackground extends Thread {

	private static ServerSocket server_socket = null;
	private static AcceptListen socket_listener = null;
	private static ArrayList<SocketThread> arrayList = null;

	private ServerGUI_Main mainGUI;
	
	public ServerBackground(ServerGUI_Main mainGui) {
		this.mainGUI = mainGui;
	}

	public void run() {
		try {
			server_socket = new ServerSocket();
			server_socket.bind(new InetSocketAddress("localhost", 5888));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		arrayList = new ArrayList<SocketThread>();

		socket_listener = new AcceptListen(server_socket, arrayList, mainGUI);
		socket_listener.start();

		while (true) {
			
			try {
				// ArrayList 돌면서 변화를 감시해야하므로 조금의 Thread 일시 정지가 있어야 작동함.
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block

				System.out.println("서버가 종료되었습니다.");			
				disconnection();				
				break;
			}	

			Delete_disconnSocket(arrayList);			
			
			// 사람이 있을 경우.
			if (!arrayList.isEmpty()) {
				for (SocketThread i : arrayList) {
					// 전송할 데이터가 있으면
					Message msg = i.getMsgClass();
					if (msg != null) {
						switch (msg.getMsgType()) {
						
						case Message.CS_TOTAL_MSG:
							Handling_TotalMsg(i,msg);							
							break;

						case Message.CS_SECRET_MSG:
							Handling_SecretMsg(i,msg);							
							break;
						}												
						i.setInitStatus();
					}					
				}
			}
		}
	}
	
	public void Handling_TotalMsg(SocketThread i , Message msg)
	{
		String str_msg = new String("[" + i.getSocket_name() + "]: " + msg.getMsg());
		Message total_msg = new Message(Message.SC_COMMON_MSG, str_msg);

		for (SocketThread j : arrayList) {
			j.sendMsg(total_msg);
		}
		mainGUI.AddTextArea(str_msg);
	}
	
	public void Handling_SecretMsg(SocketThread i, Message msg)
	{
		Message whisper_msg;
		if (!msg.getReceiverName().isEmpty()) {
			String str_msgToReceiver = new String(
					"[" + msg.getSenderName() + "] 님 으로부터 귓속말 도착 :" + msg.getMsg());
			whisper_msg = new Message(Message.SC_COMMON_MSG, str_msgToReceiver);

			for (SocketThread j : arrayList)
				if (j.getSocket_name().equals(msg.getReceiverName())) {
					j.sendMsg(whisper_msg);
					break;
				}

			String send_msgToSender = new String(
					"[" + msg.getReceiverName() + "] 님 에게 귓속말 보냄 : " + msg.getMsg());

			whisper_msg = new Message(Message.SC_COMMON_MSG, send_msgToSender);
			i.sendMsg(whisper_msg);
			
			mainGUI.AddTextArea(send_msgToSender);
		}
	}
	
	public void ServerToClient_BroadCasting(String msg)
	{
		for( SocketThread i : arrayList)
		{
			i.sendMsg(new Message(Message.SC_COMMON_MSG,msg));
		}
	}
	
	public void Delete_disconnSocket(ArrayList<SocketThread> arrayList)
	{
		// 종료된 소켓 삭제.
		int index;
		String msg = null;
		boolean IsDeleteUnit = false;
		for ( index = 0; index <  arrayList.size() ; index++) {
			if( !arrayList.get(index).getAliveStatus())
			{
				IsDeleteUnit = true;	
				msg = new String("["+arrayList.get(index).getSocket_name()+"] : 님이 퇴장 하셨습니다.");
				break;
			}
		}
		
		// 종료할 소켓이 있을 경우.
		if( IsDeleteUnit )
		{
			arrayList.remove(index);
			
			ArrayList<String> arrayName = new ArrayList<String>();
			for (SocketThread i : arrayList) {
				arrayName.add(i.getSocket_name());
			}
			// 종료 후, 다른 클라이언트에게 메시지 보냄
			for( SocketThread i : arrayList)
			{				
				i.sendMsg(new Message(Message.SC_CLIENT_EXIT_MSG, msg ,arrayName));
			}
		}
	}

	public void disconnection()
	{
		System.out.println("클라이언트들과 연결 종료");
		for(SocketThread i : arrayList)
		{			
			i.sendMsg(new Message(Message.SC_SERVER_EXIT_MSG,"서버가 종료되었습니다."));
			i.disconnect();			
		}		
		try {
			
			server_socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
