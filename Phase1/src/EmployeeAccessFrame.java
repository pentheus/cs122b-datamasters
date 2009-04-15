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
	Random r;
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
		setResizable(false);
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
        
		//Default connection, REMOVE BEFORE COMPLETION
		//Done to save time during testing, so the tester doesnt have to enter this each time
		try {
			connection =
				DriverManager.getConnection("jdbc:postgresql://localhost/fabflixs", "testuser", "testpass");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        NewConnection();
	}
	
	public void employeeAccessDialog()
	{
			EmployeeAccessDialog r = new EmployeeAccessDialog(this, result);
			r.isEnabled();
	}
	
	//use this to print the information out to a dialog box
	public void employeeAccessDialog(String s)
	{
			EmployeeAccessDialog r = new EmployeeAccessDialog(this, s);
			r.isEnabled();
	}
	


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
				if ((user.getText().length() > 0) && (password.getText().length() > 0))
				{
					//stuff to get from database 
					
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

	//this is completed, use it as an example for others
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
					// Incorporate postgreSQL driver
					try
					{
						//test data: SELECT * FROM stars WHERE last_name='Kidman';
						// create update DB statement -- deleting second record of table; return status
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
					{	//Still need to test.
						Statement update = connection.createStatement();
						Integer retID = update.executeUpdate("delete from customers where id = " + id.getText());
						employeeAccessDialog(retID.toString());
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
					{	//Still need to test.
						//You need to create a statement that selects the star_id from the stars_in_movies
						//db to see if it exists before you delete it from the stars db
						//if it exists, tell the user that it cannot be deleted because it is associated
						update = connection.createStatement();
						Integer retID = update.executeUpdate("delete from stars where id = " + id.getText());
						employeeAccessDialog(retID.toString());
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
					try 
					{	
						Statement update = connection.createStatement();
						Integer retID = update.executeUpdate("delete from movies where id = " + id.getText());
						employeeAccessDialog(retID.toString());
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
					{	//Still need to test.
						Statement update = connection.createStatement();
						Integer retID = update.executeUpdate("delete from genre where id = " + id.getText());
						employeeAccessDialog(retID.toString());
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

	public void insertAssocation() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField starName = new JTextField(30);		
		final JTextField starID = new JTextField(30);	
		final JTextField movieName = new JTextField(30);	
		final JTextField movieID = new JTextField(30);
		
		JButton get = new JButton("Get Results");
		JButton clear = new JButton("Clear");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((starName.getText().length() > 0) || (starID.getText().length() > 0))
				{
					try 
					{	//I'm not really sure how to do an insert for queries.
						Statement insert = connection.createStatement();
						insert.executeUpdate("INSERT INTO stars VALUES (DEFAULT," + starName.getText() + "and starID = " +
											 starID.getText());
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
            	starName.setText("");
            	starID.setText("");
				movieName.setText("");
				movieID.setText("");
            }
        });
		contentPane.add(new JLabel("Enter star's full name or..."));
		contentPane.add(starName);
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
            	//do actual checking later
				if ((title.getText().length() > 0) && (directorLastName.getText().length() > 0))
				{
					try 
					{	//I'm not really sure how to do an insert for queries.
						Statement insert = connection.createStatement();
						insert.executeUpdate("insert title = " + title.getText() + "and directorLastName = " +
											 directorLastName.getText());
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
		
		JButton get = new JButton("Connect");
		JButton clear = new JButton("Clear");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((firstName.getText().length() > 0) && (ccNum.getText().length() > 0))
				{
					try 
					{	
						int ccID = -1;
						boolean found = false;
						Statement getCCID = connection.createStatement();
						ResultSet result = getCCID.executeQuery("SELECT * FROM creditcards WHERE credit_card_number = "
								+ ccNum.getText());
						while(result.next())
						{
							ccID = result.getInt(4);
							found = true;
						}
						if(found)
						{
							Statement insert = connection.createStatement();
							insert.executeUpdate("INSERT INTO customers VALUES(" + ccID +  ", '" + firstName.getText() + 
									"', '" + lastName.getText() + "', " + ccNum.getText() + ", '" + 
									address.getText() + "', '" + email.getText() + "', '" + password.getText() + 
									"')");
						}
						else
						{
							Statement insert = connection.createStatement();
							insert.executeUpdate("INSERT INTO customers VALUES(DEFAULT, '" + firstName.getText() + 
									"', '" + lastName.getText() + "', " + ccNum.getText() + ", '" + 
									address.getText() + "', '" + email.getText() + "', '" + password.getText() + 
									"')");
						}
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

	public void insertStar() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());        
		
	    final JTextField firstName = new JTextField(30);		
		final JTextField lastName = new JTextField(30);	
		final JTextField dob = new JTextField(30);	
		final JTextField photoUrl = new JTextField(30);	
		
		JButton get = new JButton("Connect");
		JButton clear = new JButton("Clear");
		get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((firstName.getText().length() > 0) && (dob.getText().length() > 0))
				{
					try 
					{	//I'm not really sure how to do an insert for queries.
						
						Statement insert = connection.createStatement();
						insert.executeUpdate("insert firstName = '" + firstName.getText() + "' and dob = " +
											 dob.getText());
						
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
		contentPane.add(new JLabel("Enter User"));
		contentPane.add(photoUrl);
		contentPane.add(get);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();		
	}

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
							//System.out.println(starId);
						}
						if(starIdTextfield.getText().length() > 0)
						{	
							starId = starIdTextfield.getText();
						}
						String genres = "";
						Statement getID = connection.createStatement();
						ResultSet result = getID.executeQuery("select * from stars_in_movies where star_id ="
								+ starId);
						while(result.next())
						{
							Statement getGenreID = connection.createStatement();
							ResultSet result1 = getGenreID.executeQuery("select * from genres_in_movies where " +
							"movie_id =" + result.getInt(2));
							
							while(result1.next())
							{
								Statement getGenre = connection.createStatement();
								ResultSet result2 = getGenre.executeQuery("select * from genres where " +
								"id =" + result1.getInt(1));
								
								while(result2.next())
								{
									genres += result2.getString(2) + "\n\r";
								}
							}

						employeeAccessDialog(genres);
						}
						
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
						String starId = "";
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
							//System.out.println(starId);
						}
						if(starIdTextfield.getText().length() > 0)
						{	
							starId = starIdTextfield.getText();
						}
						
						String movies = "";
						Statement getID = connection.createStatement();
						ResultSet result = getID.executeQuery("select * from stars_in_movies where star_id ="
								+ starId);
						while(result.next())
						{
							Statement getMovieID = connection.createStatement();
							ResultSet result1 = getMovieID.executeQuery("select * from movies where " +
							"id =" + result.getInt(2));
							
							while(result1.next())
							{
								movies += result1.getString(2) + "\n\r";
							}

						employeeAccessDialog(movies);
						}
						
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

	public void getMetadata() {
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());     
        
		contentPane.add(new JLabel("Results go here"));
		setContentPane(contentPane);

        pack();
		repaint();		
	}
	
	public void randomGenerate()
	{
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());
        
		
	    final JTextField start = new JTextField(30);		
		final JTextField end = new JTextField(30);	
		
		JButton add = new JButton("Calculate");
		JButton clear = new JButton("Clear");
		add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((start.getText().length() > 0) && (end.getText().length() > 0))
				{
					long range = Integer.valueOf(end.getText()) - Integer.valueOf(start.getText()) + 1;
				    // compute a fraction of the range, 0 <= frac < range
				    long fraction = (long)(range * r.nextDouble());
					result = (int)(fraction + Integer.valueOf(start.getText()));
					employeeAccessDialog();
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            		start.setText("");
					end.setText("");
            }
        });
		contentPane.add(new JLabel("Enter Lower bound then upper bound"));
		contentPane.add(start);
		contentPane.add(end);
		contentPane.add(add);
		contentPane.add(clear);
	    
		setContentPane(contentPane);

        pack();
		repaint();
	}
	
	public void powerFunc()
	{
		JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.setLayout(new FlowLayout());
        
		
	    final JTextField start = new JTextField(30);		
		final JTextField end = new JTextField(30);	
		
		JButton add = new JButton("Calculate");
		JButton clear = new JButton("Clear");
		add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				if ((start.getText().length() > 0) && (end.getText().length() > 0))
				{
					result = Math.pow(Double.valueOf(start.getText()), Double.valueOf(end.getText()));
					employeeAccessDialog();
				}
				else
					Toolkit.getDefaultToolkit().beep();  // signal error
            }
        });
		
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            		start.setText("");
					end.setText("");
            }
        });
		contentPane.add(new JLabel("Number to the power of"));
		contentPane.add(start);
		contentPane.add(end);
		contentPane.add(add);
		contentPane.add(clear);
	    
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
	            parentFrame.insertAssocation();
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
