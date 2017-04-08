//�ǽð� û�븦 ���� ���ξ����尡 �ƴ� ������ ���� �����带 ������ ��� ����
package uni.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JTextArea;

public class ClientThread extends Thread{
	Socket socket;
	JTextArea area;
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public ClientThread(Socket socket, JTextArea area) {
		this.socket=socket;
		this.area=area;
		
		//�ǻ̱�
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	public void run() {
		while(true){
			listen();
		}		
	}
	
	//���ϱ�
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//���
	public void listen(){
		try {
			String data=buffr.readLine();
			area.append(data+"\n");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
