package tools;

public class Movie implements Comparable<Movie>
{
	private String title;
	private String year;
	private String directorFirstName;
	private String directorLastName;
	private String genres;
	private String stars;
	private String comparator;
	private String record;
	private String compareDirection;
	
	Movie(String title, String year, String directorFirstName, String directorLastName,
			String genres, String stars, String comparator, String compareDirection, String record)
	{
		this.title = title;
		this.year = year;
		this.directorFirstName = directorFirstName;
		this.directorLastName = directorLastName;
		this.genres = genres;
		this.stars = stars;
		this.comparator = comparator;
		this.record = record;
		this.compareDirection = compareDirection;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getYear() {
		return year;
	}
	public void setDirectorFirstName(String directorFirstName) {
		this.directorFirstName = directorFirstName;
	}
	public String getDirectorFirstName() {
		return directorFirstName;
	}
	public void setDirectorLastName(String directorLastName) {
		this.directorLastName = directorLastName;
	}
	public String getDirectorLastName() {
		return directorLastName;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	public String getGenres() {
		return genres;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public String getStars() {
		return stars;
	}

	@Override
	public int compareTo(Movie m) 
	{
		if (comparator.equalsIgnoreCase("year"))
		{
			if(compareDirection.equalsIgnoreCase("AtoZ"))
			{
				return year.compareToIgnoreCase(m.getYear());
			}
			return m.getYear().compareToIgnoreCase(year);
		}
		else if (comparator.equalsIgnoreCase("director"))
		{
			if(compareDirection.equalsIgnoreCase("AtoZ"))
			{
				return getDirector().compareToIgnoreCase(m.getDirector());
			}
			return m.getDirector().compareToIgnoreCase( getDirector() );
		}
		if(compareDirection.equalsIgnoreCase("AtoZ"))
		{
			return title.compareToIgnoreCase(m.getTitle());
		}
		return m.getTitle().compareToIgnoreCase(title);
	}

	public void setComparator(String comparator) {
		this.comparator = comparator;
	}

	public String getComparator() {
		return comparator;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getRecord() {
		return record;
	}
	
	public String getDirector()
	{
		return directorFirstName + " " + directorLastName;
	}

	public void setCompareDirection(String compareDirection) {
		this.compareDirection = compareDirection;
	}

	public String getCompareDirection() {
		return compareDirection;
	}
	
}
