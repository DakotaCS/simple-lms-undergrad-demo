//I declare that this project is my own work and that all material previously written or published in any source by any other person has been duly acknowledged in the assignment. 
//I have not submitted this work, or a significant part thereof, previously as part of any academic program. 
//In submitting this assignment I give permission to copy it for assessment purposes only.
//Dakota C. Soares

//: src/libra_lms_main

/***********************************************************************
 * Assignment3, COMP482
 * Class: libra_lms_main.java
 * 
 * Purpose: This contains the main() method that allows a user to run the main Libra LM Software program. This is a prototype, and some 
 * implementation is missing - see report for details. 
 * 
 * @author: Dakota Soares, and other sources. Sources are appropriately referenced in-line. 
 * 
 * Student ID: 3318342
 * @date: February 26th, 2021 
 * 
 * Notes: see included report for test cases, parameters, etc. 
 * 
 */


//reference import - see lib/pdfbox
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
//javax swing packages
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
//java awt, io, and util packages
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Container; 
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

//main class
@SuppressWarnings("serial")
public class libra_lms_main extends JFrame implements MouseListener
{
	//any objects on the GUI that are accessed universally are below:
	JMenu mnEdit = new JMenu("Edit");
	JButton btnAddItem = new JButton("Add Item");
	JButton btnDelItem = new JButton("Delete Item");
	JButton btnAddPatron = new JButton("Add Patron");
	JButton btnDeletePatron = new JButton("Delete Patron");
	private JFrame frmLibraLms;
	private JTextField textfieldPIL;
	JTextArea textAreaLML = new JTextArea();
	JTextArea textAreaPIL = new JTextArea();
	JTextField textfieldLML = new JTextField();
	JTextArea searchLML = new JTextArea();
	JTextArea searchPIL = new JTextArea();
	//action that allows the user to select a line in the JtextAreas
	Action selectLine; 
	//file for the open/save dialogs
	@SuppressWarnings("unused")
	private File file = null;
	//setup the jfile chooser
    final JFileChooser fileChooser = new JFileChooser();
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		//run the program on a thread - 
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					//create the window
					libra_lms_main window = new libra_lms_main();
					window.frmLibraLms.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	

	/** 
	*  Initializes the lms object
	*  @param 
	*  @return 
	*  @throws 
	*/
	public libra_lms_main() 
	{
		//initialize the application
		initialize();
		//start the mouse listener for each text area:
		textAreaLML.addMouseListener(this);
		selectLine = getAction(DefaultEditorKit.selectLineAction);
		textAreaPIL.addMouseListener(this);
		selectLine = getAction(DefaultEditorKit.selectLineAction); 
	}
	
	/** 
	*  Gets the object Action
	*  @param name
	*  @return Returns an Action object. 
	*  @throws
	*/
    private Action getAction(String name)
    {
    	//set the action to null
        Action action = null;
        //create an arry of actions and get them from the text area
        Action[] actions = textAreaLML.getActions();
        //for every action in the array....
        for (int i = 0; i < actions.length; i++)
        {
        	//if the names are equal
            if (name.equals( actions[i].getValue(Action.NAME).toString() ) )
            {
            	//set them equal to each other and break
                action = actions[i];
                break;
            }
        }
        //return the action
        return action;
    }
    
	/** 
	*  Mouse click listener 
	*  @param MouseEvent
	*  @return 
	*  @throws 
	*/
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		//if the left mouse button is clicked once, trigger action performed:
		if ( SwingUtilities.isLeftMouseButton(e)  && e.getClickCount() == 1)
        {
            selectLine.actionPerformed( null );
        }
	}
	
	/** 
	*  Sorts an Arraylist of Material items
	*  @param 
	*  @return 
	*  @throws 
	*/
	public void autoSort()
	{
		try {
		//create an array list of mItem - 
		ArrayList<mItem> items = new ArrayList<mItem>(); 
		//for every line in the text area... 
		for (String line : textAreaLML.getText().split("\\n"))
		{	
			//create an array of objects split by comma
			String[] vals = line.split(", ");
			String call_id = vals[0]; 
			String title = vals[1]; 
			String author = vals[2]; 
			String year = vals[3]; 
			String quantity = vals[4];
			int call_id_no = 0; 
			int year_no = 0; 
			int quantity_no = 0; 
			//parse the integer values
			try
			{
				call_id_no = Integer.parseInt(call_id); 
				year_no = Integer.parseInt(year); 
				quantity_no = Integer.parseInt(quantity); 
			}
			//in the actual implementation we'd write the error to a log file
			//for now just print out a cast conversion error message: 
			catch(Exception e1)
			{
				System.out.println("Cast Error - try conversion again"); 
			}
			//add the item to the ArrayList
			items.add(new mItem(call_id_no, title, author, year_no, quantity_no)); 
		}
		//we can then sort the items
		Collections.sort(items);
		//empty the search area and text area
		searchLML.setText(""); 
		textAreaLML.setText("");
		//for every item in the array
		for (mItem m : items)
		{
			//append it to the textArea material's list
			textAreaLML.append(m.toString() + "\n");
		}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Nothing to sort.");
		}
	}
	
	/** 
	*  Sort an ArrayList of Patron items
	*  @param 
	*  @return  
	*  @throws 
	*/
	public void autoSortPatron()
	{
		try {
		//create an array list of pItem
		ArrayList<pItem> items = new ArrayList<pItem>(); 
		//for every line in the text area...
		for (String line : textAreaPIL.getText().split("\\n"))
		{	
			//create an array of objects split by comma
			String[] vals = line.split(", ");
			String patron_id = vals[0]; 
			String name = vals[1]; 
			String address = vals[2]; 
			String email = vals[3]; 
			String phone = vals[4];
			String book = vals[5]; 
			String return_date = vals[6]; 
			int patron_id_no = 0;  
			int book_id = 0; 
			//parse the integer values 
			try
			{
				patron_id_no = Integer.parseInt(patron_id); 
				book_id = Integer.parseInt(book); 
			}
			//in the actual implementation we'd write the error to a log file
			//for now just print out a cast conversion error message: 
			catch(Exception e1)
			{
				System.out.println("Cast Error - try conversion again"); 
			}
			//add the item to the ArrayList
			items.add(new pItem(patron_id_no, name, address, email, phone, book_id, return_date)); 
		}
		//we can then sort the items
		Collections.sort(items);
		//empty the search area and text area
		searchPIL.setText(""); 
		textAreaPIL.setText("");
		//for every pItem
		for (pItem m : items)
		{
			//append it to the text area patron's list
			textAreaPIL.append(m.toString() + "\n");
		}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Nothing to sort.");
		}
		
	}
	

	/** 
	*  Initialize the frame
	*  @param 
	*  @return  
	*  @throws 
	*/
	private void initialize() 
	{
		//create a new frame
		frmLibraLms = new JFrame();
		frmLibraLms.setResizable(false);
		//set the title
		frmLibraLms.setTitle("Libra Software LMS");
		//set the bounds of the frame
		frmLibraLms.setBounds(100, 100, 1349, 714);
		//set the default close operation
		frmLibraLms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//create the menu bar
		JMenuBar menuBar = new JMenuBar();
		frmLibraLms.setJMenuBar(menuBar);
		//add a menu item
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		//create a jmenu item...
		JMenuItem mntmOpenPatronList = new JMenuItem("Open Patron List");
		//set the mnemonic to "O"
		mntmOpenPatronList.setMnemonic(KeyEvent.VK_O);
		//set the keystoke so that the actionlistener will fire when CTRL+O is pressed
		KeyStroke keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		mntmOpenPatronList.setAccelerator(keyStrokeToOpen);
		//add the open patron action listener
		mntmOpenPatronList.addActionListener(new ActionListener() 
		{	
			public void actionPerformed(ActionEvent e) 
			{
				//create a file chooser
				JFileChooser chooser = new JFileChooser();
				//show the open dialog
				int returnVal = chooser.showOpenDialog(null); 
				//create a file
				File file = null;
				//if the return value is approved then get the selected file
				if(returnVal == JFileChooser.APPROVE_OPTION)     
				  file = chooser.getSelectedFile();    
					try 
					{
					//create the buffered reader
					@SuppressWarnings("resource")
					BufferedReader in = new BufferedReader(new FileReader(file));
					//read the line from the buffered reader
					String line = in.readLine(); 
						//while the line is null
						while(line != null)
						{
							//the following lines check to make sure the formatting is correct in the file:
							//set the count
							int count = 0;
							//for every character in the line
							for (int i = 0; i < line.length(); i++)
							{
								//if the character is a comma
								if (line.charAt(i) == ',')
								{
									//increase the count
									count++; 
								}
								//if the count is greater then six commas, throw an exception
								if (count > 6)
								{
									throw new IOException(); 
								}
							}
							//otherwise append it to the text area
							textAreaPIL.append(line + "\n");
							//read the next line 
							line = in.readLine();
				  
						}
					//close the input
					in.close();
					}
					//catch any errors and display a dialog to the user 
					catch (Exception e1)
					{
						JOptionPane.showMessageDialog(null, "Invalid Patron File.");
					} 
				}
			}
		);
		
		mnFile.add(mntmOpenPatronList);
		//create the open library materials menu item
		JMenuItem mntmOpenLibraryMaterials = new JMenuItem("Open Library Materials List CTRL + 0");
		mntmOpenLibraryMaterials.addActionListener(new ActionListener() 
		{
			//add the open library material list 
			public void actionPerformed(ActionEvent e) 
			{
				//create a file chooser
				JFileChooser chooser = new JFileChooser();
				//show the open dialog
				int returnVal = chooser.showOpenDialog(null); 
				//create a file 
				File file = null;
				//if the value returned is approved 
				if(returnVal == JFileChooser.APPROVE_OPTION)     
				  file = chooser.getSelectedFile();    
				//try the following...
				try 
				{
					@SuppressWarnings("resource")
					//create a buffered reader object
					BufferedReader in = new BufferedReader(new FileReader(file));
					//get the line
					String line = in.readLine(); 
					//while the line is not null
					while(line != null)
					{
						//the following lines check to make sure the formatting is correct in the file:
						//set the count
						int count = 0;
						//for every character in the line
						for (int i = 0; i < line.length(); i++)
						{
							//if the character is a comma...
							if (line.charAt(i) == ',')
							{
								//increase the count
								count++; 
							}
							//if the count is greater then four
							if (count > 4)
							{
								//throw an exception
								throw new IOException(); 
							}
						}
						//otherwise append the result to the text area
						textAreaLML.append(line + "\n");
						//read the next line
						line = in.readLine();
					}
				}
				//catch any exception and print a note to the user
				catch (IOException e1)
				{
					JOptionPane.showMessageDialog(null, "Invalid Materials File.");
					
				}
			}
		});
		mnFile.add(mntmOpenLibraryMaterials);
		//add a jseperator
		JSeparator separator_4 = new JSeparator();
		mnFile.add(separator_4);
		//add a save patron list menu item
		JMenuItem mntmSavePatronList = new JMenuItem("Save Patron List");
		mntmSavePatronList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//call the save dialog
				saveDialog(); 
			}
		});
		mnFile.add(mntmSavePatronList);
		//add a save library menu item
		JMenuItem mntmSaveLibraryMaterials = new JMenuItem("Save Library Materials List");
		mntmSaveLibraryMaterials.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//call the save dialog
				saveDialog(); 
			}
		});
		mnFile.add(mntmSaveLibraryMaterials);
		//add a jseparator
		JSeparator separator_5 = new JSeparator();
		mnFile.add(separator_5);
		//create a print patron list menu item
		JMenuItem mntmPrintPatronList = new JMenuItem("Print Patron List");
		//set the mnemonic to P
		mntmPrintPatronList.setMnemonic(KeyEvent.VK_P);
		//set the keystroke to open the print dialog when CTROL + P is pressed
		KeyStroke keyStrokeToPrint = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);
		mntmPrintPatronList.setAccelerator(keyStrokeToPrint);
		//add an action listener for the print patron list button 
		mntmPrintPatronList.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) 
			{
				//send the text area to the print dialog object
				printDialog mydiag = new printDialog(textAreaLML); 
				//add the text area as a component to the dialog
				printDialog.printComponent(textAreaLML);
			}
		});
		mnFile.add(mntmPrintPatronList);
		//create a print library materials menu item
		JMenuItem mntmPrintLibraryMaterials = new JMenuItem("Print Library Materials List CTRL + >");
		mntmPrintLibraryMaterials.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) 
			{
				//send the text area to the print dialog object
				printDialog mydiag = new printDialog(textAreaLML);
				//add the text area as a component to the dialog
				printDialog.printComponent(textAreaLML);
			}
		});
		mnFile.add(mntmPrintLibraryMaterials);
		//create a jseperator object 
		JSeparator separator_6 = new JSeparator();
		mnFile.add(separator_6);
		//create a new activate self search portal menu item
		JMenuItem mntmActivateSelfsearchPortal = new JMenuItem("Activate Self-Search Portal");
		mntmActivateSelfsearchPortal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//create a new Patron Self-Search frame
				PSSFrame pssFrame = new PSSFrame(); 
				pssFrame.setTitle("Libra Software LMS: Patron Search");
				pssFrame.setVisible(true);
				pssFrame.setAlwaysOnTop(true);
				pssFrame.setResizable(false);
				//this is just an example of some of the modifications to the window to ensure patrons cannot close it. 
				//the window will in reality be only closable using a specific keyboard combination or password dialog
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				pssFrame.setSize(screenSize.width, screenSize.height);
				pssFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); 
			}
		});
		mnFile.add(mntmActivateSelfsearchPortal);
		//create a new activate administrator privilege menu item 
		JMenuItem mntmActivateAdministratorPrivilege = new JMenuItem("Activate Administrator Privileges");
		mntmActivateAdministratorPrivilege.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) 
			{
				//create a new authorization dialog
				AuthorizationFrame AuthFrame = new AuthorizationFrame(); 
				AuthFrame.setTitle("Administrator Verification");
				AuthFrame.setVisible(true);
				AuthFrame.setAlwaysOnTop(true);
				AuthFrame.setBounds(100, 100, 370, 400); 
				AuthFrame.setResizable(false);
			}
		});
		mnFile.add(mntmActivateAdministratorPrivilege);
		//create a new jseperator 
		JSeparator separator_7 = new JSeparator();
		mnFile.add(separator_7);
		
		//create a new export material list menu option
		//this menu option uses an externally referenced library from PDF Box. 
		//implementation was retrieved from: https://zetcode.com/java/pdfbox/
		JMenuItem mntmExportLists = new JMenuItem("Export Library Materials List (to PDF)");
		mntmExportLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//try to make a new PDF document
				try (PDDocument doc = new PDDocument()) 
				{
					//create a new page and addd it to the document
		            PDPage myPage = new PDPage();
		            doc.addPage(myPage);
		            //try the following: 
		            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) 
		            {
		            	//place the text in the page 
		                cont.beginText();
		                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
		                cont.setLeading(14.5f);
		                cont.newLineAtOffset(25, 700);
		                //for every line, add it to a new line of the page
		            	for (String line : textAreaLML.getText().split("\\n"))
		        		{	
		            		cont.showText(line);
		            		cont.newLine();
		        		}

		                cont.endText();
		            }
		            //catch any errors
		            catch(IOException e1)
		            {
		            	System.out.println("Export Failed."); 
		            	e1.printStackTrace();
		            }
		            //save the document to the following location: 
		            doc.save("src/LibraryMaterialListSave.pdf");
		            JOptionPane.showMessageDialog(null, "Export Completed. Saved at: src/LibraryMaterialsListSave.pdf");
		        }
				//catch any errors
				catch (IOException e2)
				{
					System.out.println("Export Failed."); 
	            	e2.printStackTrace();
				}
		    }	
		});
		
		//create a new menu item that exports the patron list 
		//this menu option uses an externally referenced library from PDF Box. 
		//implementation was retrieved from: https://zetcode.com/java/pdfbox/
		JMenuItem mntmExportPatronInfo = new JMenuItem("Export Patron Info List (to PDF)");
		mntmExportPatronInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//try to make a new PDF document
				try (PDDocument doc = new PDDocument()) 
				{
					//create a new page and addd it to the document
		            PDPage myPage = new PDPage();
		            doc.addPage(myPage);
		            //try the following: 
		            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) 
		            {
		            	//place the text in the page 
		                cont.beginText();
		                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
		                cont.setLeading(14.5f);
		                cont.newLineAtOffset(25, 700);
		                //for every line, add it to a new line of the page
		            	for (String line : textAreaLML.getText().split("\\n"))
		        		{	
		            		cont.showText(line);
		            		cont.newLine();
		        		}

		                cont.endText();
		            }
		            //catch any errors
		            catch(IOException e1)
		            {
		            	System.out.println("Export Failed."); 
		            	e1.printStackTrace();
		            }
		            //save the document to the following location: 
		            doc.save("src\\LibraryPatronListSave.pdf");
		        }
				//catch any errors
				catch (IOException e2)
				{
					System.out.println("Export Failed."); 
	            	e2.printStackTrace();
				}
			}
		});
		mnFile.add(mntmExportPatronInfo);
		mnFile.add(mntmExportLists);
		
		//create a new exit menu item
		JMenuItem mntmExitCtrl = new JMenuItem("Exit CTRL + Q");
		mntmExitCtrl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) 
			{
				//quit the program normally
				System.exit(0);
			}
		});
		//create a new jseparator object
		JSeparator separator_9 = new JSeparator();
		mnFile.add(separator_9);
		mnFile.add(mntmExitCtrl);
		//create a new jseparator object
		JSeparator separator_8 = new JSeparator();
		mnFile.add(separator_8);
		//set the edit menu item initially to not be available (admin privileges  needed)
		mnEdit.setEnabled(false);
		menuBar.add(mnEdit);
		
		//the following buttons correspond to the "Edit" menu - the implemntation has been left out for this
		//prototype - the operations are exactly the same as the buttons in the main Jframe dialog. 
		JMenuItem mntmAddItem = new JMenuItem("Add Item");
		mnEdit.add(mntmAddItem);
		
		JMenuItem mntmAddPatron = new JMenuItem("Add Patron");
		mnEdit.add(mntmAddPatron);
		//create a new jseparator object
		JSeparator separator_2 = new JSeparator();
		mnEdit.add(separator_2);
		
		JMenuItem mntmDeleteItem = new JMenuItem("Delete Item");
		mnEdit.add(mntmDeleteItem);
		
		JMenuItem mntmDeletePatron = new JMenuItem("Delete Patron");
		mnEdit.add(mntmDeletePatron);
		//create a new jseparator object
		JSeparator separator_3 = new JSeparator();
		mnEdit.add(separator_3);
		
		//create a new edit materials item menu item
		JMenuItem mntmUpdateDatabaseInformation = new JMenuItem("Edit Materials Item");
		mntmUpdateDatabaseInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//get the selected text from the textarea
				String selected = textAreaLML.getSelectedText(); 
				//show the edit material pane dialog 
				JEditMaterialPane materialPane = new JEditMaterialPane(); 
				materialPane.display(selected);
				
			}
		});
		mnEdit.add(mntmUpdateDatabaseInformation);
		
		//create a new edit patron item menu item
		JMenuItem mntmEditPatronItem = new JMenuItem("Edit Patron Item");
		mntmEditPatronItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//get the selected text from the textarea
				String selected = textAreaPIL.getSelectedText(); 
				//show the edit patron pane dialog 
				JEditPatronPane patronPane = new JEditPatronPane(); 
				patronPane.display(selected);
				patronPane.setVisible(true);
			}
		});
		mnEdit.add(mntmEditPatronItem);
		//add a new menu option
		JMenu mnAdvancedMaterialsOptions = new JMenu("Advanced Materials Options");
		menuBar.add(mnAdvancedMaterialsOptions);
		
		//create a new search by call menu item 
		JMenuItem mntmSearchbyCall = new JMenuItem("Search (by Call Number)");
		//set the mnemonic to Y
		mntmSearchbyCall.setMnemonic(KeyEvent.VK_Y);
		//set the keystroke to open the search dialog
		KeyStroke keyStrokeToSearch = KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK);
		mntmSearchbyCall.setAccelerator(keyStrokeToSearch);
		//add the action listener to the search by call button
		mntmSearchbyCall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//show an input dialog and get the string as input
	            String result = (String)JOptionPane.showInputDialog
	            		(null,"Enter a Call Number to Search:", "Advanced Search: Call Number",JOptionPane.PLAIN_MESSAGE);
	            //string to check to ensure the text area is not empty
	            String check = textAreaLML.getText().trim();
	            //if the result is not null and the text area is not null
	            if (result != null && result.length() > 0 && !check.equals(""))
	            {
	            	//reset the search text area
	            	searchLML.setText("");
	            	//for every line in the text area
					for (String line : textAreaLML.getText().split("\\n"))
					{	
						//get the call id by splitting the line
						String[] vals = line.split(", ");
						String call_id = vals[0]; 
						if (call_id.equals(result))
						{
							//if a match is found then append it to the search text area
							searchLML.append(line + "\n");
						}
					}
	            }
	            else
	            {
	            	//otherwise nothing was found
	            	JOptionPane.showMessageDialog(null, "No library material list loaded, or no Call Number entered.", null, JOptionPane.INFORMATION_MESSAGE);
	            }
			}
		});
		mnAdvancedMaterialsOptions.add(mntmSearchbyCall);
		//create a new menu item to search by year 
		JMenuItem mntmSearchbyYear = new JMenuItem("Search (by Year)");
		mntmSearchbyYear.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//show an input dialog and get the string as input
	            String result = (String)JOptionPane.showInputDialog
	            		(null,"Enter a Year to Search:", "Advanced Search: Year",JOptionPane.PLAIN_MESSAGE);
	            //string to check to ensure the text area is not empty
	            String check = textAreaLML.getText().trim();
	            //if the result is not null and the text area is not null
	            if (result != null && result.length() > 0 && !check.equals(""))
	            {
	            	//reset the search text area
	            	searchLML.setText("");
	            	//for every line in the text area
					for (String line : textAreaLML.getText().split("\\n"))
					{	
						//get the year by splitting the line
						String[] vals = line.split(", ");
						String call_id = vals[3]; 
						if (call_id.equals(result))
						{
							//if a match is found append the result to the search text area
							searchLML.append(line + "\n");
						}
					}
	            }
	            else
	            {
	            	//otherwise nothing was found
	            	JOptionPane.showMessageDialog(null, "No library material list loaded, or no Call Number entered.", null, JOptionPane.INFORMATION_MESSAGE);
	            }
			}
		});
		mnAdvancedMaterialsOptions.add(mntmSearchbyYear);
		//create a new menu item to search by quantity
		JMenuItem mntmSearchbyQuantity = new JMenuItem("Search (by Quantity)");
		mntmSearchbyQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//show an input dialog and get the string as input
				String result = (String)JOptionPane.showInputDialog
	            		(null,"Enter a Quantity to Search:", "Advanced Search: Quantity",JOptionPane.PLAIN_MESSAGE);
				//string to check to ensure the text area is not empty
	            String check = textAreaLML.getText().trim();
	            //if the result is not null and the text area is not null
	            if (result != null && result.length() > 0 && !check.equals(""))
	            {
	            	//reset the search text area
	            	searchLML.setText("");
	            	//for every line in the text area
					for (String line : textAreaLML.getText().split("\\n"))
					{	
						//get the quantity by splitting the line
						String[] vals = line.split(", ");
						String call_id = vals[4]; 
						if (call_id.equals(result))
						{
							//if a match is found append the result to the search text area
							searchLML.append(line + "\n");
						}
					}
	            }
	            else
	            {
	            	//otherwise nothing was found
	            	JOptionPane.showMessageDialog(null, "No library material list loaded, or no Call Number entered.", null, JOptionPane.INFORMATION_MESSAGE);
	            }
			}
		});
		mnAdvancedMaterialsOptions.add(mntmSearchbyQuantity);
		//create a new jseparator menu item 
		JSeparator separator = new JSeparator();
		mnAdvancedMaterialsOptions.add(separator);
		//create a new save search menu item
		JMenuItem mntmSaveSearch = new JMenuItem("Save Search");
		mntmSaveSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//call the save dialog
				saveDialog(); 
			}
		});
		mnAdvancedMaterialsOptions.add(mntmSaveSearch);
		//create a new advanced patron option in the menu tab
		JMenu mnAdvancedPatronOptions = new JMenu("Advanced Patron Options");
		menuBar.add(mnAdvancedPatronOptions);
		//create a new menu item by searching by first name 
		JMenuItem mntmSearchbyFirst = new JMenuItem("Search (by Name) CTRL + F");
		mntmSearchbyFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//show an input dialog and get the string as input
				  String result = (String)JOptionPane.showInputDialog
		            		(null,"Enter a name to Search:", "Advanced Search: Name",JOptionPane.PLAIN_MESSAGE);
				//string to check to ensure the text area is not empty
				String check = textAreaPIL.getText().trim();
					//if the result is not null and the text area is not null
		            if (result != null && result.length() > 0 && !check.equals(""))
		            {
		            	//reset the search text area
		            	searchPIL.setText("");
		            	//for every line in the text area
						for (String line : textAreaPIL.getText().split("\\n"))
						{	
							//get the name by splitting the line
							String[] vals = line.split(", ");
							String name = vals[1]; 
							if (name.contains(result))
							{
								//if a match is found append the result to the search text area
								searchPIL.append(line + "\n");
							}
						}
		            }
		            else
		            {
		            	//otherwise nothing was found 
		            	JOptionPane.showMessageDialog(null, "No library patron list loaded, or no name entered.", null, JOptionPane.INFORMATION_MESSAGE);
		            }
			}
		});
		mnAdvancedPatronOptions.add(mntmSearchbyFirst);
		//create a search by id menu item 
		JMenuItem mntmSearchbyId = new JMenuItem("Search (by ID) CTRL + I");
		mntmSearchbyId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
					//show an input dialog and get the string as input
				  String result = (String)JOptionPane.showInputDialog
		            		(null,"Enter a Patron ID to Search:", "Advanced Search: ID",JOptionPane.PLAIN_MESSAGE);
				  	//string to check to ensure the text area is not empty
		            String check = textAreaPIL.getText().trim();
		            //if the result is not null and the text area is not null
		            if (result != null && result.length() > 0 && !check.equals(""))
		            {
		            	//reset the search text area
		            	searchPIL.setText("");
		            	//for every line in the text area
						for (String line : textAreaPIL.getText().split("\\n"))
						{	
							//get the id by splitting the line
							String[] vals = line.split(", ");
							String id = vals[0]; 
							if (id.equals(result))
							{
								//if a match is found append the result to the search text area
								searchPIL.append(line + "\n");
							}
						}
		            }
		            else
		            {
		            	//otherwise nothing was found 
		            	JOptionPane.showMessageDialog(null, "No library patron list loaded, or no name entered.", null, JOptionPane.INFORMATION_MESSAGE);
		            }
			}
		});
		mnAdvancedPatronOptions.add(mntmSearchbyId);
		//create a new search by date menu item 
		JMenuItem mntmSearchdueDate = new JMenuItem("Search (Due Date)");
		mntmSearchdueDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
					//show an input dialog and get the string as input
				  String result = (String)JOptionPane.showInputDialog
		            		(null,"Enter a due date to Search:", "Advanced Search: Due Date",JOptionPane.PLAIN_MESSAGE);
				  	//string to check to ensure the text area is not empty
		            String check = textAreaPIL.getText().trim();
		            //if the result is not null and the text area is not null
		            if (result != null && result.length() > 0 && !check.equals(""))
		            {
		            	//reset the search text area
		            	searchPIL.setText("");
		            	//for every line in the text area
						for (String line : textAreaPIL.getText().split("\\n"))
						{	
							//get the date by splitting the line
							String[] vals = line.split(", ");
							String date = vals[6]; 
							if (date.equals(result))
							{
								//if a match is found append the result to the search text area
								searchPIL.append(line + "\n");
							}
						}
		            }
		            else
		            {
		            	//otherwise nothing was found 
		            	JOptionPane.showMessageDialog(null, "No library patron list loaded, or no name entered.", null, JOptionPane.INFORMATION_MESSAGE);
		            }
			}
		});
		mnAdvancedPatronOptions.add(mntmSearchdueDate);
		//create a new save search menu item 
		JMenuItem mntmSaveSearch_1 = new JMenuItem("Save Search");
		mntmSaveSearch_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//call the save dialog
				saveDialog(); 
			}
		});
		mnAdvancedPatronOptions.add(mntmSaveSearch_1);
		//create the help menu
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		//add a help menu button
		JMenuItem mntmHelpDocumentation = new JMenuItem("Help Documentation");
		mntmHelpDocumentation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//display the help dialog
				JOptionPane.showMessageDialog(null, "Help Documentation can be found here: www.libralms.com", "Libra Software LMS: Help Documentation", JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnHelp.add(mntmHelpDocumentation);
		//create a new jseparator
		JSeparator separator_1 = new JSeparator();
		mnHelp.add(separator_1);
		//create an about dialog
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//display the about dialog
				JOptionPane.showMessageDialog(null, "Prototype Developed by Dakota Soares, COMP482 Project, Athabasca University", "Libra Software LMS: About", JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnHelp.add(mntmAbout);
		//gridbag layout components for the main JFrame: 
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{120, 71, 79, 93, 85, 105, 31, 110, 65, 79, 93, 87, 115, 0};
		gridBagLayout.rowHeights = new int[]{20, 23, 373, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmLibraLms.getContentPane().setLayout(gridBagLayout);
		
		//action listener for the delete materials list
		btnDelItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try {
				//get the selected string
				String selected = textAreaLML.getSelectedText(); 
				//clear the search jtextarea
				searchLML.setText(""); 
				//make an array of items
				ArrayList<mItem> items = new ArrayList<mItem>(); 
				//for every line... split it into an array
				for (String line : textAreaLML.getText().split("\\n"))
				{	
					String[] vals = line.split(", ");
					String call_id = vals[0]; 
					String title = vals[1]; 
					String author = vals[2]; 
					String year = vals[3]; 
					String quantity = vals[4];
					int call_id_no = 0; 
					int year_no = 0; 
					int quantity_no = 0; 
					//try to parse the integer
					try
					{
						call_id_no = Integer.parseInt(call_id); 
						year_no = Integer.parseInt(year); 
						quantity_no = Integer.parseInt(quantity); 
					}
					catch(Exception e1)
					{
						//in the actual implementation a log file error would be written
						//in addition to the line below
						System.out.println("Cast Error - try conversion again"); 
					}
					//add the items to the array
					items.add(new mItem(call_id_no, title, author, year_no, quantity_no)); 
				}
				//trim the selection and split it to get the call id
				selected.trim(); 
				String[] sel_temp = selected.split(", ");
				String call_id = sel_temp[0]; 
				int call_id_no = Integer.parseInt(call_id); 
						
				//for every item in the list
				for (int i = 0; i < items.size(); i++)
				{
					//if there's a match
					if (call_id_no == items.get(i).call_no)
					{
						//remove the item
						items.remove(i); 
					}
				}
				//sort everything again
				Collections.sort(items);
				//clear the text area
				textAreaLML.setText("");
				//add the materials back to the text area
				for (mItem i : items)
				{
					textAreaLML.append(i.toString() + "\n");
				}
			}
			
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "Nothing selected to delete. Please select an item to delete.", "Delete Item", JOptionPane.PLAIN_MESSAGE);
			}
		}
		});
		//labels for the main GUI
		JLabel lblNewLabel = new JLabel("Library Materials List");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		frmLibraLms.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		//grid bag layout details for the text field
		GridBagConstraints gbc_textfieldLML = new GridBagConstraints();
		gbc_textfieldLML.fill = GridBagConstraints.BOTH;
		gbc_textfieldLML.insets = new Insets(0, 0, 5, 5);
		gbc_textfieldLML.gridwidth = 5;
		gbc_textfieldLML.gridx = 1;
		gbc_textfieldLML.gridy = 0;
		frmLibraLms.getContentPane().add(textfieldLML, gbc_textfieldLML);
		textfieldLML.setColumns(10);
		//create a search for the library materials list
		JButton btnSearchLML = new JButton("Search");
		btnSearchLML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//clear the search area text 
				searchLML.setText("");
				//if the text field text is nothing
				if (textfieldLML.getText().equals(""))
				{
					//then prompt the user
					JOptionPane.showMessageDialog(null, "Nothing to Search.");
				}
				//otherwise
				else 
				{
					//get the text
					String txt = textfieldLML.getText(); 
					//for every line...
					for (String line : textAreaLML.getText().split("\\n"))
					{
						//if there is a match (even a partial one)
						if (line.contains(txt))
						{
							//append it to the search text area
							searchLML.append(line + "\n");
						}
					}
				}
				
			}
		});
		//implementation details for the patron information list
		JLabel lblNewLabel_1 = new JLabel("Patron Information List");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 7;
		gbc_lblNewLabel_1.gridy = 0;
		frmLibraLms.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		//implementation details for the text field
		textfieldPIL = new JTextField();
		GridBagConstraints gbc_textfieldPIL = new GridBagConstraints();
		gbc_textfieldPIL.fill = GridBagConstraints.BOTH;
		gbc_textfieldPIL.insets = new Insets(0, 0, 5, 0);
		gbc_textfieldPIL.gridwidth = 5;
		gbc_textfieldPIL.gridx = 8;
		gbc_textfieldPIL.gridy = 0;
		frmLibraLms.getContentPane().add(textfieldPIL, gbc_textfieldPIL);
		textfieldPIL.setColumns(10);
		GridBagConstraints gbc_btnSearchLML = new GridBagConstraints();
		gbc_btnSearchLML.anchor = GridBagConstraints.WEST;
		gbc_btnSearchLML.fill = GridBagConstraints.VERTICAL;
		gbc_btnSearchLML.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearchLML.gridx = 1;
		gbc_btnSearchLML.gridy = 1;
		frmLibraLms.getContentPane().add(btnSearchLML, gbc_btnSearchLML);
		//create a new autosort button to sort the materials list
		JButton btnAutoSortLML = new JButton("Auto Sort");
		btnAutoSortLML.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//call the generic auto sort function
				autoSort(); 
			}
		});
		//implementation for GUI for the auto sort button
		GridBagConstraints gbc_btnAutoSortLML = new GridBagConstraints();
		gbc_btnAutoSortLML.anchor = GridBagConstraints.WEST;
		gbc_btnAutoSortLML.fill = GridBagConstraints.VERTICAL;
		gbc_btnAutoSortLML.insets = new Insets(0, 0, 5, 5);
		gbc_btnAutoSortLML.gridx = 2;
		gbc_btnAutoSortLML.gridy = 1;
		frmLibraLms.getContentPane().add(btnAutoSortLML, gbc_btnAutoSortLML);
		
		//no functionality prototype only for the sort options dialog
		JButton btnSortOptionsLML = new JButton("Sort Options");
		btnSortOptionsLML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//sample of options... no implementation
				SortFrameMaterialList sort = new SortFrameMaterialList(); 
				sort.setTitle("Sort Options");
				sort.setVisible(true);
				sort.setAlwaysOnTop(true);
				sort.setBounds(100, 100, 250, 200); 
				sort.setResizable(false);
			}
		});
		//implementation for the GUI for the sort options button
		GridBagConstraints gbc_btnSortOptionsLML = new GridBagConstraints();
		gbc_btnSortOptionsLML.anchor = GridBagConstraints.WEST;
		gbc_btnSortOptionsLML.fill = GridBagConstraints.VERTICAL;
		gbc_btnSortOptionsLML.insets = new Insets(0, 0, 5, 5);
		gbc_btnSortOptionsLML.gridx = 3;
		gbc_btnSortOptionsLML.gridy = 1;
		frmLibraLms.getContentPane().add(btnSortOptionsLML, gbc_btnSortOptionsLML);
		
		//action listener for adding items to the materials list
		btnAddItem.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) 
			{
				//get the proposed entry
				 String result = (String)JOptionPane.showInputDialog
		            		(null,"Enter an item to add: (Format: call#, title, author, year, quantity)", "Materials List: Add Item",JOptionPane.PLAIN_MESSAGE);
		            //if the result is not null and greater then 0
		            if (result != null && result.length() > 0)
		            {
		            	//try to split it
		            	try 
		            	{
		            	String[] vals = result.split(", ");
		    			String call_id = vals[0]; 
		    			String title = vals[1]; 
		    			String author = vals[2]; 
		    			String year = vals[3]; 
		    			String quantity = vals[4];
		    			int call_id_no = 0; 
		    			int year_no = 0; 
		    			int quantity_no = 0; 
		    			//if the array is less then five then the format isn't correct
		    			if (vals.length <5) throw new Exception(); 
		    			//otherwise try a parse
		    			call_id_no = Integer.parseInt(call_id); 
		    			year_no = Integer.parseInt(year); 
		    			quantity_no = Integer.parseInt(quantity); 
		    				//for every line in the text area check to see if the id's are the same...
		    				for (String line : textAreaLML.getText().split("\\n"))
							{	
								String[] vals1 = line.split(", ");
								String call_id1 = vals[0]; 
								int call_id_no1= Integer.parseInt(call_id1); 
								//if they are then throw an exception
								if (call_id_no == call_id_no1)
								{
									throw new Exception(); 
								}
							}
		    			}
		            	catch (Exception e1)
		            	{
		            	}
		            	//otherwise all good so we can append the result
		            	textAreaLML.append(result);
		            }
		            else
		            {
		            	//otherwise nothing was entered
		            	JOptionPane.showMessageDialog(null, "No library material list item entered.", null, JOptionPane.INFORMATION_MESSAGE);
		            }
				
			}
		});
		btnAddItem.setEnabled(false);
		//implementation for the GUI for the add item button
		GridBagConstraints gbc_btnAddItem = new GridBagConstraints();
		gbc_btnAddItem.anchor = GridBagConstraints.WEST;
		gbc_btnAddItem.fill = GridBagConstraints.VERTICAL;
		gbc_btnAddItem.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddItem.gridx = 4;
		gbc_btnAddItem.gridy = 1;
		frmLibraLms.getContentPane().add(btnAddItem, gbc_btnAddItem);
		//implementation for the GUI for the delete item button
		btnDelItem.setEnabled(false);
		GridBagConstraints gbc_btnDelItem = new GridBagConstraints();
		gbc_btnDelItem.anchor = GridBagConstraints.WEST;
		gbc_btnDelItem.fill = GridBagConstraints.VERTICAL;
		gbc_btnDelItem.insets = new Insets(0, 0, 5, 5);
		gbc_btnDelItem.gridx = 5;
		gbc_btnDelItem.gridy = 1;
		frmLibraLms.getContentPane().add(btnDelItem, gbc_btnDelItem);
		
		//action listener for the delete patron button 
		btnDeletePatron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try {
				//get the selected string
				String selected = textAreaPIL.getSelectedText(); 
				//clear the search text area 
				searchPIL.setText(""); 
				//create an array of patron items
				ArrayList<pItem> items = new ArrayList<pItem>(); 
				//for every line in the text area, split it into an array
				for (String line : textAreaPIL.getText().split("\\n"))
				{	
					String[] vals = line.split(", ");
        			String patron_id = vals[0]; 
        			String name = vals[1]; 
        			String address = vals[2]; 
        			String email = vals[3]; 
        			String phone = vals[4];
        			String book = vals[5]; 
        			String return_date = vals[6]; 
        			int patron_id_no = 0;  
        			int book_id = 0; 
        			//try to parse the integers 
        			try
        			{
        				patron_id_no = Integer.parseInt(patron_id); 
        				book_id = Integer.parseInt(book); 
        			}
        			catch(Exception e1)
        			{
        				//in the actual implementation a log file error would be written
						//in addition to the line below
        				System.out.println("Cast Error - try conversion again"); 
        			}
					//add the items to the array 
					items.add(new pItem(patron_id_no, name, address, email, phone, book_id, return_date)); 
				}
				//trim the selection
				selected.trim(); 
				//split the selected string and parse the id
				String[] sel_temp = selected.split(", ");
				String patron_id = sel_temp[0]; 
				int patron_id_no = Integer.parseInt(patron_id); 
				//for every item in the array see if the patron id's match
				for (int i = 0; i < items.size(); i++)
				{
					//if they do then remove the item
					if (patron_id_no == items.get(i).patron)
					{
						items.remove(i); 
					}
				}
				//run a sort to adjust the list
				Collections.sort(items);
				//clear the search area
				textAreaPIL.setText("");
				//add the items back into the text area
				for (pItem i : items)
				{
					textAreaPIL.append(i.toString() + "\n");
				}
			}
				
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "Nothing selected to delete. Please select a patron to delete.", "Delete Patron", JOptionPane.PLAIN_MESSAGE);
			}
		}
		});
		//add an action listern to the add patron button
		btnAddPatron.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) 
			{
				//get the proposed entry
				String result = (String)JOptionPane.showInputDialog
	            		(null,"Enter an item to add: (Format: patron#, name, address, email, phone, book id, due date)", 
	            				"Materials List: Add Item",JOptionPane.PLAIN_MESSAGE);
	            //if the result is not null and greater then 0
	            if (result != null && result.length() > 0)
	            {
	            	//try and split the string  
	            	try 
	            	{
	            		String[] vals = result.split(", ");
	        			String patron_id = vals[0]; 
	        			String name = vals[1]; 
	        			String address = vals[2]; 
	        			String email = vals[3]; 
	        			String phone = vals[4];
	        			String book = vals[5]; 
	        			String return_date = vals[6]; 
	        			int patron_id_no = 0;  
	        			int book_id = 0; 
	        			//try to parse the integers 
	        			try
	        			{
	        				patron_id_no = Integer.parseInt(patron_id); 
	        				book_id = Integer.parseInt(book); 
	        			}
	        			catch(Exception e1)
	        			{
	        				//in the actual implementation a log file error would be written
							//in addition to the line below
	        				System.out.println("Cast Error - try conversion again"); 
	        			}
	        			//if the value array is less then 7 then the formatting is incorrect and throw an exception
	        			if (vals.length <7) throw new Exception(); 
	    				//for every line in the text area
	    				for (String line : textAreaPIL.getText().split("\\n"))
						{	
	    					//create an array
							String[] vals1 = line.split(", ");
							String patron_id1 = vals[0]; 
							int patron_id_no1= Integer.parseInt(patron_id1); 
							//if the proposed entry id is equal to an id already in the list
							if (patron_id_no == patron_id_no1)
							{
								//throw an exception 
								throw new Exception(); 
							}
						}
	    			}
	            	//catch any errors
	            	catch (Exception e1)
	            	{
	            		//JOptionPane.showMessageDialog(null, "Incorrect Parameters.", null, JOptionPane.INFORMATION_MESSAGE);
	            	}
	            	//append the result
	            	textAreaPIL.append(result);
	            }
	            //otherwise alert the user 
	            else
	            {
	            	JOptionPane.showMessageDialog(null, "No library patron entered.", null, JOptionPane.INFORMATION_MESSAGE);
	            }
			}
		});
		//create a new search patron information button  
		JButton btnSearchPIL = new JButton("Search");
		btnSearchPIL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//clear the search area
				searchPIL.setText("");
				//if nothing is in the textfield then show a message box
				if (textfieldPIL.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Nothing to Search.");
				}
				//otherwise...
				else 
				{
					//get the text and search for it
					String txt = textfieldPIL.getText(); 
					System.out.println(txt); 
					
					for (String line : textAreaPIL.getText().split("\\n"))
					{
						if (line.contains(txt))
						{
							searchPIL.append(line + "\n");
						}
					}
				}
			}
		});
		//implementation for the GUI for the search patron info list button
		GridBagConstraints gbc_btnSearchPIL = new GridBagConstraints();
		gbc_btnSearchPIL.anchor = GridBagConstraints.WEST;
		gbc_btnSearchPIL.fill = GridBagConstraints.VERTICAL;
		gbc_btnSearchPIL.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearchPIL.gridx = 8;
		gbc_btnSearchPIL.gridy = 1;
		frmLibraLms.getContentPane().add(btnSearchPIL, gbc_btnSearchPIL);
		//create a new autosort patron list button
		JButton btnAutoSortPIL = new JButton("Auto Sort");
		btnAutoSortPIL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//call the autosort function
				autoSortPatron(); 
			}
		});
		//implementation for the GUI for the add item button
		GridBagConstraints gbc_btnAutoSortPIL = new GridBagConstraints();
		gbc_btnAutoSortPIL.anchor = GridBagConstraints.WEST;
		gbc_btnAutoSortPIL.fill = GridBagConstraints.VERTICAL;
		gbc_btnAutoSortPIL.insets = new Insets(0, 0, 5, 5);
		gbc_btnAutoSortPIL.gridx = 9;
		gbc_btnAutoSortPIL.gridy = 1;
		frmLibraLms.getContentPane().add(btnAutoSortPIL, gbc_btnAutoSortPIL);
		//create a sort option... no implementation 
		JButton btnSortOptionsPIL = new JButton("Sort Options");
		btnSortOptionsPIL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				SortFramePatronList sort = new SortFramePatronList(); 
				sort.setTitle("Sort Options: Patron Sort");
				sort.setVisible(true);
				sort.setAlwaysOnTop(true);
				sort.setBounds(100, 100, 250, 200); 
				sort.setResizable(false);
			}
		});
		//implementation for the GUI for the add patron and delete patron buttons
		//--------------------------------- Auto Generated -----------------------
		GridBagConstraints gbc_btnSortOptionsPIL = new GridBagConstraints();
		gbc_btnSortOptionsPIL.anchor = GridBagConstraints.WEST;
		gbc_btnSortOptionsPIL.fill = GridBagConstraints.VERTICAL;
		gbc_btnSortOptionsPIL.insets = new Insets(0, 0, 5, 5);
		gbc_btnSortOptionsPIL.gridx = 10;
		gbc_btnSortOptionsPIL.gridy = 1;
		frmLibraLms.getContentPane().add(btnSortOptionsPIL, gbc_btnSortOptionsPIL);
		GridBagConstraints gbc_btnAddPatron = new GridBagConstraints();
		gbc_btnAddPatron.anchor = GridBagConstraints.WEST;
		gbc_btnAddPatron.fill = GridBagConstraints.VERTICAL;
		gbc_btnAddPatron.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddPatron.gridx = 11;
		gbc_btnAddPatron.gridy = 1;
		frmLibraLms.getContentPane().add(btnAddPatron, gbc_btnAddPatron);
		GridBagConstraints gbc_btnDeletePatron = new GridBagConstraints();
		gbc_btnDeletePatron.anchor = GridBagConstraints.WEST;
		gbc_btnDeletePatron.fill = GridBagConstraints.VERTICAL;
		gbc_btnDeletePatron.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeletePatron.gridx = 12;
		gbc_btnDeletePatron.gridy = 1;
		frmLibraLms.getContentPane().add(btnDeletePatron, gbc_btnDeletePatron);
		GridBagConstraints gbc_textAreaLML = new GridBagConstraints();
		gbc_textAreaLML.fill = GridBagConstraints.BOTH;
		gbc_textAreaLML.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaLML.gridwidth = 6;
		gbc_textAreaLML.gridx = 0;
		gbc_textAreaLML.gridy = 2;
		textAreaLML.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {}});
		frmLibraLms.getContentPane().add(textAreaLML, gbc_textAreaLML);
		
		GridBagConstraints gbc_textAreaPIL = new GridBagConstraints();
		gbc_textAreaPIL.insets = new Insets(0, 0, 5, 0);
		gbc_textAreaPIL.fill = GridBagConstraints.BOTH;
		gbc_textAreaPIL.gridwidth = 6;
		gbc_textAreaPIL.gridx = 7;
		gbc_textAreaPIL.gridy = 2;
		frmLibraLms.getContentPane().add(textAreaPIL, gbc_textAreaPIL);
		
		JLabel lblNewLabel_2 = new JLabel("Library Materials List Search Results (below):");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridwidth = 3;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 3;
		frmLibraLms.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JButton btnSaveSearch = new JButton("Save Search");
		btnSaveSearch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//call the save dialog
				  saveDialog(); 
			}
		});
		GridBagConstraints gbc_btnSaveSearch = new GridBagConstraints();
		gbc_btnSaveSearch.anchor = GridBagConstraints.WEST;
		gbc_btnSaveSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSaveSearch.gridx = 4;
		gbc_btnSaveSearch.gridy = 3;
		frmLibraLms.getContentPane().add(btnSaveSearch, gbc_btnSaveSearch);
		
		JButton btnNewButton = new JButton("Clear Results");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//clear the text
				searchLML.setText("");	
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 5;
		gbc_btnNewButton.gridy = 3;
		frmLibraLms.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("Patron Information List Search Results (below): ");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridwidth = 3;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 7;
		gbc_lblNewLabel_3.gridy = 3;
		frmLibraLms.getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JButton btnSaveSearch_1 = new JButton("Save Search");
		btnSaveSearch_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//call the save dialog
				saveDialog(); 
			}
		});
		GridBagConstraints gbc_btnSaveSearch_1 = new GridBagConstraints();
		gbc_btnSaveSearch_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnSaveSearch_1.gridx = 11;
		gbc_btnSaveSearch_1.gridy = 3;
		frmLibraLms.getContentPane().add(btnSaveSearch_1, gbc_btnSaveSearch_1);
		
		JButton btnNewButton_1 = new JButton("Clear Results");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//clear the search text area
				searchPIL.setText(""); 
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 12;
		gbc_btnNewButton_1.gridy = 3;
		frmLibraLms.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		
		searchLML.setEditable(false);
		GridBagConstraints gbc_searchLML = new GridBagConstraints();
		gbc_searchLML.gridheight = 4;
		gbc_searchLML.gridwidth = 6;
		gbc_searchLML.insets = new Insets(0, 0, 5, 5);
		gbc_searchLML.fill = GridBagConstraints.BOTH;
		gbc_searchLML.gridx = 0;
		gbc_searchLML.gridy = 4;
		frmLibraLms.getContentPane().add(searchLML, gbc_searchLML);
		
		searchPIL.setEditable(false);
		GridBagConstraints gbc_searchPIL = new GridBagConstraints();
		gbc_searchPIL.insets = new Insets(0, 0, 5, 0);
		gbc_searchPIL.gridheight = 4;
		gbc_searchPIL.gridwidth = 6;
		gbc_searchPIL.fill = GridBagConstraints.BOTH;
		gbc_searchPIL.gridx = 7;
		gbc_searchPIL.gridy = 4;
		frmLibraLms.getContentPane().add(searchPIL, gbc_searchPIL);
	}
	
	/** 
	*  Saves a file 
	*  @param 
	*  @return 
	*  @throws 
	*/
	public void saveDialog()
	{
		 FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Text File", "txt");
	      final JFileChooser saveAsFileChooser = new JFileChooser();
	      saveAsFileChooser.setApproveButtonText("Save");
	      saveAsFileChooser.setFileFilter(extensionFilter);
	      int actionDialog = saveAsFileChooser.showSaveDialog(null);
	      if (actionDialog != JFileChooser.APPROVE_OPTION) {
	         return;
	      }

	      File file = saveAsFileChooser.getSelectedFile();
	      if (!file.getName().endsWith(".txt")) {
	         file = new File(file.getAbsolutePath() + ".txt");
	      }

	      BufferedWriter outFile = null;
	      try {
	         outFile = new BufferedWriter(new FileWriter(file));

	         searchLML.write(outFile);
	         JOptionPane.showMessageDialog(null, "Library Materials List Search Saved");

	      } catch (IOException ex) {
	         ex.printStackTrace();
	      } finally {
	         if (outFile != null) {
	            try {
	               outFile.close();
	            } catch (IOException e1) {}
	         }
	      }
	}
	
	/** 
	*  Creates an Authorization Dialog. Reference: Hammed, Mehtab. (Feb 2019). Web. 
	*  Sourced from: https://www.tutorialsfield.com/login-form-in-java-swing-with-source-code/
	*  @param 
	*  @return Returns a AuthorizationFrame object 
	*  @throws 
	*/
	public class AuthorizationFrame extends JFrame implements ActionListener
	{
			//create relevant objects
		    Container container = getContentPane();
		    JLabel userLabel = new JLabel("Username");
		    JLabel passwordLabel = new JLabel("Password");
		    JTextField userTextField = new JTextField();
		    JTextField passwordField = new JTextField();
		    JButton verifyButton = new JButton("Verify");
		    JButton resetButton = new JButton("Reset");
		    //constructor
		    AuthorizationFrame() 
		    {
		        setLayoutManager();
		        setLocationAndSize();
		        addComponentsToContainer();
		        addActionEvent();
		    }
		    //set the layout manager
		    public void setLayoutManager() 
		    {
		        container.setLayout(null);
		    }
		    //set positioning and location
		    public void setLocationAndSize() 
		    {
		        userLabel.setBounds(50, 150, 100, 30);
		        passwordLabel.setBounds(50, 220, 100, 30);
		        userTextField.setBounds(150, 150, 150, 30);
		        passwordField.setBounds(150, 220, 150, 30);
		        verifyButton.setBounds(50, 300, 100, 30);
		        resetButton.setBounds(200, 300, 100, 30);
		    }
		    //add components to the container
		    public void addComponentsToContainer() 
		    {
		        container.add(userLabel);
		        container.add(passwordLabel);
		        container.add(userTextField);
		        container.add(passwordField);
		        container.add(verifyButton);
		        container.add(resetButton);
		    }
		    //add action events
		    public void addActionEvent() 
		    {
		        verifyButton.addActionListener(this);
		        resetButton.addActionListener(this);
		    }
		    public boolean auth; 
		    
		    //action performed for the verify administrator privileges button 
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {
		        if (e.getSource() == verifyButton) 
		        { 
		            String userText;
		            String pwdText;
		            //get the entries
		            userText = userTextField.getText();
		            pwdText = passwordField.getText();
		            //if both are correct
		            if (userText.equalsIgnoreCase("admin") == true && pwdText.equalsIgnoreCase("1234") == true) 
		            {
		            	//then display a message and re-enable the edit menu and material edit buttons
		                JOptionPane.showMessageDialog(this, "Administrator Rights Granted");
		                 
		                auth = true; 
		                mnEdit.setEnabled(true);
		                btnAddItem.setEnabled(true);
		                btnDelItem.setEnabled(true);
		                this.setVisible(false);
		            } 
		            //otherwise access denied
		            else if (userText.equalsIgnoreCase("admin") == false || pwdText.equalsIgnoreCase("1234") == false)
		            {
		                JOptionPane.showMessageDialog(this, "Administrator Rights Denied");
		            }
		        }
		        //Resets the dialog text fields
		        if (e.getSource() == resetButton) 
		        {
		            userTextField.setText("");
		            passwordField.setText("");
		        }
		    }
	}
	
	/** 
	*  Creates a SortFrame Dialog (partial implementation due to time constraints
	*  @param 
	*  @return Returns a SortFrameMaterialList object. 
	*  @throws 
	*/
	public class SortFrameMaterialList extends JFrame implements ActionListener
	{
			//create the objects
		    Container container = getContentPane();
	        JCheckBox checkbox = new JCheckBox();
	        JCheckBox checkbox2 = new JCheckBox();
	        //constructor
		    SortFrameMaterialList() 
		    {
		        setLayoutManager();
		        setLocationAndSize();
		        addComponentsToContainer();
		        addActionEvent();
		    }
		    //set the layout manager
		    public void setLayoutManager() 
		    {
		        container.setLayout(null);
		    }
		    //set the position and location
		    public void setLocationAndSize() 
		    {
		        checkbox.setBounds(50, 25, 400, 50);
		        checkbox2.setBounds(50, 75, 400, 50);
		        checkbox.setText("Sort by Call ID");
		        checkbox2.setText("Sort by Quantity");
		    }
		    //add the components 
		    public void addComponentsToContainer()
		    {
		        container.add(checkbox); 
		        container.add(checkbox2); 
		    }
		    //add actions (not implemented) 
		    public void addActionEvent() 
		    {}
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {}
	}
	
	/** 
	*  Creates a SortFrame Dialog (partial implementation due to time constraints
	*  @param 
	*  @return Returns a SortFramePatronList object. 
	*  @throws 
	*/
	public class SortFramePatronList extends JFrame implements ActionListener
	{
			//create the objects
		    Container container = getContentPane();
	        JCheckBox checkbox = new JCheckBox();
	        JCheckBox checkbox2 = new JCheckBox();
	        //constructor
		    SortFramePatronList() 
		    {
		        setLayoutManager();
		        setLocationAndSize();
		        addComponentsToContainer();
		        addActionEvent();
		        
		    }
		    //set the layout manager
		    public void setLayoutManager() 
		    {
		        container.setLayout(null);
		    }
		    //set the position and layout
		    public void setLocationAndSize() 
		    {
		        checkbox.setBounds(50, 25, 400, 50);
		        checkbox2.setBounds(50, 75, 400, 50);
		        checkbox.setText("Sort by Name");
		        checkbox2.setText("Sort by Item Call Number");
		    }
		    //add the components 
		    public void addComponentsToContainer() 
		    {
		        container.add(checkbox); 
		        container.add(checkbox2); 
		    }
		    //add the action events 
		    public void addActionEvent() 
		    {}
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {}
	}
	
	/** 
	*  Creates a JEditMaterialsPane
	*  @param 
	*  @return Returns a JEditMaterialsPane object. Sourced from: 
	*  https://stackoverflow.com/questions/3002787/simple-popup-java-form-with-at-least-two-fields
	*  @throws 
	*/
	public class JEditMaterialPane extends JFrame implements ActionListener
	{

	    private void display(String text) 
	    {
	    	//try the following
	    	try
	    	{
	    	//create a field, panel and label 
	        JTextField field1 = new JTextField(text);
	        JPanel panel = new JPanel(new GridLayout(0, 1));
	        panel.add(new JLabel("Edit the item:"));
	        panel.add(field1);
	        //get the result from the option panel 
	        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Material Dialog",
	            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	        if (result == JOptionPane.OK_OPTION) 
	        {
	        	//get the text 
	           String result1 = field1.getText();
	           
	           //create an array list of mItem - 
		   		ArrayList<mItem> items = new ArrayList<mItem>(); 
	           
	           try
	           {
	        	   	//create an array of objects split by comma
		   			String[] vals = result1.split(", ");
		   			String call_id = vals[0]; 
		   			String title = vals[1]; 
		   			String author = vals[2]; 
		   			String year = vals[3]; 
		   			String quantity = vals[4];
		   			int call_id_no = 0; 
		   			int year_no = 0; 
		   			int quantity_no = 0; 
		   			//parse the integer values
		   			try
		   			{
		   				call_id_no = Integer.parseInt(call_id); 
		   				year_no = Integer.parseInt(year); 
		   				quantity_no = Integer.parseInt(quantity); 
		   			}
		   			//in the actual implementation we'd write the error to a log file
		   			//for now just print out a cast conversion error message: 
		   			catch(Exception e1)
		   			{
		   				System.out.println("Cast Error - try conversion again"); 
		   			}
		   			//add the item to the ArrayList
		   			items.add(new mItem(call_id_no, title, author, year_no, quantity_no));
	           }
	           catch(Exception e)
	           {}
	   		//for every line in the text area... 
	   		for (String line : textAreaLML.getText().split("\\n"))
	   		{	
	   			//create an array of objects split by comma
	   			String[] vals = line.split(", ");
	   			String call_id = vals[0]; 
	   			String title = vals[1]; 
	   			String author = vals[2]; 
	   			String year = vals[3]; 
	   			String quantity = vals[4];
	   			int call_id_no = 0; 
	   			int year_no = 0; 
	   			int quantity_no = 0; 
	   			//parse the integer values
	   			try
	   			{
	   				call_id_no = Integer.parseInt(call_id); 
	   				year_no = Integer.parseInt(year); 
	   				quantity_no = Integer.parseInt(quantity); 
	   			}
	   			//in the actual implementation we'd write the error to a log file
	   			//for now just print out a cast conversion error message: 
	   			catch(Exception e1)
	   			{
	   				System.out.println("Cast Error - try conversion again"); 
	   			}
	   				//add the item to the ArrayList
	   				items.add(new mItem(call_id_no, title, author, year_no, quantity_no)); 
	   			}
	   			//we can then sort the items
	   			Collections.sort(items);
	   			//empty the search area and text area
	   			searchLML.setText(""); 
	   			textAreaLML.setText("");
	   			//for every item in the array
	   			for (mItem m : items)
	   			{
	   				//append it to the textArea material's list
	   				textAreaLML.append(m.toString() + "\n");
	   			}
	        } 
	        else 
	        {
	        	dispose(); 
	        }
	        
	    	}
	    	catch(Exception e)
	    	{}
	    }
	    //add action event 
	    public void addActionEvent() 
	    {}
	    @Override
	    public void actionPerformed(ActionEvent e) 
	    {}
	}
	
	/** 
	*  Creates a JEditPatronPane
	*  @param 
	*  @return Returns a JEditPatronPane object. Sourced from: 
	*  https://stackoverflow.com/questions/3002787/simple-popup-java-form-with-at-least-two-fields
	*  @throws 
	*/
	public class JEditPatronPane extends JFrame implements ActionListener
	{
	    private void display(String text) 
	    {
	    	//try the following; 
	    	try
	    	{
	    	
	        JTextField field1 = new JTextField(text);
	        JPanel panel = new JPanel(new GridLayout(0, 1));
	        panel.add(new JLabel("Edit the item:"));
	        panel.add(field1);
	        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Patron Dialog",
	            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	        if (result == JOptionPane.OK_OPTION) 
	        {
	           String result1 = field1.getText();
	           
	           //create an array list of mItem - 
		   		ArrayList<pItem> items = new ArrayList<pItem>(); 
	           try
	           {
	       			//create an array of objects split by comma
	       			String[] vals = result1.split(", ");
	       			String patron_id = vals[0]; 
	       			String name = vals[1]; 
	       			String address = vals[2]; 
	       			String email = vals[3]; 
	       			String phone = vals[4];
	       			String book = vals[5]; 
	       			String return_date = vals[6]; 
	       			int patron_id_no = 0;  
	       			int book_id = 0; 
	       			//parse the integer values 
	       			try
	       			{
	       				patron_id_no = Integer.parseInt(patron_id); 
	       				book_id = Integer.parseInt(book); 
	       			}
	       			//in the actual implementation we'd write the error to a log file
	       			//for now just print out a cast conversion error message: 
	       			catch(Exception e1)
	       			{
	       				System.out.println("Cast Error - try conversion again"); 
	       			}
	       			//add the item to the ArrayList
	       			items.add(new pItem(patron_id_no, name, address, email, phone, book_id, return_date)); 
	           }
	        	catch(Exception e)
	           {
	           }
	           //for every line in the text area
	           for (String line : textAreaPIL.getText().split("\\n"))
	   			{	
	   			//create an array of objects split by comma
	   			String[] vals = line.split(", ");
	   			String patron_id = vals[0]; 
	   			String name = vals[1]; 
	   			String address = vals[2]; 
	   			String email = vals[3]; 
	   			String phone = vals[4];
	   			String book = vals[5]; 
	   			String return_date = vals[6]; 
	   			int patron_id_no = 0;  
	   			int book_id = 0; 
	   			//parse the integer values 
	   			try
	   			{
	   				patron_id_no = Integer.parseInt(patron_id); 
	   				book_id = Integer.parseInt(book); 
	   			}
	   			//in the actual implementation we'd write the error to a log file
	   			//for now just print out a cast conversion error message: 
	   			catch(Exception e1)
	   			{
	   				System.out.println("Cast Error - try conversion again"); 
	   			}
	   			//add the item to the ArrayList
	   			items.add(new pItem(patron_id_no, name, address, email, phone, book_id, return_date)); 
	   		}
	   		//we can then sort the items
	   		Collections.sort(items);
	   		//empty the search area and text area
	   		searchPIL.setText(""); 
	   		textAreaPIL.setText("");
	   		//for every pItem
	   		for (pItem m : items)
	   		{
	   			//append it to the text area patron's list
	   			textAreaPIL.append(m.toString() + "\n");
	   		}
	        } 
	        else 
	        {}
	    	}
	    	catch(Exception e)
	    	{}
	    }
	    //action events
	    public void addActionEvent() 
	    {}
	    @Override
	    public void actionPerformed(ActionEvent e) 
	    {}
	}
	
	/** 
	*  Creates a PSSFrame (Patron Self-Search Dialog
	*  @param 
	*  @return Returns a PSSFrame object. 
	*  @throws 
	*/
	public class PSSFrame extends JFrame implements ActionListener
	{
			//constructor 
		    PSSFrame() 
		    {
		    	//create the components 
		    	JTextField textField = new JTextField();
		    	JTextArea textArea = new JTextArea();
		    	JButton btnClose = new JButton("Close");
		    	setTitle("Libra Software LMS: Patron Search");
				setVisible(true);
				setResizable(false);
				//fill the screen
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				setSize(screenSize.width, screenSize.height);
				//don't force the window to close on exit so patron's can shut the window accidentally 
				setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				SpringLayout springLayout = new SpringLayout();
				//---------------------- Auto Generated -----------------------------------
				getContentPane().setLayout(springLayout);
				JLabel lblNewLabel = new JLabel("Patron Search System");
				springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 79, SpringLayout.NORTH, getContentPane());
				springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 600, SpringLayout.WEST, getContentPane());
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 70));
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				getContentPane().add(lblNewLabel);
				
				JLabel lblSearchBelowTo = new JLabel("Search below to find material in our library by title: ");
				springLayout.putConstraint(SpringLayout.NORTH, lblSearchBelowTo, 55, SpringLayout.SOUTH, lblNewLabel);
				springLayout.putConstraint(SpringLayout.WEST, lblSearchBelowTo, 0, SpringLayout.WEST, lblNewLabel);
				lblSearchBelowTo.setFont(new Font("Tahoma", Font.PLAIN, 30));
				getContentPane().add(lblSearchBelowTo);
				//create a new search button
				JButton btnSearch = new JButton("Search");
				springLayout.putConstraint(SpringLayout.EAST, btnSearch, -1080, SpringLayout.EAST, getContentPane());
				btnSearch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						    textArea.setText(""); 
							String txt = textField.getText(); 
							//note.. that an error will occur if the materials list isn't loaded... nothing will happen. 
							for (String line : textAreaLML.getText().split("\\n"))
							{
								if (line.contains(txt))
								{
									textArea.append(line + "\n");
								}
								
							}
							textField.setText("");
					}
				});
				btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 30));
				getContentPane().add(btnSearch);
				
				JButton btnClear = new JButton("Clear");
				springLayout.putConstraint(SpringLayout.NORTH, btnClear, 0, SpringLayout.NORTH, btnSearch);
				springLayout.putConstraint(SpringLayout.WEST, btnClear, 223, SpringLayout.EAST, btnSearch);
				//clears all text areas in the dialog
				btnClear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						
						textArea.setText("");
						textField.setText("");
					}
				});
				btnClear.setFont(new Font("Tahoma", Font.PLAIN, 30));
				getContentPane().add(btnClear);
				
				springLayout.putConstraint(SpringLayout.NORTH, btnSearch, 24, SpringLayout.SOUTH, textField);
				springLayout.putConstraint(SpringLayout.NORTH, textField, 96, SpringLayout.SOUTH, lblSearchBelowTo);
				springLayout.putConstraint(SpringLayout.SOUTH, textField, -662, SpringLayout.SOUTH, getContentPane());
				springLayout.putConstraint(SpringLayout.WEST, textField, 0, SpringLayout.WEST, lblNewLabel);
				springLayout.putConstraint(SpringLayout.EAST, textField, 0, SpringLayout.EAST, lblSearchBelowTo);
				textField.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				getContentPane().add(textField);
				textField.setColumns(10);
				
				JLabel lblSearchResults = new JLabel("Search Results:");
				springLayout.putConstraint(SpringLayout.NORTH, lblSearchResults, 113, SpringLayout.SOUTH, btnSearch);
				springLayout.putConstraint(SpringLayout.WEST, lblSearchResults, 0, SpringLayout.WEST, lblNewLabel);
				lblSearchResults.setFont(new Font("Tahoma", Font.PLAIN, 30));
				getContentPane().add(lblSearchResults);
				
				
				springLayout.putConstraint(SpringLayout.NORTH, textArea, 25, SpringLayout.SOUTH, lblSearchResults);
				springLayout.putConstraint(SpringLayout.WEST, textArea, 600, SpringLayout.WEST, getContentPane());
				springLayout.putConstraint(SpringLayout.SOUTH, textArea, 341, SpringLayout.SOUTH, lblSearchResults);
				springLayout.putConstraint(SpringLayout.EAST, textArea, 0, SpringLayout.EAST, lblSearchBelowTo);
				getContentPane().add(textArea);
				
				//disposes of the form - this functionality would be disabled in the actual implementation - a series
				//of key combinations would force the window to close. 
				btnClose.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						dispose(); 
					}
				});
				springLayout.putConstraint(SpringLayout.SOUTH, btnClose, -22, SpringLayout.SOUTH, getContentPane());
				springLayout.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, getContentPane());
				getContentPane().add(btnClose);
		    }
		    public void setLocationAndSize() 
		    {}
		 
		    public void addComponentsToContainer() 
		    {}
		 
		    public void addActionEvent() 
		    {}
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {}
	}
	
	//overrides due to the main frame implementing a Mouselistener for the text area selections
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

}
//end of class libra_lms_main.java