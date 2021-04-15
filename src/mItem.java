//I declare that this project is my own work and that all material previously written or published in any source by any other person has been duly acknowledged in the assignment. 
//I have not submitted this work, or a significant part thereof, previously as part of any academic program. 
//In submitting this assignment I give permission to copy it for assessment purposes only.
//Dakota C. Soares

//: src/mItem.java

/***********************************************************************
 * Assignment3, COMP482
 * Class: mItem.java
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
*  Creates an object of type mItem
*  @param none 
*  @return mItem
*  @throws 
*/
public class mItem implements Comparable<mItem>
{
	int call_no; 
	String title; 
	String author; 
	int year; 
	int quantity; 
	//default constructor 
	public mItem()
	{
		
	}
	//constructor
	public mItem(int call_no, String title, String author, int year, int quantity)
	{
		this.call_no = call_no; 
		this.title = title; 
		this.author = author; 
		this.year = year; 
		this.quantity = quantity; 
	}
	//returns the call id 
	public int get_call_no()
	{
		return call_no; 
	}
   
	//prints the item (same as toString)
	public void printItem()
	{
		System.out.println(call_no + ", " + title + ", " + author + ", " + year + ", " + quantity); 
	}

	//compares an instance of mItem with another using the call id
	@Override
	public int compareTo(mItem comparestu) 
	{
	        int compareItem=((mItem)comparestu).get_call_no();
	        /* For Ascending order*/
	        return this.call_no-compareItem;
	    }

	    @Override
	    public String toString() 
	    {
	        return call_no + ", " + title + ", " + author + ", " + year + ", " + quantity;
	    }
}
//end of class mItem.java