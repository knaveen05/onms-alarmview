
import javax.swing.*;
import javax.swing.RowSorter.SortKey;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class alarmView
{
	protected static final Event E = null;
	static String[][] alarms;// = new String[];
    JButton[][] button; //names the grid of buttons
    static JPanel jpl = new JPanel(true);
    static JPanel filt = new JPanel(true);
    JPopupMenu menu = new JPopupMenu("Popup");
    static List<? extends SortKey> sortkey;
    static JTextField filterText = new JTextField("");
    static JButton loadFilter = new JButton("Filters");
    static JLabel filterLabel = new JLabel("Filter, && for AND, || for OR, ! for NOT");
    static TextArea alarm = new TextArea("Alarm");
    static Dimension D = Toolkit.getDefaultToolkit().getScreenSize();
    static 	boolean b = false;
	static JTextField urlFld = new JTextField(20);
	static JTextField unameFld = new JTextField();
	static JTextField passFld = new JPasswordField();
	static JTextField proxyFld = new JTextField();
	static JTextField proxyportFld = new JTextField();
	static String authString = "" ;//"demo:demo";\
	static boolean useProxy = false;
//    static Dimension s = null;
//    static  JButton filtButton = new JButton("Filter");

 //   static int sortkey = 0;
    /* alarm definition
    0 --> Alarm ID
    1 --> Severity
    2 --> Count
    3 --> Description
    4 --> First Occurance
    5 --> Last Occurance
    6 --> Node ID
    7 --> Node Label
    8 --> IP Address
    9 --> Host Name
    10 --> Service Name
    */

	
    
   public alarmView(){   

		System.out.println("Test function");
   }
   public static void rePaint(){
		  String[] col = {"Alarm ID", "Severity", "Count", "Description", "First Occurance", "Last Occurance", "Node ID", "Node Label", "IP Address", "Host Name", "Service Name", "Ack By"};	  
			DefaultTableModel model = new DefaultTableModel(alarms, col)
			{
				public Class getColumnClass(int column)
				{
					return getValueAt(0, column).getClass();
				}
			}; 
			jpl.removeAll();
			jpl.add("Data", createData(model));
			jpl.setPreferredSize(D);
//			jpl.revalidate();
//			jpl.repaint();

			if (b == false) {
			D=jpl.getPreferredSize();
			b=true;
			}else {
			D=jpl.getSize();
			}
			System.out.println("resized: " + D);
   }
   public static void paint(){ 
	//   jpl.removeAll();
	rePaint();
		jpl.addComponentListener(new ComponentListener(){
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			jpl.removeAll();
			rePaint();
			jpl.revalidate();
			jpl.repaint();
			
			
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        jpl.revalidate();
	//	jpl.scrollRectToVisible(new Rectangle(400,725));
		jpl.repaint();
		
		System.out.println("From paint");
   }

	private static JComponent createData(DefaultTableModel model)
	{

		final JTable table = new JTable( model )
		{
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{ 
			final	Component c = super.prepareRenderer(renderer, row, column);
				
				//  Color row based on a cell value

				if (!isRowSelected(row))
				{
					c.setBackground(getBackground());
					int modelRow = convertRowIndexToModel(row);
					String type = (String)getModel().getValueAt(modelRow, 1);
					String ack =  (String)getModel().getValueAt(modelRow, 11);
					String time = (String)getModel().getValueAt(modelRow, 10);
					if ("CRITICAL".equals(type)) c.setBackground(Color.decode("#CC0000"));
					if ("MAJOR".equals(type)) c.setBackground(Color.decode("#FF3300"));
					if ("MINOR".equals(type)) c.setBackground(Color.decode("#FF9900"));
					if ("WARNING".equals(type)) c.setBackground(Color.decode("#FFCC00"));
					if ("INDETERMINATE".equals(type)) c.setBackground(Color.decode("#999000"));
					if ("NORMAL".equals(type)) c.setBackground(Color.decode("#336600"));
					if ("CLEARED".equals(type)) c.setBackground(Color.decode("#999999"));
			//		if (ack == null) c.setForeground(Color.decode("#ffffff"));
					if (ack == " ") c.setFont(new Font("Ariel", Font.BOLD, 11));
					if (ack != " ") c.setFont(new Font("Ariel", Font.PLAIN, 12)) ;
					
				}
				if (isRowSelected(row)){
					int modelRow = convertRowIndexToModel(row);
					c.setBackground(Color.decode("#ffffff"));
					System.out.print((String)getModel().getValueAt(modelRow, 0) + "   ");
					alarm.setText((String)getModel().getValueAt(modelRow, 3));
					
				this.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent evt){
						if (evt.getButton() == MouseEvent.BUTTON3) {
						System.out.print("Right Clicked");
						}
					}
				});
				}
				
				
//				c.setPreferredSize(new Dimension(1325,725));
				return c;
			}  
		};
		
		//Dimension TSize = null;
	//	TSize= D;
		D.width = D.width - 25;
		D.height = D.height - 100;
		table.getTableHeader().setToolTipText("Click to sort");
		table.removeColumn(table.getColumnModel().getColumn(11));
		table.getColumnModel().getColumn(4).setWidth(1000);
		table.setPreferredScrollableViewportSize(D);
//		System.out.println("Table Size: " + table.getPreferredScrollableViewportSize() + " D is: " + D);
        table.getColumnModel().setColumnMargin(2);
        table.getColumnModel().getColumn(0).setPreferredWidth(D.width/25);
        table.getColumnModel().getColumn(1).setPreferredWidth(D.width/20);
        table.getColumnModel().getColumn(2).setPreferredWidth(D.width/38);
        table.getColumnModel().getColumn(3).setPreferredWidth(2*D.width/5);
        table.getColumnModel().getColumn(4).setPreferredWidth(D.width/10);
        table.getColumnModel().getColumn(5).setPreferredWidth(D.width/10);    
        table.getColumnModel().getColumn(6).setPreferredWidth(D.width/40);
        table.getColumnModel().getColumn(7).setPreferredWidth(D.width/15);
        table.getColumnModel().getColumn(8).setPreferredWidth(D.width/15);
        table.getColumnModel().getColumn(9).setPreferredWidth(D.width/15);
        table.getColumnModel().getColumn(10).setPreferredWidth(D.width/15);
        
       final TableRowSorter<TableModel> sorter =new TableRowSorter<TableModel>(model);

        String text = filterText.getText();
   	////Starts Filter ////////	           		
   		        		if (text.length() == 0 ) {
   		        			sorter.setRowFilter(null);
   		        			table.setRowSorter(sorter);
   		        		}else {	        			
   		 					

   		 					RowFilter<TableModel, Object> rf = null;
   		 					List<RowFilter<Object,Object>> rf1 = new ArrayList<RowFilter<Object,Object>>();
   		 					try {
   		 						String[] textArray1 = text.split(" && ");	
   		 					    for (int j=0; j<textArray1.length; j++)
   		 					    {	List<RowFilter<Object,Object>> rfs = new ArrayList<RowFilter<Object,Object>>();
   			 					
   		 					    	String[] textArray = textArray1[j].split(" \\|\\| ");
   		 					    for (int i = 0; i < textArray.length; i++) {
   		 					        if (textArray[i].startsWith("!")) { 
   		 					        	textArray[i] = textArray[i].substring(1);
   		 					        	rfs.add(RowFilter.notFilter(RowFilter.regexFilter(textArray[i]))); 
   		 					        } else {
   		 					    	rfs.add(RowFilter.regexFilter( textArray[i]));
   		 					        }
   		 					    }
   		 					    rf1.add(RowFilter.orFilter(rfs));
   		 					}	
   		 					    rf = RowFilter.andFilter(rf1);
   		 					} catch (java.util.regex.PatternSyntaxException e) {
 //  		 					        return;
   		 					}

   		 					sorter.setRowFilter(rf);
   	 	 					table.setRowSorter(sorter);
   		
   	 	 					}
   	//// Ends Filter   ///////*/
      
        table.getRowSorter().addRowSorterListener(new RowSorterListener() {
        	@Override
        	public void sorterChanged(RowSorterEvent rsevent) {
        		sortkey = 	rsevent.getSource().getSortKeys();

        	}
        });
        
        
        TableColumnModelListener collListener = new TableColumnModelListener(){
        	
			@Override
			public void columnAdded(TableColumnModelEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Column Added");
			}

			@Override
			public void columnMarginChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
			System.out.println("Margin Change");	
			}

			@Override
			public void columnMoved(TableColumnModelEvent arg0) {
				// TODO Auto-generated method stub
			
				System.out.println("Column Moved");
			}

			@Override
			public void columnRemoved(TableColumnModelEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void columnSelectionChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        };
       KeyListener keyListener = new KeyListener() {
	            public void keyPressed(KeyEvent keyEvent) {
//	            	System.out.println("Pressed");
	            }

	            public void keyReleased(KeyEvent keyEvent) {
//	            	System.out.println("Released");
	           		String text = filterText.getText();
////Starts Filter ////////	           		
	        		if (text.length() == 0 ) {
	        			sorter.setRowFilter(null);
	        			table.setRowSorter(sorter);
	        		}else {	        			
	 					

	 					RowFilter<TableModel, Object> rf = null;
	 					List<RowFilter<Object,Object>> rf1 = new ArrayList<RowFilter<Object,Object>>();
	 					try {
	 						String[] textArray1 = text.split(" && ");	
	 					    for (int j=0; j<textArray1.length; j++)
	 					    {	List<RowFilter<Object,Object>> rfs = new ArrayList<RowFilter<Object,Object>>();
		 					
	 					    	String[] textArray = textArray1[j].split(" \\|\\| ");
	 					    for (int i = 0; i < textArray.length; i++) {
	 					        if (textArray[i].startsWith("!")) { 
	 					        	textArray[i] = textArray[i].substring(1);
	 					        	rfs.add(RowFilter.notFilter(RowFilter.regexFilter(textArray[i]))); 
	 					        } else {
	 					    	rfs.add(RowFilter.regexFilter( textArray[i]));
	 					        }
	 					    }
	 					    rf1.add(RowFilter.orFilter(rfs));
	 					}	
	 					    rf = RowFilter.andFilter(rf1);
	 					} catch (java.util.regex.PatternSyntaxException e) {
	 					        return;
	 					}

	 					sorter.setRowFilter(rf);
 	 					table.setRowSorter(sorter);
	
 	 					}
//// Ends Filter   ///////*/	            	
	            }

	            public void keyTyped(KeyEvent keyEvent) {
//	            	System.out.println("Typed");
	            } 
	        };
 
        filterText.addKeyListener(keyListener);
//        table.setRowSorter(sorter);
        table.getRowSorter().setSortKeys(sortkey);
		return new JScrollPane( table );
	}
	
	public static TableRowSorter<TableModel> tablefilter(TableRowSorter<TableModel> sorter , JTable table){
	    
		String text = filterText.getText();
		if (text.length() == 0 ) {
			sorter.setRowFilter(null);
			table.setRowSorter(sorter);
//			System.out.println("Blank field ");
		}else {
			
				sorter.setRowFilter(RowFilter.regexFilter(text));

			}
		return sorter;
	}
	
   public static void main (String[] args) throws Exception {
	   int i = 0;
	   loginFld();
       EventQueue.invokeLater(new Runnable() {
    	 
           @Override
           public void run() {
        	  new alarmView();
	   try {
		login();
		paint();

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Not working, Check Your Login Credential/Proxy connection");
		System.exit(1);
		
	}
		 	JFrame.setDefaultLookAndFeelDecorated(true);
		 	JFrame frame = new JFrame("OpenNMS alarmView \"Alarms Console\"");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    filterText.setPreferredSize(new Dimension(400,20));
		    filterText.setToolTipText("&& for AND, || for OR ! for NOT");
	//	    filterText.setBounds(5,5,100,100);
		    filt.setPreferredSize(new Dimension(D.width-25,80));
//		    alarm.setAutoscrolls(true);
		    alarm.setPreferredSize(new Dimension(D.width-25, 40));
		    filterLabel.setToolTipText("&& for AND, || for OR ! for NOT");
		    filt.add(filterLabel, BorderLayout.NORTH);
		    filt.add(filterText, BorderLayout.NORTH);
		    filt.add(loadFilter);
		    filt.add(alarm, BorderLayout.SOUTH);
	//	    filt.add(filtButton);
		    frame.add(filt, BorderLayout.NORTH);
		    frame.add(jpl);
		    frame.pack();
		 	frame.setLocationRelativeTo(jpl);
		 	frame.setVisible(true);
	}
       });

       while (i == 0){
    	   Thread.sleep(10000);
    	   try {
    		login();
    		paint();
    	   } catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			JOptionPane.showMessageDialog(null, "Not connected");
    		} 
       }
   }
   
   
   
private static void login() throws Exception {
	// TODO Auto-generated method stub

//	String authString = loginFld.unameFld+ ":" + loginFld.passFld;

	//String authString = name + ":" + password;
	String inputLine;
	String inputLine1="";
	byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
	String authStringEnc = new String(authEncBytes);
	System.out.println("Base64 encoded auth string: " + authStringEnc);
	
	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyFld.getText(), Integer.parseInt(proxyportFld.getText())));
	URL onms = new URL(urlFld.getText()+"rest/alarms?limit=0");
//	URL onms = new URL("http://demo.opennms.org/opennms/rest/alarms?comparator=ge&severity=WARNING&limit=0");
	URLConnection gc ;
	if (useProxy == true) {
		gc = onms.openConnection(proxy);
		System.out.println("Yes Proxy");
	} else {
	gc = onms.openConnection();
	System.out.println("No proxy");
	}
	gc.setRequestProperty("authorization", "Basic " );
	gc.setRequestProperty( "Content-type", "text/xml" );
	gc.setRequestProperty( "application", "text/xml" );
	gc.setRequestProperty("Authorization", "Basic " + authStringEnc);
	//Map headerFields = gc.getHeaderFields();
	//System.out.println("header fields are: " + headerFields);

	BufferedReader in = new BufferedReader(
				   		new InputStreamReader(
						gc.getInputStream()
				   		));
	inputLine1=in.readLine();
	 	   while ((inputLine = in.readLine()) != null) {
			   inputLine1=inputLine1+"\n"+inputLine;
		   }
	 	   System.out.println(inputLine1);
//	 	   return inputLine1;
	 	  xmltoArray(inputLine1);
//		 	paint();  
//	return inputLine1;
}
   public static void xmltoArray(String Line){
    
  	try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(Line.getBytes("UTF-8"))));
		 	doc.getDocumentElement().normalize();
			
//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("alarm");
		   	String[][] alarm =new String[nList.getLength()][12];	
//			System.out.println("Alarm Count : " + nList.getLength());//   eElement.getAttribute("count"));
//			System.out.println("----------------------------");
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
//				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
					Element eElement = (Element) nNode;
					alarm[temp][0]=eElement.getAttribute("id");
					alarm[temp][1]=eElement.getAttribute("severity");
					alarm[temp][2]=eElement.getAttribute("count");
					if (eElement.getElementsByTagName("description").item(0) != null) {
						alarm[temp][3]=eElement.getElementsByTagName("description").item(0).getTextContent(); 
						}
					if (eElement.getElementsByTagName("firstEventTime").item(0) != null) {
						alarm[temp][4]=eElement.getElementsByTagName("firstEventTime").item(0).getTextContent();
					}
					if (eElement.getElementsByTagName("lastEventTime").item(0) != null) {
						alarm[temp][5]=eElement.getElementsByTagName("lastEventTime").item(0).getTextContent();
					}
					if (eElement.getElementsByTagName("nodeId").item(0) != null) {
						alarm[temp][6]=eElement.getElementsByTagName("nodeId").item(0).getTextContent();
					}
					if (eElement.getElementsByTagName("nodeLabel").item(0) != null) {
						alarm[temp][7]=eElement.getElementsByTagName("nodeLabel").item(0).getTextContent();
					}
					if (eElement.getElementsByTagName("ipAddress").item(0) != null) {
						alarm[temp][8]=eElement.getElementsByTagName("ipAddress").item(0).getTextContent();
					}
						alarm[temp][9]=eElement.getElementsByTagName("host").item(0).getTextContent();
					if (eElement.getElementsByTagName("ipAddress").item(0) != null) {
						alarm[temp][10]=eElement.getElementsByTagName("ipAddress").item(0).getTextContent();
					}
					if (eElement.getElementsByTagName("ackUser").item(0) != null) {
						alarm[temp][11]=eElement.getElementsByTagName("ackUser").item(0).getTextContent();
					}
 for (int i=0;i<12;i++){
	if (alarm[temp][i] == null){
		alarm[temp][i] = " ";
	} 
//	System.out.print(alarm[temp][i]+", ");
}
//System.out.println(" ");  
				}
			}
		    alarms=alarm;
		    } catch (Exception e) {
			e.printStackTrace();

		    } 
   } 
	public static void readFile(){
	      urlFld.setText("http://");
		try{
			File iniFile = new File("onmsconfig.ini");
			if (iniFile.exists()){
				BufferedReader iniReader = new BufferedReader(new FileReader("onmsconfig.ini"));
				String line = null;

				while ((line = iniReader.readLine()) != null){
		//			System.out.println(line);
					if(line.contains("URL --> ") && line != null ) urlFld.setText(line.replaceFirst("URL --> ", ""));
					if(line.contains("CRED --> ")) { 
						
//						line.replaceFirst("CRED --> ", "");
						String[] login = line.split(":");
						
						if (login[0] != null ) unameFld.setText(login[0].replaceFirst("CRED --> ", ""));
						if (login.length == 2 ) passFld.setText(login[1]);
						System.out.println ("Login length is: " + login.length);
					} else {
						System.out.println("Login length is 1");
					}
					if(line.contains("PROXY --> ")) {
						proxyFld.setText(line.replaceFirst("PROXY --> ", ""));
					}
					if(line.contains("PROXYPORT --> ")) {
						proxyportFld.setText(line.replaceFirst("PROXYPORT --> ", ""));
					}
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
				bw.write("PROXY --> " + proxyFld.getText()+"\n");
				bw.write("PROXYPORT --> " + proxyportFld.getText());
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private static void  loginFld(){
			readFile();
			JFrame.setDefaultLookAndFeelDecorated(true);
			JFrame loginFrame = new JFrame("Login Form ");
			
			loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       
	        JPanel loginPanel = new JPanel();
	      loginPanel.setLayout(new GridLayout(0,2));
	      loginPanel.add(new JLabel("URL"), BorderLayout.EAST);
	      loginPanel.add(urlFld);
	      
	      loginPanel.add(new JLabel("UserName"));
	      loginPanel.add(unameFld);
	      
	      loginPanel.add(new JLabel("Password"));
	      loginPanel.add(passFld);
	      loginPanel.add(new JLabel("Proxy"));
	      loginPanel.add(proxyFld);
	      loginPanel.add(new JLabel("Proxy Port"));
	      loginPanel.add(proxyportFld);
//	      loginPanel.isOptimizedDrawingEnabled();
//	      loginPanel.setOpaque(true);
 
	     int result = JOptionPane.showConfirmDialog(null, loginPanel,
	               "OpenNMS alarmView \"Please Enter Login Details\"", JOptionPane.OK_CANCEL_OPTION);
//	     loginFrame.add(loginPanel);
//	     loginFrame.pack();
//		 loginFrame.setVisible(true);
		 
	      if (result == JOptionPane.OK_OPTION) {
	      authString = unameFld.getText() + ":" + passFld.getText();
	      System.out.println("Auth str :" + authString);
	      if (!proxyFld.getText().toString().equals("")) {
	    	  System.out.println("Use yes proxy:" + proxyFld.getText()+":");
		    	useProxy = true;
		    	if((proxyportFld.getText().equals(""))){
		    		JOptionPane.showMessageDialog(null, "Provide port for proxy!!!!");
		    		System.exit(1);
		    	} else {
		    		try{
		    			  int num = Integer.parseInt(proxyportFld.getText());
		    			  // is an integer!
		    			} catch (NumberFormatException e) {
		    			  // not an integer!
		    				JOptionPane.showMessageDialog(null, "Non Integer port number!!!!");
		    				System.exit(1);
		    			}
		    	}
	      }
	      writeFile();
	      }

	      if (result == JOptionPane.CANCEL_OPTION){
	    	  System.exit(0);
	      }
	      
	   }   
}
