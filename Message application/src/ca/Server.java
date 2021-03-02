package ca;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server  extends JFrame implements ActionListener{
	    
		private static final long serialVersionUID = 1L;
		JPanel headerPanel,footerPanel; 
		JTextField txtfield;
		JButton sendbut;
		static JTextArea txtarea;
		
		static BufferedReader br;
		static ServerSocket skt;
		static Socket s;
		static DataInputStream in;
		static DataOutputStream dout;
		
		public Server() {
			
			//header panel			
			headerPanel = new JPanel();
			headerPanel.setLayout(null);
			headerPanel.setBackground(new Color(237, 237, 237));
			headerPanel.setBounds(0, 0, 350, 50);
			add(headerPanel); 
			//back icon			
			ImageIcon icon1 = new ImageIcon("back.png");
			Image img = icon1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
			ImageIcon icon2 = new ImageIcon(img);
			JLabel l1 = new JLabel(icon2);
			l1.setBounds(5, 10, 25, 25);
			headerPanel.add(l1);
			
			l1.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					System.exit(0);
				}
			});
			//name label
			JLabel l2 = new JLabel("Tom");
			l2.setBounds(170, 0, 50, 50);
			l2.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
			headerPanel.add(l2);
			//background of text display			
			txtarea = new JTextArea();
			txtarea.setBounds(10, 52, 330, 450);
			txtarea.setBackground(new Color(244, 244, 244)); 
			txtarea.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
			txtarea.setLineWrap(true);
			txtarea.setWrapStyleWord(false);
			txtarea.setEditable(false);
			add(txtarea);
			//footer panel
			footerPanel = new JPanel();
			footerPanel.setLayout(null);
			footerPanel.setBackground(new Color(237, 237, 237));
			footerPanel.setBounds(0, 500, 350, 50);
			add(footerPanel); 
			//message enter field
			txtfield = new JTextField();
			txtfield.setBounds(10, 10, 250, 30); 
			txtfield.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
			footerPanel.add(txtfield);
			//send button			
			sendbut = new JButton("Send");
			sendbut.setBounds(270, 10, 70, 30);
			sendbut.setBackground(new Color(237, 237, 237));
			sendbut.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
			sendbut.setBorder(null); 
			sendbut.setForeground(new Color(40, 153, 247));
			sendbut.addActionListener(this);
			footerPanel.add(sendbut);
			
			getContentPane().setBackground(new Color(244, 244, 244));
			setResizable(false);
			setLayout(null);
			setSize(350, 550);
			setUndecorated(true);
//			setLocationRelativeTo(null);
			setLocation(250, 100);
			setVisible(true);
			 
		}
 
		public void actionPerformed(ActionEvent ae) {
			 
			try {
				String out = txtfield.getText();
				txtarea.setText(txtarea.getText() + "\n\t\t" + out);
				dout.writeUTF(out);
				 txtfield.setText("");
			} catch (IOException e) { 
				e.printStackTrace();
			} 
			 
		}
		public static void main(String[] args) {
			 new Server().setVisible(true);
			 
			 String msg = "", m= "";
			 try {
				 skt = new ServerSocket(6001);
				 s = skt.accept();
				 in = new DataInputStream(s.getInputStream());
				 dout = new DataOutputStream(s.getOutputStream());
				 br = new BufferedReader(new InputStreamReader(System.in));
				 
				 
				 while(!m.equals("stop")) {
					 msg = in.readUTF();
					 txtarea.setText(txtarea.getText() + "\n" + msg);
				 }
				 skt.close();
				 s.close();
			 }catch(Exception e) {}
		}

}

