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
				// ArrayList ���鼭 ��ȭ�� �����ؾ��ϹǷ� ������ Thread �Ͻ� ������ �־�� �۵���.
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block

				System.out.println("������ ����Ǿ����ϴ�.");			
				disconnection();				
				break;
			}	

			Delete_disconnSocket(arrayList);			
			
			// ����� ���� ���.
			if (!arrayList.isEmpty()) {
				for (SocketThread i : arrayList) {
					// ������ �����Ͱ� ������
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
					"[" + msg.getSenderName() + "] �� ���κ��� �ӼӸ� ���� :" + msg.getMsg());
			whisper_msg = new Message(Message.SC_COMMON_MSG, str_msgToReceiver);

			for (SocketThread j : arrayList)
				if (j.getSocket_name().equals(msg.getReceiverName())) {
					j.sendMsg(whisper_msg);
					break;
				}

			String send_msgToSender = new String(
					"[" + msg.getReceiverName() + "] �� ���� �ӼӸ� ���� : " + msg.getMsg());

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
		// ����� ���� ����.
		int index;
		String msg = null;
		boolean IsDeleteUnit = false;
		for ( index = 0; index <  arrayList.size() ; index++) {
			if( !arrayList.get(index).getAliveStatus())
			{
				IsDeleteUnit = true;	
				msg = new String("["+arrayList.get(index).getSocket_name()+"] : ���� ���� �ϼ̽��ϴ�.");
				break;
			}
		}
		
		// ������ ������ ���� ���.
		if( IsDeleteUnit )
		{
			arrayList.remove(index);
			
			ArrayList<String> arrayName = new ArrayList<String>();
			for (SocketThread i : arrayList) {
				arrayName.add(i.getSocket_name());
			}
			// ���� ��, �ٸ� Ŭ���̾�Ʈ���� �޽��� ����
			for( SocketThread i : arrayList)
			{				
				i.sendMsg(new Message(Message.SC_CLIENT_EXIT_MSG, msg ,arrayName));
			}
		}
	}

	public void disconnection()
	{
		System.out.println("Ŭ���̾�Ʈ��� ���� ����");
		for(SocketThread i : arrayList)
		{			
			i.sendMsg(new Message(Message.SC_SERVER_EXIT_MSG,"������ ����Ǿ����ϴ�."));
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
