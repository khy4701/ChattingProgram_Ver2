package Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import Client.ClientGUI.ClientGUI_Chatting;
import Client.ClientGUI.ClientGUI_Manager;
import Util.Message;
import Util.PrintMessage;

public class ClientBackground extends Thread {

	ClientInfo userInfo;
	ClientGUI_Chatting Chatting_screen = null;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	ClientGUI_Manager gui_Manager;
	
	public ClientBackground(ClientGUI_Chatting Chatting_screen, ClientInfo clientInfo, ClientGUI_Manager gui_Manager){
		this.userInfo = clientInfo;
		this.Chatting_screen = Chatting_screen;		
		this.gui_Manager = gui_Manager;
	}

	public void run() {
		// TODO Auto-generated method stub

		// Socket ����
		try {			
			Socket socket = new Socket();
			
			socket.connect(new InetSocketAddress("localhost", 5888));
			
			// Server���� Ŭ���̾�Ʈ�� �α��� ������ üũ�ϴ� �Լ�
			if( sendInitInfo(socket) == false )
			{				
				System.out.println("�ش� Ŭ���̾�Ʈ ���� ����");				
				// ********* �ٽ� �α��� ��������.
				gui_Manager.showLoginPage();
				return ;
			}
			
			PrintMessage.printUserAndTimeOrder("Ŭ���̾�Ʈ ���� ����");
			userInfo.setSocket(socket);
			
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());

			while (true) {

				PrintMessage.printUserAndTimeOrder("�����غ���!");
				Message listenMsg = (Message) ois.readObject();
				PrintMessage.printUserAndTimeOrder("�о���!");
				String client_name = userInfo.getItem_Id();
				PrintMessage.printUserAndTimeOrder("Listen ����");
				PrintMessage.printUserAndTimeOrder("msg: "+listenMsg.getMsg()+", type: "+listenMsg.getMsgType());
				if (listenMsg != null) {
					
					switch (listenMsg.getMsgType()) {

					case Message.SC_CONTROL_MSG:
						
						Handling_ControlMsg(listenMsg, client_name);
						
						break;
						
					case Message.SC_COMMON_MSG:
						
						PrintMessage.printUserAndTimeOrder("SC_COMMON_MSG �߰�");
						Chatting_screen.Add_TextData(listenMsg.getMsg());
						break;		
						
					case Message.SC_SERVER_EXIT_MSG:
						Chatting_screen.Add_TextData(listenMsg.getMsg());
						Chatting_screen.setTextChatField(false);
						break;
						
					case Message.SC_CLIENT_EXIT_MSG:
						
						PrintMessage.printUserAndTimeOrder("SC_EXIT_MSG ����");
						Handling_ExitMsg(listenMsg);
						
						break;
					
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				ois.close();
				userInfo.getSocket().close();
				PrintMessage.printUserAndTimeOrder("���� ����");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void Handling_ControlMsg( Message listenMsg, String client_name)
	{
		Chatting_screen.Add_TextData(listenMsg.getMsg());
		// ClientName GUI ����
		Chatting_screen.setClientName(client_name);
		// TotalNum GUI ����
		int size = listenMsg.getArrayName().size();
		Chatting_screen.setTotalNum(Integer.toString(size));
		// ListView ����.
		Chatting_screen.setListView(listenMsg.getArrayName());
	}
	
	public void Handling_ExitMsg(Message listenMsg)
	{
		Chatting_screen.Add_TextData(listenMsg.getMsg());
		// TotalNum GUI ����
		int array_size = listenMsg.getArrayName().size();
		Chatting_screen.setTotalNum(Integer.toString(array_size));
		// ListView ����.
		Chatting_screen.setListView(listenMsg.getArrayName());		
	}
	
	public void disconnect()
	{
		try {
			
			userInfo.getSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getClientName()
	{
		return userInfo.getItem_Id();
	}
	
	public void sendMsg(Message msg)
	{
		
		try {
			oos.writeObject(msg);
			oos.flush();
			PrintMessage.printUserAndTimeOrder("�������� ���� :"+ msg.getMsg());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				oos.close();
				userInfo.getSocket().close();		
				PrintMessage.printUserAndTimeOrder("���� ����");				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
	}
	
	public boolean sendInitInfo(Socket socket)
	{		
		String id = userInfo.getItem_Id();
		String pw = userInfo.getItem_Pw();
		
		String msg = id.concat("#"+pw);
		
		BufferedOutputStream bos;
		BufferedInputStream bis;
		
		try {
			bos = new BufferedOutputStream(socket.getOutputStream());
			bis = new BufferedInputStream(socket.getInputStream());
			
			bos.write(msg.getBytes());
			bos.flush();
						
			Thread.sleep(1000);
			
			byte []bytes = new byte[100];			
			int readByte = -1;
			
			readByte = bis.read(bytes);
			String control_msg = new String(bytes,0, readByte);
			
			
			if( control_msg.equals("success") )
				return true;
			else if( control_msg.equals("fail"))
				return false;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	
		
		return false;
	}
}
