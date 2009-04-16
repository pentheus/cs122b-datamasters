//Bret Lowrey and Danny Liu
//Done for phase 1 of CS122B, Jacobson
//University of California Irvine, Spring 2009
import javax.swing.*;
import java.awt.*;

public class EmployeeAccessTable extends JPanel
{
	private final JTable table;
	
	static final long serialVersionUID = 0;
	
	public EmployeeAccessTable(Object[][] data, String[] columnNames) 
	{
        super(new GridBagLayout());

        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 140));
        table.setFillsViewportHeight(true);


        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
	
	//returns a string of the currently selected cell, if not a string it returns invaild
	public String getCurrentlySelected()
	{
		Object returnStr = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
		if(returnStr instanceof String)
		{
			return (String) returnStr;
		}
		return "INVALID";
		
	}
}
