import javax.swing.*;

import java.awt.*;


public class EmployeeAccessTable extends JPanel
{
	JLabel result;
	JButton okButton;
	
	static final long serialVersionUID = 0;
	
	public EmployeeAccessTable(Object[][] data, String[] columnNames) 
	{
        super(new GridLayout(1,0));

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 140));
        table.setFillsViewportHeight(true);


        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
}
