package info;

public class Item {
	private String title;
	private int quantity;
	
	public Item(String title, int quantity)
	{
		this.title = title;
		this.quantity = quantity;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void incQuantity() {
		quantity++;
	}
	public int getQuantity() {
		return quantity;
	}

}
