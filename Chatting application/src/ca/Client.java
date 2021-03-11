package ca;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.*;

public class Client extends JFrame implements ActionListener{
       
	private static final long serialVersionUID = 1L;
	
	JPanel headerPanel,footerPanel;
	JTextField txtfield;
	JButton sendbut;
	static JTextArea txtarea;
	 
	static BufferedReader br;
	static Socket s;
	static DataInputStream in;
	static DataOutputStream dout;
	
	
	public Client() {
		
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
		JLabel l2 = new JLabel("Jerry");
		l2.setBounds(150, 0, 50, 50);
		l2.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		headerPanel.add(l2);
		

		//Display Date, month and year
		JLabel l3 = new JLabel();
		l3.setBounds(130, 40, 100, 50);
		l3.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
		LocalDate ld = LocalDate.now();  
		l3.setText(ld.getMonth() + " " + ld.getDayOfMonth() + ", " + ld.getYear());
		add(l3);
		
		//background of text display			
		txtarea = new JTextArea();
		txtarea.setBounds(10, 72, 330, 430);
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
		setLocation(650, 100);
		setVisible(true);
		 
	}

	public void actionPerformed(ActionEvent ae) {
		  
		 try {
			 String out = txtfield.getText();
			 LocalTime lt = LocalTime.now();
			 String t = lt.getHour() + ":" + lt.getMinute();
			 txtarea.setText(txtarea.getText() + "\n\t\t" + out + "\n\t\t" + t);
			 dout.writeUTF(out);
			 txtfield.setText("");
		} catch (IOException e) { 
			e.printStackTrace();
		}
		  
	}
	public static void main(String[] args) {
		 new Client().setVisible(true);
		 
		 
		 try {
			 
			 s = new Socket("localhost", 6001);
			 in = new DataInputStream(s.getInputStream());
			 dout = new DataOutputStream(s.getOutputStream());
			 br = new BufferedReader(new InputStreamReader(System.in));
			 
			 String msg = "",m= "";
			 while(!m.equals("stop")) {  
				 msg = in.readUTF();
				 LocalTime lt = LocalTime.now();
				 String t = lt.getHour() + ":" + lt.getMinute();
				 txtarea.setText(txtarea.getText() + "\n" + msg + "\n" + t);
			 }
			 s.close();
		 }catch(Exception e) {}
	}

}
