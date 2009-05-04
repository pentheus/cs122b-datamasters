package info;

import java.util.ArrayList;

public class Cart {
	private ArrayList<Item> items = new ArrayList<Item>();

	public void addItem(Item item) {
		if(itemExists(item))
			getItem(item.getTitle()).incQuantity();
		else
			items.add(item);
	}

	public ArrayList<Item> getItems() {
		return items;
	}
	
	public boolean itemExists(Item item)
	{
		for(Item i : items)
		{
			if (i.getTitle().equals(item.getTitle()))
			{
				return true;
			}
		}
		return false;
	}
	
	public Item getItem(String title)
	{
		for(Item i : items)
		{
			if (i.getTitle().equals(title))
			{
				return i;
			}
		}
		return null;
	}
}
