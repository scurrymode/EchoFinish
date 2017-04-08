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
	ServerSocket server; //���� ������ ����
	
	Thread thread; //���� ������ ������!
	
	BufferedReader buffr;
	BufferedWriter buffw;
	

	public ServerMain() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port), 10);
		bt_start = new JButton("����");
		area = new JTextArea();
		scroll = new JScrollPane(area);

		p_north.add(t_port);
		p_north.add(bt_start);
		

		add(p_north, BorderLayout.NORTH);
		add(scroll);
		
		//������ ����
		bt_start.addActionListener(this);
		
		setBounds(600, 100, 300, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//���� ���� �� ����
	//������ ���� checked Exception, runtime Exception
	public void startServer(){
		bt_start.setEnabled(false); //��ư ��Ȱ��ȭ
						
		try {
			port = Integer.parseInt(t_port.getText());
			server = new ServerSocket(port);
			area.append("��������, ���� �غ��..\n");
	
			Socket socket = server.accept();
			area.append("��������..\n");
			
			//���Ͽ� �ٷ� ����ű�
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			//Ŭ���̾�Ʈ�� �޼��� �ް� ������
			while(true){
				String data;
				data=buffr.readLine(); //�޼��� �ޱ�
				area.append("Ŭ���̾�ũ�� ��: "+data+"\n"); //����͸�
				buffw.write(data+"\n"); //�޼��� ������
				buffw.flush(); //���ۺ���!!
			}
			
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(this, "port�� ���ڷ� �־��!!");
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
