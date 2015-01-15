/**
 * @(#)LoginForm.java
 *
 *
 * @author
 * @version 1.00 2010/4/25
 */
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.LookAndFeel;

public class LoginForm extends JFrame implements ActionListener{
	private Container container;
	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private JButton cmdLogin, cmdCancel;
	private JLabel lblUSer, lblPassword,lblLevel;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private boolean b;
	private static int level = 0;

	public static int getLevel(){
		return level;
	}

	public LoginForm(){

		setTitle("Login Form");
		this.setUndecorated(true);
		setIconImage (getToolkit().getImage("Gambar/login.png"));
		setResizable(false);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		setSize(290,145);
		setLocationRelativeTo(null);

		container = getContentPane();
		layout = new GridBagLayout();
		container.setLayout(layout);

		gbc = new GridBagConstraints();
		lblUSer = new JLabel("Username");
		gbc.insets = new Insets(2,2,2,2);
		container.add(lblUSer, gbc);

		txtUser = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridwidth = 3;
		container.add(txtUser, gbc);

		lblPassword = new JLabel("Password");
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		container.add(lblPassword, gbc);

		txtPassword = new JPasswordField(15);
		gbc.gridx = 1;
		gbc.gridwidth = 3;
		container.add(txtPassword, gbc);

		cmdLogin = new JButton("Login");
		cmdLogin.addActionListener( this );
		gbc.gridy = 2;
		gbc.gridx = 1;
		gbc.gridwidth = 1;
		container.add(cmdLogin, gbc);

		cmdCancel = new JButton("Cancel");
		cmdCancel.addActionListener( this );
		gbc.gridx = 2;
		container.add(cmdCancel, gbc);

		this.getRootPane().setDefaultButton(cmdLogin);

		addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent e){
				cancel();
        	}
        });
	}

	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("Login")){
			doLogin();
		}else{
			cancel();
		}

	} //actionPerformed()

	public void doLogin(){
		String u = txtUser.getText();
		String p = txtPassword.getText();
		Database.connectDb();
		User o = new User(u, p);
		b = o.isValidUser();
		if(b){
			this.setVisible(false);
			this.level = User.getLevelUser(u);
			MenuUtama main = new MenuUtama();
		}else
			JOptionPane.showMessageDialog(null, "Username atau Password anda salah", "Warning !!!", JOptionPane.WARNING_MESSAGE);
	}

	public void cancel(){
		int respond = JOptionPane.showConfirmDialog(null, "Anda yakin akan keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
		if(respond == 0) dispose();
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
      		public void run() {
      			try{
		        	LookAndFeel lf = new com.jtattoo.plaf.acryl.AcrylLookAndFeel();
		     		UIManager.setLookAndFeel(lf);
		     		new LoginForm().setVisible(true);
		  		}catch(Exception e) {
		  		    System.out.println("Error : " + e);
		  		}

      		}
    	});

	}
}