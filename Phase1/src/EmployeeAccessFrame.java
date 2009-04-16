//Bret Lowrey and Danny Liu
//Done for phase 1 of CS122B, Jacobson
//University of California Irvine, Spring 2009

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.sql.*;

public class EmployeeAccessFrame extends JFrame
{
	MenuBar menuBar;
	static final long serialVersionUID = 22;
	double result = 0;
	Connection connection; 
	
	public EmployeeAccessFrame()
	{		
		super("Employee Access - Fabflix");
		try {
			Class.forName("org.postgresql.Driver").newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setSize(400, 350);
		setResizable(false); //Use a flow layout for most of the interfaces so this is important to the look of the program
		addWindowListener(new WindowAdapter() {   // anonymous inner class
            public void windowClosing(WindowEvent e) {
            	exitSystem();
            }
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
//		 Add a content pane, empty for now
		JPanel contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());  
        setContentPane(contentPane);
        
        menuBar = new MenuBar(this);
        setJMenuBar(menuBar);

		setSize(400, 350 + menuBar.getHeight());
        
        NewConnection();
	}
	
	//This method creates a new table window to display results in, the boolean is to include a delete button
	public void employeeAccessTable(Object[][] data,String[] columnNames, boolean delete)
	{
		JFrame frame = new JFrame("Results Table");

        //Create and set up the content pane.
        final EmployeeAccessTable table = new EmployeeAccessTable(data, columnNames);
        table.setOpaque(true); //content panes must be opaque
        frame.setContentPane(table);
        //this checks to see if the window needs a button to delete
        //it is used if the user wants to delete the selected record from the database
        if(delete)
        {
        	JButton get = new JButton("Delete");
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 1;
    		get.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                	String movieID = table.getCurrentlySelected();
                	//the string is invalid if it is selecting something other than a string field
                	if(!movieID.equals("INVALID"))
                	{
                		Statement update;
    					try 
    					{	
    						boolean movieExist = false;
    						Statement checkMovie = connection.createStatement();
    						//check to see if the movie exists, needed to ensure it isnt deleting something that is associated
    						ResultSet result = checkMovie.executeQuery("SELECT * FROM stars_in_movies where movie_id = " + movieID);
    						while(result.next())
    						{
    							movieExist = true;
    						}
    						if(movieExist)
    						{
    							employeeAccessDialog("Cannot be deleted because the Movie is associated with a star!");
    						}
    						else
    						{
    							update = connection.createStatement();
    							update.executeUpdate("delete from movies where id = " + movieID);
    							employeeAccessDialog("Movie Deleted");
    						}
    					}
    					catch (SQLException e1) 
    					{
    						e1.printStackTrace();
    						employeeAccessDialog("Movie record not found!");
    					}
                	}
                	else
                	{
                		employeeAccessDialog("Invalid object Selected");
                	}
                }
                	
            });
    		
    		table.add(get,c);
        }
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);

	}
	
	//use this to print the information out to a dialog box
	public void employeeAccessDialog(String s)
	{
			EmployeeAccessDialog r = new EmployeeAccessDialog(this, s);
			r.isEnabled();
	}
	
	//used when comparing the two ids to associated
	public void employeeAccessDialog(ArrayList<String> starIDs, ArrayList<String> movieIDs,Connection connection)
	{
			EmployeeAccessDialog r = new EmployeeAccessDialog(this, starIDs, movieIDs, connection);
			r.isEnabled();
	}
	
	//this creates the area to make a new connection
	public void NewConnection() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField database = new JTextField(30);		
		final JTextField user = new JTextField(30);	
		final JTextField password = new JTextField(30);	
		
		JButton get = new JButton("Connect");
		JButton clear = new JButton("Clear");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((database.getText().length() > 0) && (user.getText().length() > 0) && (password.getText().length() > 0))
				{
					try {
						connection = DriverManager.getConnection("jdbc:postgresql://localhost/"+ database.getText()
									, user.getText(), password.getText());
						employeeAccessDialog("Connection Successful!");
					} catch (SQLException e1) {
						employeeAccessDialog("Connection Failed!");
					}					
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	database.setText("");
            	user.setText("");
				password.setText("");
            }
        });
		contentPane.add(new JLabel("Enter database"));
		contentPane.add(database);
		contentPane.add(new JLabel("Enter User"));
		contentPane.add(user);
		contentPane.add(new JLabel("Enter Password"));
		contentPane.add(password);
		contentPane.add(get);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();
		
	}

	//this creates the area to enter an arbitary SQL command
	public void AdvancedSql() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField sqlCommand = new JTextField(30);
		
		JButton get = new JButton("Query");
		JButton clear = new JButton("Clear");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((sqlCommand.getText().length() > 0))
				{
					try
					{
						Statement select = connection.createStatement();
						select.executeQuery(sqlCommand.getText());
						employeeAccessDialog("Query exectued successfully!");
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
						employeeAccessDialog("Query failed!");
					}
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	sqlCommand.setText("");
            }
        });
		contentPane.add(new JLabel("Enter SQL Command"));
		contentPane.add(sqlCommand);
		contentPane.add(get);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();		
	}

	//this creates the area to delete the customer inputed from the database
	public void deleteCustomer() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField id = new JTextField(30);
		
		JButton del = new JButton("Delete");
		JButton clear = new JButton("Clear");
		del.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((id.getText().length() > 0))
				{
					try 
					{
						Statement update = connection.createStatement();
						update.executeUpdate("delete from customers where id = " + id.getText());
						employeeAccessDialog("Customer Deleted");
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						employeeAccessDialog("Customer record not found!");
					}
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	id.setText("");
            }
        });
		contentPane.add(new JLabel("Enter ID for customer"));
		contentPane.add(id);
		contentPane.add(del);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();		
	}
	
	//this creates the area to delete a star
	public void deleteStar() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField id = new JTextField(30);
		
		JButton del = new JButton("Delete");
		JButton clear = new JButton("Clear");
		del.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((id.getText().length() > 0))
				{
					Statement update;
					try 
					{	
						boolean movieExist = false;
						Statement checkStar = connection.createStatement();
						ResultSet result = checkStar.executeQuery("SELECT * FROM stars_in_movies where star_id = " + id.getText());
						while(result.next())
						{
							movieExist = true;
						}
						if(movieExist)
						{
							employeeAccessDialog("Cannot be deleted because the star is associated with a movie!");
						}
						else
						{
							update = connection.createStatement();
							update.executeUpdate("delete from stars where id = " + id.getText());
							employeeAccessDialog("Star Deleted");
						}
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						employeeAccessDialog("Star record not found!");
					}
							
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	id.setText("");
            }
        });
		contentPane.add(new JLabel("Enter ID for star"));
		contentPane.add(id);
		contentPane.add(del);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();			
	}

	//this creates the area to delete a movie
	public void deleteMovie() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField id = new JTextField(30);
		
		JButton del = new JButton("Delete");
		JButton clear = new JButton("Clear");
		del.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((id.getText().length() > 0))
				{
					Statement update;
					try 
					{	
						boolean movieExist = false;
						Statement checkMovie = connection.createStatement();
						ResultSet result = checkMovie.executeQuery("SELECT * FROM stars_in_movies where movie_id = " + id.getText());
						while(result.next())
						{
							movieExist = true;
						}
						if(movieExist)
						{
							employeeAccessDialog("Cannot be deleted because the Movie is associated with a star!");
						}
						else
						{
							update = connection.createStatement();
							update.executeUpdate("delete from movies where id = " + id.getText());
							employeeAccessDialog("Movie Deleted");
						}
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						employeeAccessDialog("Movie record not found!");
					}
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	id.setText("");
            }
        });
		contentPane.add(new JLabel("Enter ID for movie"));
		contentPane.add(id);
		contentPane.add(del);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();	
	}

	//this creates the area to delte a genre
	public void deleteGenre() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField id = new JTextField(30);
		
		JButton del = new JButton("Delete");
		JButton clear = new JButton("Clear");
		del.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((id.getText().length() > 0))
				{
					try 
					{	
						boolean movieExist = false;
						Statement checkGenre = connection.createStatement();
						ResultSet result = checkGenre.executeQuery("SELECT * FROM genres_in_movies where genres_id = " + id.getText());
						while(result.next())
						{
							movieExist = true;
						}
						if(movieExist)
						{
						employeeAccessDialog("Cannot be deleted because the genre is associated with a movie!");
						}
						else
						{
							Statement update = connection.createStatement();
							update.executeUpdate("delete from stars where id = " + id.getText());
							employeeAccessDialog("Genre deleted");
						}
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						employeeAccessDialog("Genre record not found!");
					}
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	id.setText("");
            }
        });
		contentPane.add(new JLabel("Enter ID for genre"));
		contentPane.add(id);
		contentPane.add(del);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();		
	}

	//this creates the area to associate a star with a movie
	public void insertAssociation() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField starFirstName = new JTextField(30);		
	    final JTextField starLastName = new JTextField(30);
		final JTextField starID = new JTextField(30);	
		final JTextField movieName = new JTextField(30);	
		final JTextField movieID = new JTextField(30);
		
		JButton get = new JButton("Find");
		JButton clear = new JButton("Clear");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if (((starFirstName.getText().length() > 0) || (starID.getText().length() > 0))&&
						(movieName.getText().length() > 0) || (movieID.getText().length() > 0))
				{
					try 
					{	
						String movieIDStr = movieID.getText();
						String starIDStr = starID.getText();
						
						ArrayList<String> movieIDs= new ArrayList<String>();
						ArrayList<String> starIDs= new ArrayList<String>();
						
						//if they did not manually search for the id, look up all possible ones in the database
						if(movieIDStr.equals(""))
						{
							if((movieName.getText().length() > 0 ))
							{
								Statement getName = connection.createStatement();
								ResultSet result = getName.executeQuery("select * from movies where " +
								"title = '" + movieName.getText() + "'");
								
								while(result.next())
								{
									Integer id = result.getInt(1); 
									movieIDs.add(id.toString());
								}
							}
						}
						else
						{
							movieIDs.add(movieIDStr);
						}
						
						//if they did not manually search for the id, look up all possible ones in the database
						if(starIDStr.equals(""))
						{
							if((starLastName.getText().length() > 0 && (starFirstName.getText().length() > 0)))
							{
								Statement getName = connection.createStatement();
								ResultSet result = getName.executeQuery("select * from stars where " +
								"first_name = '" + starFirstName.getText() + "' and last_name = '" + 
								starLastName.getText() + "'");
								
								while(result.next())
								{
									Integer id = result.getInt(1); 
									starIDs.add(id.toString());
								}
							}
						}
						else
						{
							starIDs.add(starIDStr);
						}
						employeeAccessDialog(starIDs,movieIDs,connection);
						
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						employeeAccessDialog("Query Failed!");
					}
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	starFirstName.setText("");
            	starLastName.setText("");
            	starID.setText("");
				movieName.setText("");
				movieID.setText("");
            }
        });
		contentPane.add(new JLabel("Enter star's first name and..."));
		contentPane.add(starFirstName);
		contentPane.add(new JLabel("Enter star's land name or..."));
		contentPane.add(starLastName);
		contentPane.add(new JLabel("the star's ID"));
		contentPane.add(starID);
		contentPane.add(new JLabel("And the movie's name or..."));
		contentPane.add(movieName);
		contentPane.add(new JLabel("the movie's ID"));
		contentPane.add(movieID);
		contentPane.add(get);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();		
	}

	//insert a brand new movie to the DB
	public void insertMovie() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField title = new JTextField(30);		
		final JTextField year = new JTextField(30);	
		final JTextField directorRestOfName = new JTextField(30);	
		final JTextField directorLastName = new JTextField(30);		
		final JTextField bannerUrl = new JTextField(30);	
		final JTextField trailerUrl = new JTextField(30);	
		
		JButton get = new JButton("Insert");
		JButton clear = new JButton("Clear");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((title.getText().length() > 0) && (directorLastName.getText().length() > 0)
						&& (year.getText().length() > 0) && (bannerUrl.getText().length() > 0)
						&& (trailerUrl.getText().length() > 0))
				{
					try 
					{	
						Statement insert = connection.createStatement();
						insert.executeUpdate("INSERT INTO movies VALUES(DEFAULT, '" + title.getText() + 
								"', " + year.getText() + ", '" + directorRestOfName.getText() + "', '" +
								directorLastName.getText() + "', '" + bannerUrl.getText() + "', '" +
								trailerUrl.getText() + "')");
						employeeAccessDialog("Query Sucessful!");
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						employeeAccessDialog("Query Failed!");
					}
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	title.setText("");
            	year.setText("");
            	directorRestOfName.setText("");
            	directorLastName.setText("");
            	bannerUrl.setText("");
            	trailerUrl.setText("");
            }
        });
		contentPane.add(new JLabel("Enter title of movie"));
		contentPane.add(title);
		contentPane.add(new JLabel("Enter year"));
		contentPane.add(year);
		contentPane.add(new JLabel("Enter director's last name"));
		contentPane.add(directorLastName);
		contentPane.add(new JLabel("Enter rest of director's name"));
		contentPane.add(directorRestOfName);
		contentPane.add(new JLabel("Enter banner URL"));
		contentPane.add(bannerUrl);
		contentPane.add(new JLabel("Enter trailer URL"));
		contentPane.add(trailerUrl);
		contentPane.add(get);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();		
	}

	//insert a customer to DB, must check to see if the cc-id exists
	public void insertCustomer() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField firstName = new JTextField(30);		
		final JTextField lastName = new JTextField(30);	
		final JTextField ccNum = new JTextField(30);	
		final JTextField address = new JTextField(30);		
		final JTextField email = new JTextField(30);	
		final JTextField password = new JTextField(30);	
		
		JButton get = new JButton("Insert");
		JButton clear = new JButton("Clear");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((firstName.getText().length() > 0) && (ccNum.getText().length() > 0)
						&& (address.getText().length() > 0) && (email.getText().length() > 0)
						&& (password.getText().length() > 0) && (lastName.getText().length() > 0))
				{
					try 
					{	
						int ccID = -1;//set to -1 because it will be changed later
						boolean found = false;
						Statement getCCID = connection.createStatement();
						ResultSet result = getCCID.executeQuery("SELECT * FROM creditcards WHERE credit_card_number = '"
								+ ccNum.getText()+ "'");
						while(result.next())
						{
							ccID = result.getInt("cc_id");
							found = true;
						}
						//if the record is not found, insert it into the appropriate tables and coordinate them 
						if(!found)
						{
							Statement insertCC = connection.createStatement();
							insertCC.executeUpdate("INSERT INTO creditcards(credit_card_number, name_on_card, expiration, cc_id)" +
									" VALUES('" + ccNum.getText() + "', '" + firstName.getText() + " " + lastName.getText() + "', " 
									+ "current_date, DEFAULT)");
							
							Statement getNewCCID = connection.createStatement();
							ResultSet result1 = getNewCCID.executeQuery("SELECT * FROM creditcards WHERE credit_card_number = '"
									+ ccNum.getText() + "'");
							while(result1.next())
							{
								ccID = result.getInt("cc_id");
							}
						}

						Statement insert = connection.createStatement();
						insert.executeUpdate("INSERT INTO customers VALUES(DEFAULT, '" + firstName.getText() + 
								"', '" + lastName.getText() + "', " + ccID + ", '" + address.getText() + 
								"', '" + email.getText() + "', '" + password.getText() + "')");
						employeeAccessDialog("Customer added");
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						employeeAccessDialog("Faultly input data!");
					}
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	firstName.setText("");
            	lastName.setText("");
				ccNum.setText("");
				address.setText("");
            	email.setText("");
            	password.setText("");
            }
        });
		contentPane.add(new JLabel("Enter first name"));
		contentPane.add(firstName);
		contentPane.add(new JLabel("Enter last name"));
		contentPane.add(lastName);
		contentPane.add(new JLabel("Enter credit card ID"));
		contentPane.add(ccNum);
		contentPane.add(new JLabel("Enter address"));
		contentPane.add(address);
		contentPane.add(new JLabel("Enter email"));
		contentPane.add(email);
		contentPane.add(new JLabel("Enter password"));
		contentPane.add(password);
		contentPane.add(get);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();		
	}

	//insert a star to the db
	public void insertStar() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField firstName = new JTextField(30);		
		final JTextField lastName = new JTextField(30);	
		final JTextField dob = new JTextField(30);	
		final JTextField photoUrl = new JTextField(30);	
		
		JButton get = new JButton("Insert");
		JButton clear = new JButton("Clear");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((firstName.getText().length() > 0) && (dob.getText().length() > 0)
						 && (lastName.getText().length() > 0)  && (photoUrl.getText().length() > 0))
				{
					try 
					{
						
						Statement insert = connection.createStatement();
						insert.executeUpdate("INSERT INTO stars VALUES(DEFAULT,'" + firstName.getText() + "', '" + lastName.getText() 
								+ "', DATE '" + dob.getText() + "', '" + photoUrl.getText()+ "')");
						
						employeeAccessDialog("Query Sucessful!");
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						employeeAccessDialog("Query Failed!");
					}
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	firstName.setText("");
            	lastName.setText("");
            	dob.setText("");
            	photoUrl.setText("");
            }
        });
		contentPane.add(new JLabel("Enter first name"));
		contentPane.add(firstName);
		contentPane.add(new JLabel("Enter last name"));
		contentPane.add(lastName);
		contentPane.add(new JLabel("Enter date of birth"));
		contentPane.add(dob);
		contentPane.add(new JLabel("Enter Photo URL"));
		contentPane.add(photoUrl);
		contentPane.add(get);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();		
	}

	//retrieve a list of the genres based on a user inputted star
	public void getGenres() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField firstName = new JTextField(30);		
		final JTextField lastName = new JTextField(30);	
		final JTextField starIdTextfield = new JTextField(30);	
		
		JButton get = new JButton("Get genres");
		JButton clear = new JButton("Clear");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((lastName.getText().length() > 0) || (starIdTextfield.getText().length() > 0))
				{
					try
					{	
						String starId = "";
						//get a list of the various ids assigned to the star
						if((lastName.getText().length() > 0 && (firstName.getText().length() > 0)))
						{
							Statement getName = connection.createStatement();
							ResultSet result = getName.executeQuery("select * from stars where " +
							"first_name = '" + firstName.getText() + "' and last_name = '" + 
							lastName.getText() + "'");
							
							while(result.next())
							{
								Integer id = result.getInt(1); 
								starId = id.toString();
							}
						}
						//if they gave the id, it does not need to be searched for
						if(starIdTextfield.getText().length() > 0)
						{	
							starId = starIdTextfield.getText();
						}
						ArrayList<Object[]> genres = new ArrayList<Object[]>();
						Statement getID = connection.createStatement();
						ResultSet result = getID.executeQuery("select * from stars_in_movies where star_id ="
								+ starId);
						//get all of the movies for the star Id
						while(result.next())
						{
							Statement getGenreID = connection.createStatement();
							ResultSet result1 = getGenreID.executeQuery("select * from genres_in_movies where " +
							"movie_id =" + result.getInt(2));
							
							//finally get the genres from the movies
							while(result1.next())
							{
								Statement getGenre = connection.createStatement();
								ResultSet result2 = getGenre.executeQuery("select * from genres where " +
								"id =" + result1.getInt(1));
								
								while(result2.next())
								{
									Object[] genre = {result1.getString(1),result2.getString(2)};
									genres.add(genre);
								}
							}
							
						}

						String[] columnNames = {"ID", "Genre"};
						Object[][] movieDataArray = new Object[genres.size()][];
						for(int i = 0; i<genres.size();i++)
						{
							movieDataArray[i] = genres.get(i);
						}
						//make table with all of the data
						employeeAccessTable(movieDataArray,columnNames,true);
						
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						employeeAccessDialog("Query Failed!");
					}
					
					
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	firstName.setText("");
            	lastName.setText("");
            	starIdTextfield.setText("");
            }
        });
		contentPane.add(new JLabel("Enter the star's first name and..."));
		contentPane.add(firstName);
		contentPane.add(new JLabel("last name; or..."));
		contentPane.add(lastName);
		contentPane.add(new JLabel("the star's ID"));
		contentPane.add(starIdTextfield);
		contentPane.add(get);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();		
	}

	//retrieve all of the movies from a given star
	public void getMovies() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField firstName = new JTextField(30);		
		final JTextField lastName = new JTextField(30);	
		final JTextField starIdTextfield = new JTextField(30);	
		
		JButton get = new JButton("Get movies");
		JButton clear = new JButton("Clear");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((lastName.getText().length() > 0) || (starIdTextfield.getText().length() > 0))
				{
					try
					{	
						ArrayList<String> starIds = new ArrayList<String>();
						//get all of the associated star ids
						if((lastName.getText().length() > 0 && (firstName.getText().length() > 0)))
						{
							Statement getName = connection.createStatement();
							ResultSet result = getName.executeQuery("select * from stars where " +
							"first_name = '" + firstName.getText() + "' and last_name = '" + 
							lastName.getText() + "'");
							
							while(result.next())
							{
								Integer id = result.getInt(1); 
								starIds.add(id.toString());
							}
						}
						if(starIdTextfield.getText().length() > 0)
						{	
							starIds.add(starIdTextfield.getText());
						}
						
						ArrayList<Object[]> movieData = new ArrayList<Object[]>();
						Statement getID = connection.createStatement();
						//find all of the associated movies
						for(String starId : starIds)
						{
							ResultSet result = getID.executeQuery("select * from stars_in_movies where star_id ="
									+ starId);
							while(result.next())
							{
								Statement getMovieID = connection.createStatement();
								ResultSet result1 = getMovieID.executeQuery("select * from movies where " +
								"id =" + result.getInt(2));
								
								while(result1.next())
								{
									//the first name is really the last column added
									//should have used the label functions here instead
									Object[] movie = {result1.getString(1),result1.getString(2),result1.getInt(3),result1.getString(4),
											result1.getString(7),result1.getString(5),result1.getString(6)};
									movieData.add(movie);
								}							
						}
						
						}
						String[] columnNames = {"ID", "Title","Year","Director's Last Name","Director's First Name","Banner URL","Trailer URL"};
						Object[][] movieDataArray = new Object[movieData.size()][];
						for(int i = 0; i<movieData.size();i++)
						{
							movieDataArray[i] = movieData.get(i);
						}
						employeeAccessTable(movieDataArray,columnNames,true);
						
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						employeeAccessDialog("Query Failed!");
					}
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	firstName.setText("");
            	lastName.setText("");
            	starIdTextfield.setText("");
            }
        });
		contentPane.add(new JLabel("Enter the star's first name and..."));
		contentPane.add(firstName);
		contentPane.add(new JLabel("last name; or..."));
		contentPane.add(lastName);
		contentPane.add(new JLabel("the star's ID"));
		contentPane.add(starIdTextfield);
		contentPane.add(get);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();		
	}

	//retrieves the metadata for all of the tables in the DB
	//metadata being column names and data types
	public void getMetadata() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new GridBagLayout()); 

        //a list of all the tables in the db
        String[] tables = { "movies", "stars", "stars_in_movies", "genres", "genres_in_movies",
        					"customers","sales","creditcards"};

        final JComboBox tableList = new JComboBox(tables);    
        GridBagConstraints cTable = new GridBagConstraints();
        cTable.fill = GridBagConstraints.HORIZONTAL;

        
        JButton get = new JButton("Get metadata");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	try
            	{
            		Statement select = connection.createStatement();
            		ResultSet result = select.executeQuery("Select * from " + (String) tableList.getSelectedItem());
            		ResultSetMetaData metadata = result.getMetaData();

            		//get the names of the columns
            		String[] columnNames = new String[metadata.getColumnCount()];
            		ArrayList<String> columnTypeNames = new ArrayList<String>();
            		//get all of the types associated
            		for (int i = 1; i <= metadata.getColumnCount(); i++)
            		{
            			columnNames[i-1] = metadata.getColumnName(i);
            			columnTypeNames.add(metadata.getColumnTypeName(i));      			
            		}
					Object[][] columnTypeNamesforTable = new Object[1][];
					columnTypeNamesforTable[0] =  columnTypeNames.toArray();
					employeeAccessTable(columnTypeNamesforTable,columnNames,false);
            	}
            	catch (Exception e1)
            	{
            		e1.printStackTrace();
            	}
            	
            	
            }
        });
		GridBagConstraints cGet = new GridBagConstraints();
        cGet.fill = GridBagConstraints.HORIZONTAL;
        cGet.gridy = 1;
        
		contentPane.add(tableList, cTable);
		contentPane.add(get, cGet);
		setContentPane(contentPane);

        pack();
		repaint();		
	}
	
	public void run()
	{
		 setVisible(true);
	}
	
	public void exitSystem()
	{
        
			int n = JOptionPane.showConfirmDialog(
                   	this,
                   "Do you really want to quit?",
                   "Confirm File | Exit",
                   JOptionPane.YES_NO_OPTION);
			if (n == 0)
				System.exit(0);
	}	
	
	class MenuBar extends JMenuBar
	{
	    private EmployeeAccessFrame parentFrame;
	    static final long serialVersionUID = 0;

	    MenuBar(EmployeeAccessFrame pf)
	    {
	        parentFrame = pf;

	        JMenu fileMenu;
	        JMenuItem menuItem;

	        // Build the File menu.
	        fileMenu = new JMenu("File");
	        fileMenu.setMnemonic(KeyEvent.VK_F);
	        add(fileMenu);

	        // attach to it a group of JMenuItems
	        menuItem = new JMenuItem("New Connection", KeyEvent.VK_C);
	        menuItem.addActionListener(new FileNewConnectionListener());
	        fileMenu.add(menuItem);
	        
	        fileMenu.addSeparator();
	        menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
	        menuItem.addActionListener(new FileExitListener());
	        fileMenu.add(menuItem);
	        
	        
	     // Build the get data menu.
	        JMenu getDataMenu;
	        getDataMenu = new JMenu("Get");
	        getDataMenu.setMnemonic(KeyEvent.VK_G);
	        add(getDataMenu);
	        
	        menuItem = new JMenuItem("Database Metadata", KeyEvent.VK_D);
	        menuItem.addActionListener(new GetMetadataListener());
	        getDataMenu.add(menuItem);
	        
	        menuItem = new JMenuItem("Movies by a star", KeyEvent.VK_M);
	        menuItem.addActionListener(new GetMoviesListener());
	        getDataMenu.add(menuItem);
	        
	        menuItem = new JMenuItem("Genres by a star", KeyEvent.VK_G);
	        menuItem.addActionListener(new GetGenresListener());
	        getDataMenu.add(menuItem);
	        
	     // Build the insert menu.
	        JMenu insertMenu;
	        insertMenu = new JMenu("Insert");
	        insertMenu.setMnemonic(KeyEvent.VK_I);
	        add(insertMenu);
	        
	        menuItem = new JMenuItem("Star into a movie", KeyEvent.VK_S);
	        menuItem.addActionListener(new InsertStarListener());
	        insertMenu.add(menuItem);
	        
	        menuItem = new JMenuItem("Customer to the database", KeyEvent.VK_C);
	        menuItem.addActionListener(new InsertCustomerListener());
	        insertMenu.add(menuItem);
	        
	        menuItem = new JMenuItem("Movie to the database", KeyEvent.VK_M);
	        menuItem.addActionListener(new InsertMovieListener());
	        insertMenu.add(menuItem);
	        
	        menuItem = new JMenuItem("Associate star with existing movie", KeyEvent.VK_A);
	        menuItem.addActionListener(new InsertAssociationListener());
	        insertMenu.add(menuItem);
	        
	        // Build the delete menu.
	        JMenu deleteMenu;
	        deleteMenu = new JMenu("Delete");
	        deleteMenu.setMnemonic(KeyEvent.VK_D);
	        add(deleteMenu);
	        
	        menuItem = new JMenuItem("Star from database", KeyEvent.VK_S);
	        menuItem.addActionListener(new DeleteStarListener());
	        deleteMenu.add(menuItem);
	        
	        menuItem = new JMenuItem("Customer from database", KeyEvent.VK_C);
	        menuItem.addActionListener(new DeleteCustomerListener());
	        deleteMenu.add(menuItem);
	        
	        menuItem = new JMenuItem("Movie from database", KeyEvent.VK_M);
	        menuItem.addActionListener(new DeleteMovieListener());
	        deleteMenu.add(menuItem);
	        
	        menuItem = new JMenuItem("Genre from database", KeyEvent.VK_G);
	        menuItem.addActionListener(new DeleteGenreListener());
	        deleteMenu.add(menuItem);
	        
	     // Build the advanced menu.
	        JMenu advancedMenu;
	        advancedMenu = new JMenu("Advanced");
	        advancedMenu.setMnemonic(KeyEvent.VK_A);
	        add(advancedMenu);
	        
	        menuItem = new JMenuItem("Input SQL Command", KeyEvent.VK_S);
	        menuItem.addActionListener(new AdvancedSqlListener());
	        advancedMenu.add(menuItem);
	    }
	    class FileExitListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.exitSystem();
	        }
	    }
	    
	    class FileNewConnectionListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.NewConnection();
	        }
	    }
	    
	    class GetMetadataListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.getMetadata();
	        }
	    }
	    
	    class GetMoviesListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.getMovies();
	        }
	    }
	    
	    class GetGenresListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.getGenres();
	        }
	    }
	    
	    class InsertStarListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.insertStar();
	        }
	    }
	    
	    class InsertCustomerListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.insertCustomer();
	        }
	    }
	    
	    class InsertMovieListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.insertMovie();
	        }
	    }
	    
	    class InsertAssociationListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.insertAssociation();
	        }
	    }
	    
	    class DeleteStarListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.deleteStar();
	        }
	    }
	    
	    class DeleteGenreListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.deleteGenre();
	        }
	    }
	    
	    class DeleteMovieListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.deleteMovie();
	        }
	    }
	    
	    class DeleteCustomerListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.deleteCustomer();
	        }
	    }
	    
	    class AdvancedSqlListener implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            parentFrame.AdvancedSql();
	        }
	    }
	}	    
}
