/*
 * ���ϰ� ��Ʈ���� ������ 1���� �ξ�����, �����ڸ��� ���ϰ� ��Ʈ���� �����ع��� ��Ż���� ��������.
 * ��Ĺ�� ��Ʈ�� ������ ���� �ʴ´�.
 * �ذ�å! �� ����ڸ��� �ڽŸ��� ��Ĺ�� ��Ʈ���� �ʿ��ϹǷ�, �����ڸ��� �ν��Ͻ��� �����Ͽ�, 
 * �� �ν��Ͻ� �ȿ� ������ ���ϰ� ��Ʈ������ �����ص���!
 * ������ ������ ������ �ؾ� �ϹǷ�, ������� �����Ѵ�! * 
 * */

package uni.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class Avatar extends Thread	{
	Socket socket;
	JTextArea area;
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public Avatar(Socket socket, JTextArea area) {
		this.socket=socket;
		this.area=area;
		
		try {
			//��ȭ�� ���� ��Ʈ�� �̱�
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���α׷��� ����ɶ����� ������ Ŭ���̾�Ʈ�� �޼����� �޾ƿͼ� �ٽ� ������.
	public void run() {
		while(true){
			listen(); //��� ���ϱ�~
		}
	}
	
	
	//���
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine();
			send(msg);//������ ���ڸ��� �ٷ� ������
			area.append(msg+"\n");//����͸�
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���ϱ�
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
			
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
