import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class EmployeeAccessDialog extends JDialog
{
	JLabel result;
	JButton okButton;
	
	static final long serialVersionUID = 0;
	
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
