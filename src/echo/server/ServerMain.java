package echo.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerMain extends JFrame implements ActionListener{
	JPanel p_north;
	JTextField t_port;
	JButton bt_start;
	JTextArea area;
	JScrollPane scroll;
	int port = 7777;
	ServerSocket server; //접속 감지용 소켓
	
	Thread thread; //서버 가동용 쓰레드!
	
	BufferedReader buffr;
	BufferedWriter buffw;
	

	public ServerMain() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port), 10);
		bt_start = new JButton("가동");
		area = new JTextArea();
		scroll = new JScrollPane(area);

		p_north.add(t_port);
		p_north.add(bt_start);
		

		add(p_north, BorderLayout.NORTH);
		add(scroll);
		
		//리스너 연결
		bt_start.addActionListener(this);
		
		setBounds(600, 100, 300, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//서버 생성 및 가동
	//예외의 종류 checked Exception, runtime Exception
	public void startServer(){
		bt_start.setEnabled(false); //버튼 비활성화
						
		try {
			port = Integer.parseInt(t_port.getText());
			server = new ServerSocket(port);
			area.append("서버생성, 서버 준비됨..\n");
	
			Socket socket = server.accept();
			area.append("서버가동..\n");
			
			//소켓에 바로 빨대꼽기
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			//클라이언트의 메세지 받고 보내기
			while(true){
				String data;
				data=buffr.readLine(); //메세지 받기
				area.append("클라이언크의 말: "+data+"\n"); //모니터링
				buffw.write(data+"\n"); //메세지 보내기
				buffw.flush(); //버퍼비우기!!
			}
			
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(this, "port는 숫자로 넣어라!!");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void actionPerformed(ActionEvent e) {
		thread = new Thread(){
			public void run() {
				startServer();
			}
		};
		
		thread.start();
	}
	
	

	public static void main(String[] args) {
		new ServerMain();

	}

}
