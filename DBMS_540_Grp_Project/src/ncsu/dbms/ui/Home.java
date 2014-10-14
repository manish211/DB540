package ncsu.dbms.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ListSelectionListener;

import ncsu.dbms.core.LocalSession;


public class Home extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		setLocationRelativeTo(null); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_Center = new JPanel();
		panel_Center.setBounds(12, 141, 211, 236);
		contentPane.add(panel_Center);
		panel_Center.setLayout(null);
		
		JButton btn_ViewScore = new JButton("ViewScore");
		btn_ViewScore.setBounds(12, 47, 187, 33);
		panel_Center.add(btn_ViewScore);
		
		JButton btn_AttemptHomework = new JButton("Attempt Homework");
		btn_AttemptHomework.setBounds(12, 83, 187, 33);
		panel_Center.add(btn_AttemptHomework);
		
		JButton btn_ViewSubmissions = new JButton("Past Submissions");
		btn_ViewSubmissions.setBounds(12, 125, 187, 33);
		panel_Center.add(btn_ViewSubmissions);
		
		JButton btn_Notification = new JButton("Notification");
		btn_Notification.setBounds(12, 161, 187, 33);
		panel_Center.add(btn_Notification);
		
		JPanel panel_Bottom = new JPanel();
		panel_Bottom.setBounds(12, 390, 211, 224);
		contentPane.add(panel_Bottom);
		
		JPanel panel_Top = new JPanel();
		panel_Top.setBounds(12, 13, 211, 128);
		contentPane.add(panel_Top);
		panel_Top.setLayout(null);
		
		final JPanel panel_Right = new JPanel();
		panel_Right.setBounds(230, 13, 640, 627);
		contentPane.add(panel_Right);
		panel_Right.setLayout(null);
		
		JLabel lbl_UserName=new JLabel("");
		lbl_UserName.setBounds(12, 10, 187, 33);
		panel_Top.add(lbl_UserName);
		panel_Right.setLayout(null);
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnHome.setBounds(12, 46, 187, 33);
		panel_Top.add(btnHome);
		
		JButton btn_Logout = new JButton("Logout");
		btn_Logout.setBounds(12, 82, 187, 33);
		panel_Top.add(btn_Logout);
		
		OpenHomePage(panel_Right);
	}

	//handlers home button click
	public void OpenHomePage(JPanel homePanel)
	{
		homePanel.removeAll();
		
		//homePanel.setBackground(Color.gray);
		JLabel lbl_SelectCourse=new JLabel("Select a course");
		lbl_SelectCourse.setBounds(100, 10,300,30);
		homePanel.add(lbl_SelectCourse);
		
		//load all the courses
		String[] data = {"one", "two", "three", "four"};
		final JList<String> listCourses = new JList<String>(data);
		//final JList<String> listCourses = new JList<String>();
		listCourses.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				LocalSession.SetCurrentSelectedCourse(listCourses.getSelectedIndex());
			}
		});
		listCourses.setBounds(100, 50,300,100);
		
		homePanel.add(listCourses);
		
		JLabel lbl_AddCourse=new JLabel("Add a course");
		lbl_AddCourse.setBounds(100, 300,100,30);
		homePanel.add(lbl_AddCourse);
		
		JLabel lbl_AddToken=new JLabel("Provide a token");
		lbl_AddToken.setBounds(100, 340,100,30);
		homePanel.add(lbl_AddToken);
		
		JTextField txt_Token=new JTextField();
		txt_Token.setBounds(250, 340, 100,30);
		txt_Token.setToolTipText("Token");
		homePanel.add(txt_Token);
		
		JButton btn_AddCourse=new JButton("Add Course");
		btn_AddCourse.setBounds(370, 340, 100, 30);
		btn_AddCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddCourse();
			}
		});
		homePanel.add(btn_AddCourse);
		
	}
	
	private void AddCourse()
	{
		
	}
}
