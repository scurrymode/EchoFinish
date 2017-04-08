/*
 * 소켓과 스트림을 서버에 1개만 두었더니, 접속자마다 소켓과 스트림을 차지해버려 쟁탈전이 벌어진다.
 * 소캣과 스트림 유지가 되지 않는다.
 * 해결책! 각 사용자마다 자신만의 소캣과 스트림이 필요하므로, 접속자마다 인스턴스를 생성하여, 
 * 그 인스턴스 안에 각각의 소켓과 스트림들을 보관해두자!
 * 별도의 독립된 동작을 해야 하므로, 쓰레드로 정의한다! * 
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
			//대화를 나눌 스트림 뽑기
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//프로그램이 종료될때까지 끝없이 클라이언트의 메세지를 받아와서 다시 보낸다.
	public void run() {
		while(true){
			listen(); //듣고 말하기~
		}
	}
	
	
	//듣고
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine();
			send(msg);//서버는 듣자마자 바로 보내기
			area.append(msg+"\n");//모니터링
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//말하기
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
			
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
