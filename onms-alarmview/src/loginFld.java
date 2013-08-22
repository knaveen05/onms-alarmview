import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

//import com.sun.java.util.jar.pack.Package.File;


public class loginFld {
	static JTextField urlFld = new JTextField(25);
	static JTextField portFld = new JTextField(5);
	static JTextField unameFld = new JTextField(5);
	static JTextField passFld = new JPasswordField(5);
	static JTextField proxyFld = new JTextField(5);
	static JTextField proxyportFld = new JTextField(5);
//	static String content = "This is the content to write into file tam tama tam";
	public static void main(String[] args) throws FileNotFoundException{
		readFile();
		loginFld();
	//	readFile();
		/*	
		try {
			File iniFile = new File("onmsconfig.ini");
			if (iniFile.exists()){
				BufferedReader iniReader = new BufferedReader(new FileReader("onmsconfig.ini"));
				String line = null;
				while ((line = iniReader.readLine()) != null){
					System.out.println(line);
				}
			} else{
				iniFile.createNewFile();
			}
			FileWriter fw = new FileWriter(iniFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	*/	
   }
	public static void readFile(){
	try{
		File iniFile = new File("onmsconfig.ini");
		if (iniFile.exists()){
			BufferedReader iniReader = new BufferedReader(new FileReader("onmsconfig.ini"));
			String line = null;
			while ((line = iniReader.readLine()) != null){
				System.out.println(line);
			}
		} 	
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	
	public static void writeFile(){
		try {
			File iniFile = new File("onmsconfig.ini");
			if (iniFile.exists()){
				BufferedReader iniReader = new BufferedReader(new FileReader("onmsconfig.ini"));
				String line = null;
				while ((line = iniReader.readLine()) != null){
					System.out.println(line);
				}
			} else{
				iniFile.createNewFile();
			}
			FileWriter fw = new FileWriter(iniFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("URL --> " + urlFld.getText()+"\n");
			bw.write("CRED --> " + unameFld.getText() +":"+passFld.getText()+"\n");
			bw.write("PROXY --> " + proxyFld.getText()+":"+proxyportFld.getText());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void  loginFld(){
		JPanel loginPanel = new JPanel();
	    JPanel loginPanel1 = new JPanel();     
	    JPanel mainPanel = new JPanel();
      
      mainPanel.add(loginPanel);
      mainPanel.add(loginPanel1);
//      proxyFld.setLocation(100, 100);
     

      loginPanel.add(new JLabel("URL"));
      loginPanel.add(urlFld);
   //   loginPanel.add(Box.createHorizontalStrut(15)); // a spacer
 //     loginPanel.add(Box.createVerticalStrut(5));
 //     loginPanel.add(new JLabel("Port"));
 //     loginPanel.add(portFld);


      loginPanel.add(new JLabel("UserName"));
      loginPanel.add(unameFld);
   //   loginPanel.add(Box.createHorizontalStrut(15)); // a spacer
      loginPanel.add(Box.createVerticalStrut(5));
      loginPanel.add(new JLabel("Password"));
      loginPanel.add(passFld);

      //JPanel loginPanel = new JPanel();
      loginPanel.add(new JLabel("Proxy"));
      loginPanel.add(proxyFld);
   //   loginPanel.add(Box.createHorizontalStrut(15)); // a spacer
      loginPanel.add(Box.createVerticalStrut(5));
      loginPanel.add(new JLabel("Port"));
      loginPanel.add(proxyportFld);
      loginPanel.isOptimizedDrawingEnabled();
      loginPanel.setBounds(0, 0, 20, 20);
      loginPanel.setOpaque(false);
      loginPanel1.setOpaque(false);
      loginPanel1.isOptimizedDrawingEnabled();
      loginPanel1.setBounds(20, 20, 200, 200);
     int result = JOptionPane.showConfirmDialog(null, mainPanel,
               "Please Enter Login Details", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
         System.out.println("x value: " + urlFld.getText());
         System.out.println("y value: " + proxyFld.getText());
         writeFile();
         
      }
      
   }

}