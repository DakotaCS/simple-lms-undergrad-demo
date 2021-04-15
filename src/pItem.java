//I declare that this project is my own work and that all material previously written or published in any source by any other person has been duly acknowledged in the assignment. 
//I have not submitted this work, or a significant part thereof, previously as part of any academic program. 
//In submitting this assignment I give permission to copy it for assessment purposes only.
//Dakota C. Soares

//: src/pItem.java

/***********************************************************************
 * Assignment3, COMP482
 * Class: pItem.java
 * 
 * Purpose: This class creates an object of type mItem.
 * 
 * @author: Dakota Soares, and other sources. Sources are appropriately referenced in-line. 
 * 
 * Student ID: 3318342
 * @date: February 26th, 2021 
 * 
 * Notes: see included report for test cases, parameters, etc. 
 * 
 */


/** 
*  Creates an object of type pItem
*  @param none 
*  @return pItem
*  @throws 
*/
public class pItem implements Comparable<pItem>
{
	int patron; 
	String name; 
	String address; 
	String email; 
	String phone; 
	int book; 
	String return_date; 
	//default constructor
	public pItem()
	{
		
	}
	//constructor
	public pItem(int patron_no, String name, String address, String email, String phone, int book_id, String return_date)
	{
		this.patron = patron_no; 
		this.name = name; 
		this.address = address; 
		this.email = email; 
		this.phone = phone; 
		this.book = book_id; 
		this.return_date = return_date; 
	}
	//gets the patron number
	public int get_patron_no()
	{
		return patron; 
	}
	//prints the item (same as toString()
	public void printItem()
	{
		System.out.println(patron + ", " + name + ", " + address + ", " + email + ", " + phone + ", " + book + ", " + return_date); 
	}
	
	@Override
	public int compareTo(pItem o) 
	{
		int comparePNo=((pItem)o).get_patron_no();
        return this.patron-comparePNo;
	}
	    @Override
	    public String toString() {
	        return patron + ", " + name + ", " + address + ", " + email + ", " + phone + ", " + book + ", " + return_date; 
	    }
}
//end of class pItem.java