package com.collection.compatable.sortById;

import java.util.Comparator;

public class CompareByName implements Comparator<Customer> {
	

		@Override
		public int compare(Customer c1, Customer c2) {
		
			int result = c1.customerName.compareTo(c2.customerName);
		
			return result;
		}

}
