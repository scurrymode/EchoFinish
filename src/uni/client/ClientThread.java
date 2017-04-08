//실시간 청취를 위해 메인쓰레드가 아닌 개발자 정의 쓰레드를 루프에 대신 넣자
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
		
		//실뽑기
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
	
	//말하기
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//듣기
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
