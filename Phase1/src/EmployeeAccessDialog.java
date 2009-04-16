//Bret Lowrey and Danny Liu
//Done for phase 1 of CS122B, Jacobson
//University of California Irvine, Spring 2009
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;


public class EmployeeAccessDialog extends JDialog
{
	JLabel result;
	JButton okButton;
	
	static final long serialVersionUID = 0;
	
	//just displays a string out to the screen in a new window with an ok button
	public EmployeeAccessDialog(JFrame parent, String s)
	{
		super(parent, "Employee Access Results", true);
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		setSize( 250, 120 );
	    
	    result = new JLabel(s);
	    contentPane.add(result, BorderLayout.PAGE_START);
	    
	    JButton okButton = new JButton("OK");
	    contentPane.add(okButton, BorderLayout.PAGE_END);
	    
	    setContentPane(contentPane);
	    
		this.addWindowListener( new MyWindowAdapter() );
		
		ButtonListener buttonListener = new ButtonListener(okButton);
		okButton.addActionListener(buttonListener);
		
		setVisible(true);
	}
	
	//used in the associateStar() method in EmployeeAcessFrame
	public EmployeeAccessDialog(JFrame parent, ArrayList<String> starIDs, ArrayList<String> movieIDs, final Connection connection)
	{
		super(parent, "Choose the association", true);
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		setSize( 250, 120 );
		
		String[] starIDsArray = new String[starIDs.size()];
		for(int i=0; i< starIDs.size();i++)
		{
			starIDsArray[i] = starIDs.get(i);
		}
		
		String[] movieIDsArray = new String[movieIDs.size()];
		for(int i=0; i< movieIDs.size();i++)
		{
			movieIDsArray[i] = movieIDs.get(i);
		}
			
		final JComboBox starList = new JComboBox(starIDsArray);  
        final JComboBox movieList = new JComboBox(movieIDsArray);
        final JLabel action = new JLabel("");
        
        JButton get = new JButton("Associate");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	try
            	{
            		Statement update = connection.createStatement();
					update.executeUpdate("INSERT INTO stars_in_movies VALUES(" + (String) starList.getSelectedItem() 
							+ ", " + (String) movieList.getSelectedItem()  + ")");
					action.setText("Association Successful!");
            	}
            	catch (Exception e1)
            	{
            		action.setText("Association Failed!");
            	}
            	
            	
            }
        });
		
		contentPane.add(starList, BorderLayout.PAGE_START);
	    contentPane.add(movieList, BorderLayout.PAGE_START);	    
	    contentPane.add(get, BorderLayout.PAGE_END);
	    contentPane.add(action, BorderLayout.PAGE_END);
	    
	    setContentPane(contentPane);
	    
		this.addWindowListener( new MyWindowAdapter() );
			
		setVisible(true);
	}
	

	class MyWindowAdapter extends WindowAdapter
	{
		public void windowClosing(WindowEvent event)
		{
		    setVisible(false);
			dispose();
		}
	}

    // Since this class listens for events from two classes (buttons),
    // it uses the getSource() method to find out which button
    // sent the event message.
	class ButtonListener implements ActionListener
	{
		JButton okButton;
		public ButtonListener(JButton okButton)
		{
			this.okButton = okButton;
		}
		public void actionPerformed(ActionEvent event)
		{
			EmployeeAccessDialog.this.dispose();
	        
		}
	}
}
