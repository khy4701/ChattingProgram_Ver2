package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Server.GUI.ServerGUI_Main;
import Util.Database;
import Util.Message;

public class AcceptListen extends Thread {

	ServerSocket server_socket;
	ArrayList<SocketThread> arrayList;
	Database db;
	ServerGUI_Main mainGUI;
	boolean IsNewPeople = false;
	
	public AcceptListen(ServerSocket server_socket, ArrayList<SocketThread> arrayList, ServerGUI_Main mainGUI ) {
		this.server_socket = server_socket;
		this.arrayList = arrayList;
		this.mainGUI = mainGUI;
		
		db = new Database();
	}

	public void run() {
		Socket socket = null;
		try {
			while ((socket = server_socket.accept()) != null) {
				String clientName = null;

				if ((clientName = Authentication(socket)) != null) {
					
					System.out.println("���� �Ϸ�");
					// socket ���� --> ���⼭ �����ڿ��� oos, ois ��Ʈ�� ����� ���!
					// ���� - Ŭ���̾�Ʈ�� ������ �¾ƾ��� , stream�� �ѹ��� ���� (������ �ٸ��� �����Ѱ���)
					// ���� : bis - oos - ois
					// Ŭ���̾�Ʈ : bos - ois - oos

					SocketThread s_th = new SocketThread(socket, clientName, mainGUI);
					arrayList.add(s_th);
					s_th.start();

					ArrayList<String> arrayName = new ArrayList<String>();
					for (SocketThread i : arrayList) {
						arrayName.add(i.getSocket_name());
					}

					// Client �鿡�� ���� Client ���� �Ѹ���
					String in_msg = new String("[" + s_th.getSocket_name() + "]: " + "�� �����Ͽ����ϴ�.");

					for (SocketThread i : arrayList) {								
							i.sendMsg(new Message(Message.SC_CONTROL_MSG, in_msg, arrayName));
					}
					
					mainGUI.setNewPeople(arrayName, in_msg );					
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String Authentication(Socket socket) {
		BufferedInputStream bis;
		BufferedOutputStream bos;

		String controlMsg;

		byte[] bytes = new byte[100];
		int readByte = -1;

		try {
			bis = new BufferedInputStream(socket.getInputStream());
			bos = new BufferedOutputStream(socket.getOutputStream());

			System.out.println("�ʱ� ������ �б���");
			readByte = bis.read(bytes);
			controlMsg = new String(bytes, 0, readByte);
			System.out.println("�ʱ� ������ ���� : " + controlMsg); 

			Thread.sleep(1000);
			
			String []readStr = controlMsg.split("#");
						
			if( db.CheckIsUser(readStr[0], readStr[1]) )
			{
				System.out.println("���� �Ϸ�g");				
				bos.write(new String("success").getBytes());
				bos.flush();
				System.out.println("���� ���: success "); 

				return readStr[0];  // ���� ����  �ÿ��� ���̵� �ѱ�.
			}else
			{
				bos.write(new String("fail").getBytes());				
				bos.flush();
				System.out.println("���� ���: Fail" ); 
				return null;
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
