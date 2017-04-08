/*
 클라이언트 메인 스레드에게는 운영만 맡기고 이 쓰레드를 통해서 귀랑 입 둬서
 socket에게 말 전하고 듣자~!
 */

package echo.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JTextArea;

public class ClientThread extends Thread{
	boolean flag=true;
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	JTextArea area;
	
	public ClientThread(Socket socket, JTextArea area) {
		this.socket=socket;
		this.area=area;
		
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//서버의 메세지 받아오기!!
		public void listen(){
			String msg=null;
			
			try {
				msg=buffr.readLine();
				area.append(msg+"\n");	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//서버에 메세지 보내기
		public void send(String msg){
			try {
				buffw.write(msg+"\n");
				buffw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	
	public void run() {
		while(flag){
			listen();
		}
	}

}
