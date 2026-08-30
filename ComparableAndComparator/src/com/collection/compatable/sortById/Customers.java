package com.collection.compatable.sortById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customers {

	public static void main(String[] args) {
		
		Customer c1 = new Customer(1001, "vikram");
		Customer c2 = new Customer(100, "vishwas");
		Customer c3 = new Customer(99, "sudama");
		Customer c4 = new Customer(1230, "arvind");
		Customer c5 = new Customer(1922, "banashankar");
		
		List<Customer> list = new ArrayList<Customer>();
		
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5); 
		
		
		//sorting the list
		
		Collections.sort(list, new CompareByName());
		
		
		//iterating the list
		
		for(int i = 0; i<  list.size(); i++) 
		{
			 Customer c = list.get(i);
			System.out.println("Customer Id : " + c.customerId  + " "+ " Customer Name : " + c.customerName );
		}
	}

}
